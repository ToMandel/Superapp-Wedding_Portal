package superapp;

import org.springframework.stereotype.Component;
import superapp.boundries.MiniAppCommandBoundary;
import superapp.boundries.SuperAppObjectBoundary;
import superapp.boundries.UserBoundary;
import superapp.boundries.UserId;
import superapp.data.MiniAppCommandEntity;
import superapp.data.SuperAppObjectEntity;
import superapp.data.UserEntity;

@Component
public class Converter {

    public UserEntity userToEntity (UserBoundary boundary){
        UserEntity entity = new UserEntity();
        if (boundary.getUserId().getEmail() == null || boundary.getUserId().getEmail() == "") {
            entity.setUserId(boundary.getUserId().getSuperapp() + "#" + "");
        }
        else
            entity.setUserId(boundary.getUserId().getSuperapp() + "#" + boundary.getUserId().getEmail());
        if (boundary.getUsername() == null)
            entity.setUsername("");
        else
            entity.setUsername(boundary.getUsername());
        if (boundary.getRole() == null)
            entity.setRole("");
        else
            entity.setRole(boundary.getRole());
        if (boundary.getAvatar() == null)
            entity.setAvatar("");
        else
            entity.setAvatar(boundary.getAvatar());

        return entity;
    }


    public UserBoundary userToBoundary (UserEntity entity){
        UserBoundary boundary = new UserBoundary();
        String arr[] = entity.getUserId().split("#");
        String superapp, email;
        if (arr.length == 2) {
            superapp = arr[0];
            email = arr[1];
        }
        else{
            superapp = arr[0];
            email = "";
        }
        boundary.setUserId(new UserId(superapp, email));
        boundary.setUsername(entity.getUsername());
        boundary.setRole(entity.getRole());
        boundary.setAvatar(entity.getAvatar());
        return boundary;
    }
    public MiniAppCommandEntity miniAppCommandToEntity(MiniAppCommandBoundary boundary)
    {
        /*MiniAppCommandEntity entity = new MiniAppCommandEntity();
    	//entity.setCommandId(boundary.getCommandId().get);
    	entity.setCommand(boundary.getCommand());
    	entity.setTargetObject(boundary.getTargetObject());
    	entity.setInvocationTimestamp(boundary.getInvocationTimestamp());
    	entity.setInvokedBy(boundary.getInvokedBy());
    	entity.setCommandAttributes(boundary.getCommandAttributes());
    	return entity;*/
        return null;

    }

    public MiniAppCommandBoundary miniAppCommandToBoundary (MiniAppCommandEntity entity){
        return null;
    }

    public SuperAppObjectEntity superAppObjectToEntity (SuperAppObjectBoundary boundary){
        return null;
    }

    public SuperAppObjectBoundary superAppObjectToBoundary (SuperAppObjectEntity entity){
        return null;
    }


    
    
}

