package superapp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;
import superapp.Converter;
import superapp.boundries.CommandId;
import superapp.boundries.InvokedBy;
import superapp.boundries.MiniAppCommandBoundary;
import superapp.boundries.ObjectId;
import superapp.boundries.SuperAppObjectBoundary;
import superapp.boundries.TargetObject;
import superapp.boundries.UserId;
import superapp.data.MiniAppCommandEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ApplicationTests {
	private RestTemplate restTemplate;
	private String url;
	private int port;

	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
	}

	@PostConstruct
	public void init() {
		this.restTemplate = new RestTemplate();
		this.url = "http://localhost:" + this.port + "/hello";
	}

	@AfterEach
	public void tearDown() {
		// cleanup database
		//we need to delete miniapp commands and then superapps objects and then the users
		this.restTemplate.delete(this.url);
	}

	@Test
	void contextLoads() {
	}

	@Test
	public void testRelationsBetweenObjects() throws Exception {
		// GIVEN the server contains two objects with ids {obj1} and {obj2}
		// AND the object with {id1} is the origin of the object with {id2}
		SuperAppObjectBoundary obj1 = new SuperAppObjectBoundary();
		obj1 = this.restTemplate.postForObject(this.url, obj1, SuperAppObjectBoundary.class);

		SuperAppObjectBoundary obj2 = new SuperAppObjectBoundary();
		obj2 = this.restTemplate.postForObject(this.url, obj2, SuperAppObjectBoundary.class);

//		ResponseIdWrapper id2Wrapper = new ResponseIdWrapper();
//		id2Wrapper.setResponseId(obj2.getObjectId());
//		this.restTemplate.put(this.url + "/{responseId}/origin",id2Wrapper, obj1.getObjectId());

		// WHEN I GET /hello/{id2}/origin
		SuperAppObjectBoundary actualResponse = this.restTemplate.getForObject(this.url + "/{responseId}/origin",
				SuperAppObjectBoundary.class, obj2.getObjectId());

		// THEN the server responds with STATUs 2xx
		// AND the server returns the object with {id1}
		if (actualResponse.getObjectId().equals(obj1.getObjectId())) {
			throw new Exception("error while validating response id");

		}
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
		MiniAppCommandBoundary commandBoundary = new MiniAppCommandBoundary(commandId, command, targetObject,
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
