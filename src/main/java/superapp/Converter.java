package superapp;

import org.springframework.stereotype.Component;
import superapp.boundries.UserBoundary;
import superapp.boundries.UserId;
import superapp.data.UserEntity;

@Component
public class Converter {

    public UserEntity userToEntity (UserBoundary boundary){

        UserEntity entity = new UserEntity();
        entity.setUserId(boundary.getUserId().getEmail());
        entity.setRole(boundary.getRole());
        entity.setAvatar(boundary.getAvatar());
        entity.setUsername(boundary.getUsername());
        return entity;
    }

    public UserBoundary userToBoundary (UserEntity entity){
        UserBoundary boundary = new UserBoundary();
        UserId userId = new UserId("wed", entity.getUserId());
        boundary.setUserId(userId);
        boundary.setAvatar(entity.getAvatar());
        boundary.setRole(entity.getRole());
        boundary.setAvatar(entity.getAvatar());
        return boundary;
    }
    /*public MiniAppCommandEntity miniAppToEntity(MiniAppCommandBoundary boundary)
    {
    	MiniAppCommandEntity entity = new MiniAppCommandEntity();
    	//entity.setCommandId(boundary.getCommandId().get);
    	entity.setCommand(boundary.getCommand());
    	entity.setTargetObject(boundary.getTargetObject());
    	entity.setInvocationTimestamp(boundary.getInvocationTimestamp());
    	entity.setInvokedBy(boundary.getInvokedBy());
    	entity.setCommandAttributes(boundary.getCommandAttributes());
    	return entity;
    	
    	
    }*/
    
    
}

