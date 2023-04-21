package superapp.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import superapp.Converter;
import superapp.boundries.CommandId;
import superapp.boundries.MiniAppCommandBoundary;
import superapp.dal.MiniAppCommandCrud;
import superapp.data.MiniAppCommandEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MiniAppCommandDB implements MiniAppCommandService{

	private MiniAppCommandCrud miniappCommandCrud;
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
    public void setMiniAppCommandCrude (MiniAppCommandCrud miniappCommandCrud) {
    	this.miniappCommandCrud = miniappCommandCrud;
    }
    
    @Autowired
    public void setConverter(Converter converter){
        this.converter = converter;
    }

    @Override
    public Object invokeCommand(MiniAppCommandBoundary command){
        List<MiniAppCommandEntity> entities = this.miniappCommandCrud.findAll();
        String internalObjectId;
        if (entities.isEmpty())
            internalObjectId = "1";
        else
            internalObjectId = Integer.toString(entities.size() + 1);
        command.setCommandId(new CommandId(nameFromSpringConfig, command.getCommandId().getMiniapp(), internalObjectId));
        command.setInvocationTimestamp(new Date());
        MiniAppCommandEntity entity = this.converter.miniAppCommandToEntity(command);
        entity = this.miniappCommandCrud.save(entity);
        return this.converter.miniAppCommandToBoundary(entity);
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
}
