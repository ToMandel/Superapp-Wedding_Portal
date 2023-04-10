package superapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import superapp.boundries.*;
import superapp.data.MiniAppCommandEntity;
import superapp.data.SuperAppObjectEntity;
import superapp.data.UserEntity;

import java.util.Map;

@Component
public class Converter {

    private ObjectMapper jackson;

    public UserEntity userToEntity (UserBoundary boundary){
        UserEntity entity = new UserEntity();
        if (boundary.getUserId().getEmail() == null || boundary.getUserId().getEmail() == "")
            entity.setUserId(boundary.getUserId().getSuperapp() + "#" + "");
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
        SuperAppObjectEntity entity = new SuperAppObjectEntity();
        entity.setObjectId(boundary.getObjectId().getSuperapp() + "#" + boundary.getObjectId().getInternalObjectId());
        if (boundary.getType() == null)
            entity.setType("");
        else
            entity.setType(boundary.getType());
        if (boundary.getActive() == null)
            entity.setActive(false);
        else
            entity.setActive(entity.getActive());
        if (boundary.getAlias() == null)
            entity.setAlias("");
        else
            entity.setAlias(entity.getAlias());
        if (boundary.getLocation() == null){
            entity.setLat(0.0);
            entity.setLng(0.0);
        }
        else {
            entity.setLat(boundary.getLocation().getLat());
            entity.setLng(boundary.getLocation().getLng());
        }
        entity.setCreationTempStamp(boundary.getCreationTimestamp());
        //TODO: add createdBy, objectDetails
        return entity;
    }

    public SuperAppObjectBoundary superAppObjectToBoundary (SuperAppObjectEntity entity){
        SuperAppObjectBoundary boundary = new SuperAppObjectBoundary();
        String arr[] = entity.getObjectId().split("#");
        String superapp, internalObjectId;
        if (arr.length == 2) {
            superapp = arr[0];
            internalObjectId = arr[1];
        }
        else{
            superapp = arr[0];
            internalObjectId = "";
        }
        boundary.setObjectId(new ObjectId(superapp, internalObjectId));
        boundary.setType(entity.getType());
        boundary.setAlias(entity.getAlias());
        boundary.setActive(entity.getActive());
        boundary.setCreationTimestamp(entity.getCreationTempStamp());
        boundary.setLocation(new Location(entity.getLat(), entity.getLng()));
        String email;
        arr = entity.getCreatedBy().split("#");
        if (arr.length == 2)
            email = arr[1];
        else
            email = "";
        CreatedBy createdBy = new CreatedBy(new UserId(superapp, email));
        boundary.setCreatedBy(createdBy);
        try {
            boundary.setObjectDetails(
                    this.jackson
                            .readValue(entity.getObjectDetails(), Map.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return boundary;
    }


    
    
}

