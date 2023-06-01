package superapp.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import superapp.Converter;
import superapp.boundries.CommandId;
import superapp.boundries.MiniAppCommandBoundary;
import superapp.dal.MiniAppCommandCrud;
import superapp.dal.SupperAppObjectCrud;
import superapp.dal.UserCrud;
import superapp.data.MiniAppCommandEntity;
import superapp.data.SuperAppObjectEntity;
import superapp.data.UserEntity;
import superapp.data.UserRole;
import superapp.miniapps.CommandsInvoker;
import superapp.miniapps.MiniAppsCommand;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class MiniAppCommandDB implements MiniAppCommandServiceWithPagination {

	private MiniAppCommandCrud miniappCommandCrud;
	private SupperAppObjectCrud supperAppObjectCrud;
	private UserCrud userCrud;
	private CommandsInvoker commandsInvoker;
	private Converter converter;
	private String nameFromSpringConfig;
	private JmsTemplate jmsTemplate;
	private ObjectMapper jackson;

	@Value("${spring.application.name:defaultName}")
	public void setNameFromSpringConfig(String nameFromSpringConfig) {
		this.nameFromSpringConfig = nameFromSpringConfig;
	}

	@Autowired
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
		this.jmsTemplate.setDeliveryDelay(3000L);
	}

	@PostConstruct
	public void init() {
		System.err.println("**** spring.application.name = " + this.nameFromSpringConfig + " ****");
		this.jackson = new ObjectMapper();
	}

	@Autowired
	public void setMiniAppCommandCrud(MiniAppCommandCrud miniappCommandCrud) {
		this.miniappCommandCrud = miniappCommandCrud;
	}

	@Autowired
	public void setUserCrud(UserCrud userCrud) {
		this.userCrud = userCrud;
	}

	@Autowired
	public void setSupperAppObjectCrud(SupperAppObjectCrud supperAppObjectCrud) {
		this.supperAppObjectCrud = supperAppObjectCrud;
	}

	@Autowired
	public void setConverter(Converter converter) {
		this.converter = converter;
	}

	@Autowired
	public void setCommandsInvoker(CommandsInvoker commandsInvoker){
		this.commandsInvoker = commandsInvoker;
	}

	private UserEntity getUser(String superAppName, String email) {
		String id = superAppName + "#" + email;
		return this.userCrud.findById(id).orElseThrow(() -> new UnauthorizedException(
				"There is no user with email: " + email + " in " + superAppName + " superapp"));
	}

	@Deprecated
	@Override
	public List<MiniAppCommandBoundary> getAllCommands() {
		throw new DeprecatedOperationException();
	}

	@Override
	public List<MiniAppCommandBoundary> getAllCommands(String superAppName, String email, int page, int size) {
		UserEntity user = getUser(superAppName, email);
		if (user.getRole() != null && user.getRole() == UserRole.ADMIN) {
			return this.miniappCommandCrud.findAll(PageRequest.of(page, size, Direction.ASC, "command", "invokedBy",
					"invocationTimestamp", "commandId")).stream().map(this.converter::miniAppCommandToBoundary)
					.toList();
		} else
			throw new ForbiddenException("Operation is not allowed, the user is not ADMIN");
	}

	@Override
	@Deprecated
	public List<MiniAppCommandBoundary> getAllMiniAppCommands(String miniAppName) {
		throw new DeprecatedOperationException();
	}

	public List<MiniAppCommandBoundary> getAllMiniAppCommands(String superAppName, String miniAppName, String email,
			int size, int page) {
		UserEntity user = getUser(superAppName, email);
		if (user.getRole() != null && user.getRole() == UserRole.ADMIN) {
			String id = this.nameFromSpringConfig + "#" + miniAppName + "#" + "*";
			return this.miniappCommandCrud
					.findAllByCommandIdLike(id, PageRequest.of(page, size, Direction.ASC, "command", "invokedBy",
							"invocationTimestamp", "commandId"))
					.stream().map(this.converter::miniAppCommandToBoundary).toList();
		} else
			throw new ForbiddenException("Operation is not allowed, the user is not ADMIN");

	}

	@Override
	@Deprecated
	public void deleteAllCommands() {
		throw new DeprecatedOperationException();
	}

	@Override
	public void deleteAllCommands(String superAppName, String email) {
		UserEntity user = getUser(superAppName, email);
		if (user.getRole() != null && user.getRole() == UserRole.ADMIN) {
			this.miniappCommandCrud.deleteAll();
		} else
			throw new ForbiddenException("Operation is not allowed, the user is not ADMIN");
	}

	@Override
	@Deprecated
	public Object invokeCommand(MiniAppCommandBoundary command) {
		throw new DeprecatedOperationException();
	}


	public Object invokeMiniAppCommandAsync(MiniAppCommandBoundary command, boolean isAsync) {
		UserEntity user = getUser(command.getInvokedBy().getUserId().getSuperapp(),
				command.getInvokedBy().getUserId().getEmail());

		if (user.getRole() != UserRole.MINIAPP_USER)
			throw new ForbiddenException("Only MINIAPP users can invoke commands");

		String objectId = command.getTargetObject().getObjectId().getSuperapp() + "#"
				+ command.getTargetObject().getObjectId().getInternalObjectId();

		SuperAppObjectEntity targetObject = this.supperAppObjectCrud.findById(objectId)
				.orElseThrow(() -> new NotFoundException("There is no object with object id: " + objectId));

		if (!targetObject.getActive())
			throw new NotFoundException("The target object you are trying to invoke a command on him is inactive");

		command.setCommandId(
				new CommandId(nameFromSpringConfig, command.getCommandId().getMiniapp(), UUID.randomUUID().toString()));
		command.setInvocationTimestamp(new Date());
		if (isAsync){
			command.getCommandAttributes().put("status", "waiting");
			try {
				String json = this.jackson.writeValueAsString(command);
				this.jmsTemplate.convertAndSend("asyncMiniAppQueue", json);
				return command;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		else {
			String miniAppName = command.getCommandId().getMiniapp();
			String commandName = command.getCommand();
			Object rv = callToFunction(command, commandName, miniAppName);
			MiniAppCommandEntity entity = this.converter.miniAppCommandToEntity(command);
			entity = this.miniappCommandCrud.save(entity);
			return rv;
		}
	}


	public Object callToFunction(MiniAppCommandBoundary commandBoundary, String commandName, String miniAppName) {
		MiniAppsCommand.MINI_APPS miniApp = MiniAppsCommand.getMiniAppName(miniAppName.toUpperCase());
		if (miniApp == MiniAppsCommand.MINI_APPS.UNKNOWN_MINIAPP)
			return CommandsInvoker.createUnknownCommandBoundary(commandName, "Could not find miniapp");
		List<String> miniAppCommands = this.commandsInvoker.getCommandsInMiniApp().get(miniApp);
		boolean found =  false;
		for (String command : miniAppCommands){
			if (command.equals(commandName)) {
				found = true;
				break;
			}
		}
		if (!found)
			return CommandsInvoker.createUnknownCommandBoundary(commandName, "Could not find command: " + commandName + " in miniapp: " + miniAppName);
		return this.commandsInvoker.createCommandClass(commandName).execute(commandBoundary);
	}
}
