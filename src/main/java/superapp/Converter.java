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

	public Converter() {
		this.jackson = new ObjectMapper();
	}

	public UserEntity userToEntity(UserBoundary boundary) {
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

	public UserBoundary userToBoundary(UserEntity entity) {

		UserBoundary boundary = new UserBoundary();
		String arr[] = entity.getUserId().split("#");
		String superapp, email;
		if (arr.length == 2) {
			superapp = arr[0];
			email = arr[1];
		} else {
			superapp = arr[0];
			email = "";
		}
		boundary.setUserId(new UserId(superapp, email));
		boundary.setUsername(entity.getUsername());
		boundary.setRole(entity.getRole());
		boundary.setAvatar(entity.getAvatar());
		return boundary;
	}

	public MiniAppCommandEntity miniAppCommandToEntity(MiniAppCommandBoundary boundary) {
		MiniAppCommandEntity entity = new MiniAppCommandEntity();
		entity.setCommandId(boundary.getCommandId().toString());
		// return "CommandId [superapp=" + superapp + ", miniapp=" + miniapp + ",
		// internalCommandId=" + internalCommandId+ "]";
		entity.setCommand(boundary.getCommand());
		// return string
		entity.setInvocationTimestamp(boundary.getInvocationTimestamp().toString());
		// return "EEE MMM dd HH:mm:ss zzz yyyy";
		entity.setInvokedBy(boundary.getInvokedBy().toString());
		// return "InvokedBy [userId=" + userId + "]";
		entity.setTargetObject(boundary.getTargetObject().toString());
		// return "TargetObject [objectId=" + objectId + "]";
		try {
			entity.setCommandAttributes(this.jackson.writeValueAsString(boundary.getCommandAttributes()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return entity;
	}

	public MiniAppCommandBoundary miniAppCommandToBoundary(MiniAppCommandEntity entity) {
		MiniAppCommandBoundary boundary = new MiniAppCommandBoundary();
		boundary.setCommandId(CommandId.fromString(entity.getCommandId()));
		boundary.setCommand(entity.getCommand());
		try {
			boundary.setInvocationTimestamp(boundary.DateParser(entity.getInvocationTimestamp()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		boundary.setInvokedBy(InvokedBy.fromString(entity.getInvokedBy()));
		boundary.setTargetObject(TargetObject.fromString(entity.getTargetObject()));
		try {
			boundary.setCommandAttributes(this.jackson.readValue(entity.getCommandAttributes(), Map.class));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return boundary;
	}

	public SuperAppObjectEntity superAppObjectToEntity(SuperAppObjectBoundary boundary) {
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
		if (boundary.getLocation() == null) {
			entity.setLat(0.0);
			entity.setLng(0.0);
		} else {
			entity.setLat(boundary.getLocation().getLat());
			entity.setLng(boundary.getLocation().getLng());
		}

		entity.setCreationTempStamp(boundary.getCreationTimestamp());
		if (boundary.getCreatedBy() == null || boundary.getCreatedBy().getUserId() == null)
			entity.setCreatedBy("");
		else
			entity.setCreatedBy(boundary.getCreatedBy().getUserId().getSuperapp() + "#" + boundary.getCreatedBy().getUserId().getEmail());
		try {
			entity.setObjectDetails(this.jackson.writeValueAsString(boundary.getObjectDetails()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return entity;
	}

	public SuperAppObjectBoundary superAppObjectToBoundary(SuperAppObjectEntity entity) {
		SuperAppObjectBoundary boundary = new SuperAppObjectBoundary();
		String arr[] = entity.getObjectId().split("#");
		String superapp, internalObjectId;
		if (arr.length == 2) {
			superapp = arr[0];
			internalObjectId = arr[1];
		} else {
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
			boundary.setObjectDetails(this.jackson.readValue(entity.getObjectDetails(), Map.class));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return boundary;
	}

}
