package superapp.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import superapp.Converter;
import superapp.boundries.CommandId;
import superapp.boundries.MiniAppCommandBoundary;
import superapp.boundries.UnknownCommandBoundary;
import superapp.dal.MiniAppCommandCrud;
import superapp.dal.SupperAppObjectCrud;
import superapp.data.MiniAppCommandEntity;
import superapp.objects.Supplier;
import org.springframework.jms.core.JmsTemplate;

import java.util.*;

@Service
public class MiniAppCommandDB implements MiniAppCommandServiceWithAsyncSupport{

	private MiniAppCommandCrud miniappCommandCrud;
    private SupperAppObjectCrud supperAppObjectCrud;
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
    public void setMiniAppCommandCrude (MiniAppCommandCrud miniappCommandCrud, SupperAppObjectCrud supperAppObjectCrud) {
    	this.miniappCommandCrud = miniappCommandCrud;
        this.supperAppObjectCrud = supperAppObjectCrud;
    }
    
    @Autowired
    public void setConverter(Converter converter){
        this.converter = converter;
    }


    @Override
    public List<MiniAppCommandBoundary> getAllCommands() {
    	List <MiniAppCommandEntity> commands = this.miniappCommandCrud.findAll();
    	List< MiniAppCommandBoundary> rv = new ArrayList<MiniAppCommandBoundary>();
    	for (MiniAppCommandEntity c : commands) {
    		rv.add(this.converter.miniAppCommandToBoundary(c));
    	}
    	return rv;
    }

    @Override
    public List<MiniAppCommandBoundary> getAllMiniAppCommands(String miniAppName) {
    	List <MiniAppCommandBoundary> allCommands = getAllCommands();
        List<MiniAppCommandBoundary> miniAppCommands = new ArrayList<MiniAppCommandBoundary>();
    	for (MiniAppCommandBoundary c : allCommands) {
    		if (c.getCommandId().getMiniapp().equals(miniAppName))
                miniAppCommands.add(c);
    	}
    	return miniAppCommands;
    }

    @Override
    public void deleteAllCommands() {
    	this.miniappCommandCrud.deleteAll();
    }

    public Object callToFunction(String commandName){
        switch (commandName){
            case "getTypes":
                return Supplier.getAllTypes();
            case "getAllSuppliers":
                return supperAppObjectCrud.findAllByType("Supplier");
            default:
                UnknownCommandBoundary boundary = new UnknownCommandBoundary();
                boundary.setCommandName(commandName);
                boundary.setErrorMessage("Could not find command");
                return boundary;

        }
    }

    @Override
    @Deprecated
    public Object invokeCommand(MiniAppCommandBoundary command) {
        throw new DeprecatedOperationException();
    }

    @Override
    public Object invokeMiniAppCommandAsync(MiniAppCommandBoundary command, boolean isAsync) {
        String internalCommandId = UUID.randomUUID().toString(); //we're doing rand so the size of entities is not relevant
        command.setCommandId(new CommandId(nameFromSpringConfig, command.getCommandId().getMiniapp(), internalCommandId));
        command.setInvocationTimestamp(new Date());
        String commandName = command.getCommand();
        Object rv = callToFunction(commandName);
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
