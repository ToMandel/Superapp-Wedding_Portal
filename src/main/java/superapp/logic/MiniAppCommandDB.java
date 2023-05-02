package superapp.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import superapp.Converter;
import superapp.boundries.CommandId;
import superapp.boundries.MiniAppCommandBoundary;
import superapp.dal.MiniAppCommandCrud;
import superapp.dal.SupperAppObjectCrud;
import superapp.data.MiniAppCommandEntity;
import superapp.data.SuperAppObjectEntity;
import superapp.objects.Supplier;

import java.util.UUID;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MiniAppCommandDB implements MiniAppCommandService{

	private MiniAppCommandCrud miniappCommandCrud;
    private SupperAppObjectCrud supperAppObjectCrud;
	private Converter converter;
    private String nameFromSpringConfig;

    @Value("${spring.application.name:defaultName}")
    public void setNameFromSpringConfig(String nameFromSpringConfig) {
        this.nameFromSpringConfig = nameFromSpringConfig;
    }
    
    @PostConstruct
    public void init() {
        System.err.println("**** spring.application.name = " + this.nameFromSpringConfig + " ****");
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
    public Object invokeCommand(MiniAppCommandBoundary command){
        //List<MiniAppCommandEntity> entities = this.miniappCommandCrud.findAll();
//        if (entities.isEmpty())
//            //internalObjectId = "1";
//        else
            //internalObjectId = Integer.toString(entities.size() + 1);
        String internalCommandId = UUID.randomUUID().toString(); //we're doing rand so the size of entities is not relevant
        command.setCommandId(new CommandId(nameFromSpringConfig, command.getCommandId().getMiniapp(), internalCommandId));
        command.setInvocationTimestamp(new Date());
        MiniAppCommandEntity entity = this.converter.miniAppCommandToEntity(command);
        entity = this.miniappCommandCrud.save(entity);

        String commandName = command.getCommand();
        return callToFunction(commandName);
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
                List<SuperAppObjectEntity> allSuppliers = supperAppObjectCrud.findAllByType("Supplier");
                return allSuppliers;
            default:
                throw new BadRequestException("Can find command: " + commandName);

        }
    }
}
