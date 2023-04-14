package superapp.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    
    @Transactional
    @Override
    public Object invokeCommand(MiniAppCommandBoundary command){
        List<MiniAppCommandEntity> entities = this.miniappCommandCrud.findAll();
        String internalObjectId;
        if (entities.isEmpty())
            internalObjectId = "1";
        else
            internalObjectId = Integer.toString(entities.size() + 1);
        //TODO: check how to get mini-app name
        command.setCommandId(new CommandId(nameFromSpringConfig, "miniapp", internalObjectId));
        command.setInvocationTimestamp(new Date());
        MiniAppCommandEntity entity = this.converter.miniAppCommandToEntity(command);
        entity = this.miniappCommandCrud.save(entity);
        return this.converter.miniAppCommandToBoundary(entity);
    }	
    
    @Transactional (readOnly = true)
    @Override
    public List<MiniAppCommandBoundary> getAllCommands() {
    	List <MiniAppCommandEntity> commands = this.miniappCommandCrud.findAll();
    	List< MiniAppCommandBoundary> rv = new ArrayList<MiniAppCommandBoundary>();
    	for (MiniAppCommandEntity c : commands) {
    		rv.add(this.converter.miniAppCommandToBoundary(c));
    	}
    	return rv;
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<MiniAppCommandBoundary> getAllMiniAppCommands(String miniAppName) {
    	List <MiniAppCommandBoundary> commands = getAllCommands();
    	for (MiniAppCommandBoundary c : commands) {
    		if (!c.getCommandId().getMiniapp().equals(miniAppName)) 
    			commands.remove(c);
    	}
    	return commands;
    }
    
    @Transactional
    @Override
    public void deleteAllCommands() {
    	this.miniappCommandCrud.deleteAll();
    }
}
