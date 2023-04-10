package demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import superapp.Converter;
import superapp.boundries.CommandId;
import superapp.boundries.InvokedBy;
import superapp.boundries.MiniAppCommandBoundary;
import superapp.boundries.ObjectId;
import superapp.boundries.TargetObject;
import superapp.boundries.UserId;
import superapp.data.MiniAppCommandEntity;

@SpringBootTest
class ApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void ConverterMiniAppCommandToEntity() {
		Converter c = new Converter();
		MiniAppCommandBoundary boundary = new MiniAppCommandBoundary();

		// Create a CommandId object
		CommandId commandId = new CommandId("mySuperappZohar", "myMiniappTal", "1234");

		// Create a TargetObject object
		ObjectId objectId = new ObjectId("mySuperappZohar", "5678");
		TargetObject targetObject = new TargetObject(objectId);

		// Create an InvokedBy object
		UserId userId = new UserId("mySuperappZohar", "user@example.com");
		InvokedBy invokedBy = new InvokedBy(userId);

		// Create a Map of command attributes
		Map<String, Object> commandAttributes = new HashMap<>();
		commandAttributes.put("color", "red");
		commandAttributes.put("size", "small");

		String command = "createObject";
		// Create a MiniAppCommandBoundary object
		MiniAppCommandBoundary commandBoundary = new MiniAppCommandBoundary
				(commandId, command, targetObject,
				new Date(), invokedBy, commandAttributes);
		MiniAppCommandEntity entity = c.miniAppCommandToEntity(commandBoundary);
		
	    // Check that the entity was created with the expected values
	    assertEquals(commandId.toString(), entity.getCommandId());
	    assertEquals(command, entity.getCommand());
	    assertEquals(targetObject, entity.getTargetObject().toString());
	    assertEquals(invokedBy, entity.getInvokedBy().toString());
	    assertEquals(commandAttributes, entity.getCommandAttributes().toString());
	}

}
