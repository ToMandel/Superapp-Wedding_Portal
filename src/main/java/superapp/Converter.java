package superapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;
import superapp.boundries.*;
import superapp.data.MiniAppCommandEntity;
import superapp.data.SuperAppObjectEntity;
import superapp.data.UserEntity;
import superapp.logic.BadRequestException;

import java.util.Map;

@Component
public class Converter {

	private ObjectMapper jackson;

	public Converter() {
		this.jackson = new ObjectMapper();
	}

	public UserEntity userToEntity(UserBoundary boundary) {
		UserEntity entity = new UserEntity();
		if (boundary.getUserId().getEmail() == null || boundary.getUserId().getEmail().equals(""))
			throw new BadRequestException("E-mail is not in a valid format");
		else
			entity.setUserId(boundary.getUserId().getSuperapp() + "#" + boundary.getUserId().getEmail());
		if (boundary.getUsername() == null || boundary.getUsername().equals(""))
			throw new BadRequestException("User Name can't be null or empty");
		else
			entity.setUsername(boundary.getUsername());
		
		if(boundary.getRole() == null || boundary.getRole().toString().equals(""))
			throw new BadRequestException("Role Name can't be null or empty");
		else
			entity.setRole(boundary.getRole());

		if (boundary.getAvatar() == null|| boundary.getAvatar().equals(""))
			throw new BadRequestException("Avatar can't be null or empty");
		else
			entity.setAvatar(boundary.getAvatar());

		return entity;
	}

	public UserBoundary userToBoundary(UserEntity entity) {

		UserBoundary boundary = new UserBoundary();
		boundary.setUserId(UserId.userIdFromString(entity.getUserId()));
		boundary.setUsername(entity.getUsername());
		boundary.setRole(entity.getRole());
		boundary.setAvatar(entity.getAvatar());
		return boundary;
	}

	public MiniAppCommandEntity miniAppCommandToEntity(MiniAppCommandBoundary boundary) {
		MiniAppCommandEntity entity = new MiniAppCommandEntity();
		CommandId commandId = boundary.getCommandId();
		//create commandId with delimiter between each field
		entity.setCommandId(commandId.getSuperapp() + '#' + commandId.getMiniapp() + '#' + commandId.getInternalCommandId());
		if (boundary.getCommand() == null || boundary.getCommand().equals(""))
			throw new BadRequestException("Command can't be null or empty");
		else
			entity.setCommand(boundary.getCommand());
		entity.setInvocationTimestamp(boundary.getInvocationTimestamp());
		if (boundary.getInvokedBy() == null ||
			boundary.getInvokedBy().getUserId() == null ||
			boundary.getInvokedBy().getUserId().getEmail() == null|| boundary.getInvokedBy().getUserId().getEmail().equals("") ||
			boundary.getInvokedBy().getUserId().getSuperapp() == null|| boundary.getInvokedBy().getUserId().getSuperapp().equals(""))
				throw new BadRequestException("Creator of command's id is not fully defined");
		else {
			//here if there is invokedBy key in the boundary
			//create invokedBy with delimiter between each field
			UserId userId = boundary.getInvokedBy().getUserId();
			entity.setInvokedBy(userId.getSuperapp() + '#' + userId.getEmail());
		}
		if (boundary.getTargetObject() == null ||
			boundary.getTargetObject().getObjectId() == null||
			boundary.getTargetObject().getObjectId().getSuperapp() == null || boundary.getTargetObject().getObjectId().getSuperapp().equals("") ||
			boundary.getTargetObject().getObjectId().getInternalObjectId() == null || boundary.getTargetObject().getObjectId().getInternalObjectId().equals(""))
				throw new BadRequestException("Target object id is not fully defined");
		else {
			//here if there is targetObject key in the boundary
			//create targetObject with delimiter between each field
			ObjectId objectId = boundary.getTargetObject().getObjectId();
			entity.setTargetObject(objectId.getSuperapp() + '#' + objectId.getInternalObjectId());
		}
		entity.setCommandAttributes(boundary.getCommandAttributes());
		return entity;
	}

	public MiniAppCommandBoundary miniAppCommandToBoundary(MiniAppCommandEntity entity) {
		MiniAppCommandBoundary boundary = new MiniAppCommandBoundary();
		//parse each id field (String) to an object
		boundary.setCommandId(CommandId.commandIdFromString(entity.getCommandId()));
		boundary.setCommand(entity.getCommand());
		boundary.setInvocationTimestamp(entity.getInvocationTimestamp());
		boundary.setInvokedBy(InvokedBy.userIdFromString(entity.getInvokedBy()));
		boundary.setTargetObject(TargetObject.targetObjectFromString(entity.getTargetObject()));
		boundary.setCommandAttributes(entity.getCommandAttributes());
		return boundary;
	}

	public SuperAppObjectEntity superAppObjectToEntity(SuperAppObjectBoundary boundary) {
		SuperAppObjectEntity entity = new SuperAppObjectEntity();
		entity.setObjectId(boundary.getObjectId().getSuperapp() + "#" + boundary.getObjectId().getInternalObjectId());
		if (boundary.getCreatedBy() == null || boundary.getCreatedBy().getUserId() == null ||
			boundary.getCreatedBy().getUserId().getSuperapp() == null ||
			boundary.getCreatedBy().getUserId().getSuperapp().equals("") ||
			boundary.getCreatedBy().getUserId().getEmail() == null ||
			boundary.getCreatedBy().getUserId().getEmail().equals(""))
			throw new BadRequestException("Created by can't be null or empty");

		if (boundary.getType() == null || boundary.getType().equals(""))
			throw new BadRequestException("Type can't be null or empty");
		else
			entity.setType(boundary.getType());
		if (boundary.getActive() == null)
			entity.setActive(true);
		else
			entity.setActive(boundary.getActive());
		if (boundary.getAlias() == null || boundary.getAlias().equals(""))
			throw new BadRequestException("Alias can't be null or empty");
		else
			entity.setAlias(boundary.getAlias());
		if (boundary.getLocation() == null)
			entity.setLocation(new Point(0, 0));
		else
			entity.setLocation(new Point(boundary.getLocation().getLat(), boundary.getLocation().getLng()));

		entity.setCreationTempStamp(boundary.getCreationTimestamp());
		if (boundary.getCreatedBy() == null || boundary.getCreatedBy().getUserId() == null)
			entity.setCreatedBy("");
		else
			entity.setCreatedBy(boundary.getCreatedBy().getUserId().getSuperapp() + "#" + boundary.getCreatedBy().getUserId().getEmail());
		entity.setObjectDetails(boundary.getObjectDetails());
		return entity;
	}

	public SuperAppObjectBoundary superAppObjectToBoundary(SuperAppObjectEntity entity) {
		SuperAppObjectBoundary boundary = new SuperAppObjectBoundary();
		boundary.setObjectId(ObjectId.objectIdFromString(entity.getObjectId()));
		boundary.setType(entity.getType());
		boundary.setAlias(entity.getAlias());
		boundary.setActive(entity.getActive());
		boundary.setCreationTimestamp(entity.getCreationTempStamp());
		boundary.setLocation(new Location(entity.getLocation().getX(), entity.getLocation().getY()));
		//boundary.setLocation(new Location(entity.getLat(), entity.getLng()));
		boundary.setCreatedBy(CreatedBy.createdByFromString(entity.getCreatedBy()));
		boundary.setObjectDetails(entity.getObjectDetails());
		return boundary;
	}

	public String toEntity (Map<String, Object> data) {
		try {
			return this.jackson
					.writeValueAsString(data);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
