package superapp.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import superapp.Converter;
import superapp.boundries.CommandId;
import superapp.boundries.MiniAppCommandBoundary;
import superapp.boundries.UnknownCommandBoundary;
import superapp.dal.MiniAppCommandCrud;
import superapp.dal.SupperAppObjectCrud;
import superapp.dal.UserCrud;
import superapp.data.MiniAppCommandEntity;
import superapp.data.UserEntity;
import superapp.data.UserRole;
import superapp.objects.Supplier;
import org.springframework.jms.core.JmsTemplate;

import java.util.*;

@Service
public class MiniAppCommandDB implements MiniAppCommandServiceWithPagination{

	private MiniAppCommandCrud miniappCommandCrud;
    private SupperAppObjectCrud supperAppObjectCrud;

    private UserCrud userCrud;

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
        this.jmsTemplate.setDeliveryDelay(5000L);
    }
    
    @PostConstruct
    public void init() {
        System.err.println("**** spring.application.name = " + this.nameFromSpringConfig + " ****");
        this.jackson = new ObjectMapper();
    }
    
    @Autowired
    public void setMiniAppCommandCrud (MiniAppCommandCrud miniappCommandCrud) {
    	this.miniappCommandCrud = miniappCommandCrud;
    }

    @Autowired
    public void setUserCrud (UserCrud userCrud){
        this.userCrud = userCrud;
    }

    @Autowired
    public void setSupperAppObjectCrud (SupperAppObjectCrud supperAppObjectCrud){
        this.supperAppObjectCrud = supperAppObjectCrud;
    }
    
    @Autowired
    public void setConverter(Converter converter){
        this.converter = converter;
    }

    private UserEntity getUser (String superAppName, String email){
        String id = superAppName + "#" + email;
        return this.userCrud.findById(id)
                .orElseThrow(() -> new UnauthorizedException("There is no user with email: " + email + "in " + superAppName + " superapp"));
    }


    @Override
    public List<MiniAppCommandBoundary> getAllCommands(String superAppName, String email, int page, int size) {
        UserEntity user = getUser(superAppName, email);
        if (user.getRole() != null && user.getRole() == UserRole.ADMIN) {
            return this.miniappCommandCrud.findAll(PageRequest.of(page, size))
                    .stream()
                    .map(this.converter::miniAppCommandToBoundary)
                    .toList();
        }
        else
            throw new ForbiddenException("Operation is not allowed, the user is not ADMIN");
    }

    @Override
    public void deleteAllCommands(String superAppName, String email) {
        UserEntity user = getUser(superAppName, email);
        if (user.getRole() != null && user.getRole() == UserRole.ADMIN) {
            this.miniappCommandCrud.deleteAll();
        }
        else
            throw new ForbiddenException("Operation is not allowed, the user is not ADMIN");
    }

    @Override
    @Deprecated
    public List<MiniAppCommandBoundary> getAllMiniAppCommands(String miniAppName) {
    	throw new DeprecatedOperationException();
    }
   
    
    public List<MiniAppCommandBoundary>getAllMiniAppCommands(String superAppName, String miniAppName, String email,int size, int page){
        UserEntity user = getUser(superAppName, email);
        if (user.getRole() != null && user.getRole() == UserRole.ADMIN) {
           String id = this.nameFromSpringConfig + "#" + miniAppName + "#" + "*";
        return this.miniappCommandCrud.findAllByCommandIdLike(id, PageRequest.of(page, size))
                .stream()
                .map(this.converter::miniAppCommandToBoundary)
                .toList();
        }
        else
            throw new ForbiddenException("Operation is not allowed, the user is not ADMIN");

    }

    @Override
    @Deprecated
    public void deleteAllCommands() {
    	throw new DeprecatedOperationException();
    }

    public Object callToFunction(MiniAppCommandBoundary command, String commandName, String miniAppName){
        switch (miniAppName) {
            case ("suppliers"):
                switch (commandName) {
                    case "getTypes":
                        return Supplier.getAllTypes();
                    //case "getAllSuppliers":
                    //    return supperAppObjectCrud.findAllByType("Supplier", PageRequest.of(FIRST_PAGE, MAX_SIZE));
                    default:
                        return createUnknownCommandBoundary(commandName, "Could not find command");

                }
            case "customers":
                switch (commandName){
                    default:
                        return createUnknownCommandBoundary(commandName, "Could not find command");
                }
            case "tables":
                switch (commandName){
                    case "getAllTablesOfGuest":
                        String type = "guest";
                        String invokedBy = command.getInvokedBy().getUserId().getSuperapp() + "#" + command.getInvokedBy().getUserId().getEmail();
                        //assume that the user invoked the command is the same user that created the object
                        return this.supperAppObjectCrud.findAllByTypeAndCreatedBy(type, invokedBy);
                    default:
                        return createUnknownCommandBoundary(commandName, "Could not find command");
                }
            default:
                return createUnknownCommandBoundary(commandName, "Could not find miniapp");
        }
    }

    public UnknownCommandBoundary createUnknownCommandBoundary (String commandName, String errMsg){
        UnknownCommandBoundary boundary = new UnknownCommandBoundary();
        boundary.setCommandName(commandName);
        boundary.setErrorMessage(errMsg);
        return boundary;
    }

    @Override
    @Deprecated
    public Object invokeCommand(MiniAppCommandBoundary command) {
        throw new DeprecatedOperationException();
    }

    @Deprecated
    @Override
    public List<MiniAppCommandBoundary> getAllCommands() {
        throw new DeprecatedOperationException();
    }

    @Override
    public Object invokeMiniAppCommandAsync(MiniAppCommandBoundary command, boolean isAsync) {
        String internalCommandId = UUID.randomUUID().toString(); //we're doing rand so the size of entities is not relevant
        command.setCommandId(new CommandId(nameFromSpringConfig, command.getCommandId().getMiniapp(), internalCommandId));
        command.setInvocationTimestamp(new Date());
        String miniAppName = command.getCommandId().getMiniapp();
        String commandName = command.getCommand();
        Object rv = callToFunction(command, commandName, miniAppName);
        if (command.getCommandAttributes() == null)
            command.setCommandAttributes(new HashMap<>());
        if (rv instanceof UnknownCommandBoundary)
            command.getCommandAttributes().put("error", "Could not found command");
        MiniAppCommandEntity entity = this.converter.miniAppCommandToEntity(command);
        entity = this.miniappCommandCrud.save(entity);
        if (isAsync) {
            command.getCommandAttributes().put("status", "waiting");
            try {
                String json = this.jackson.writeValueAsString(command);
                this.jmsTemplate.convertAndSend("asyncMiniAppQueue", json);
                entity = this.converter.miniAppCommandToEntity(command);
                entity = this.miniappCommandCrud.save(entity);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return rv;
    }
}
