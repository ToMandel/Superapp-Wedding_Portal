package superapp;

import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;
import superapp.boundries.ObjectId;
import superapp.boundries.SuperAppObjectBoundary;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
		this.url = "http://localhost:" + this.port;
	}

	@AfterEach
	public void tearDown() {
		// cleanup database
		//we need to delete miniapp commands and then superapps objects and then the users
		String deleteAllCommandsPATH = "/superapp/admin/miniapp";
		String deleteAllObjectsPATH = "/superapp/admin/objects";
		String deleteAllUsersPATH = "/superapp/admin/users";
		this.restTemplate.delete(this.url + deleteAllCommandsPATH);
		this.restTemplate.delete(this.url + deleteAllObjectsPATH);
		this.restTemplate.delete(this.url + deleteAllUsersPATH);
	}

	@Test
	void contextLoads() {
	}

	@Test
	public void testRelationsBetweenChildToParent() throws Exception {
		// GIVEN the server contains two objects with ids {parentObjectId} and {childObjectId}
		// AND the object with {parentObjectId} is the parent of the object with {childObjectId}

		//create two objects - obj1, obj2
		SuperAppObjectBoundary parent = new SuperAppObjectBoundary();
		parent.setAlias("TestObj1");
		parent.setType("TEST");
		parent = this.restTemplate.postForObject(this.url + "/superapp/objects", parent, SuperAppObjectBoundary.class);

		SuperAppObjectBoundary child = new SuperAppObjectBoundary();
		child.setAlias("TestObj2");
		child.setType("TEST");
		child = this.restTemplate.postForObject(this.url + "/superapp/objects", child, SuperAppObjectBoundary.class);

		//set attributes to the parent and child ObjectIds
		ObjectId parentObjectId = new ObjectId();
		parentObjectId.setSuperapp(parent.getObjectId().getSuperapp());
		parentObjectId.setInternalObjectId(parent.getObjectId().getInternalObjectId());

		ObjectId childObjectId = new ObjectId();
		childObjectId.setSuperapp(child.getObjectId().getSuperapp());
		childObjectId.setInternalObjectId(child.getObjectId().getInternalObjectId());

		this.restTemplate.put(this.url + "/superapp/objects/{superapp}/{parentInternalId}/children",childObjectId, parent.getObjectId().getSuperapp(), parent.getObjectId().getInternalObjectId());

		// WHEN I GET /superapp/objects/{childSuperapp}/{childInternalObjectId}/parents
		SuperAppObjectBoundary[] actualResponse;
		actualResponse = this.restTemplate.getForObject(this.url + "/superapp/objects/{superapp}/{internalObjectId}/parents", SuperAppObjectBoundary[].class, childObjectId.getSuperapp(), childObjectId.getInternalObjectId());

		// THEN the server responds with STATUs 2xx
		// AND the server returns the a list with one object with {parentObjectId}
		if (actualResponse[0] == null)
			throw new Exception("Error while validating response id");
		assertEquals(actualResponse[0].getObjectId(), parentObjectId);

	}


	@Test
	public void testRelationsBetweenParentToChild() throws Exception {
		// GIVEN the server contains two objects with ids {parentObjectId} and {childObjectId}
		// AND the object with {parentObjectId} is the parent of the object with {childObjectId}

		//create two objects - obj1, obj2
		SuperAppObjectBoundary parent = new SuperAppObjectBoundary();
		parent.setAlias("TestObj1");
		parent.setType("TEST");
		parent = this.restTemplate.postForObject(this.url + "/superapp/objects", parent, SuperAppObjectBoundary.class);

		SuperAppObjectBoundary child = new SuperAppObjectBoundary();
		child.setAlias("TestObj2");
		child.setType("TEST");
		child = this.restTemplate.postForObject(this.url + "/superapp/objects", child, SuperAppObjectBoundary.class);

		//set attributes to the parent and child ObjectIds
		ObjectId parentObjectId = new ObjectId();
		parentObjectId.setSuperapp(parent.getObjectId().getSuperapp());
		parentObjectId.setInternalObjectId(parent.getObjectId().getInternalObjectId());

		ObjectId childObjectId = new ObjectId();
		childObjectId.setSuperapp(child.getObjectId().getSuperapp());
		childObjectId.setInternalObjectId(child.getObjectId().getInternalObjectId());

		this.restTemplate.put(this.url + "/superapp/objects/{superapp}/{parentInternalId}/children",childObjectId, parent.getObjectId().getSuperapp(), parent.getObjectId().getInternalObjectId());

		// WHEN I GET /superapp/objects/{parentSuperapp}/{parentInternalObjectId}/children
		SuperAppObjectBoundary[] actualResponse;
		actualResponse = this.restTemplate.getForObject(this.url + "/superapp/objects/{superapp}/{internalObjectId}/children", SuperAppObjectBoundary[].class, parentObjectId.getSuperapp(), parentObjectId.getInternalObjectId());

		// THEN the server responds with STATUs 2xx
		// AND the server returns the a list with one object with {parentObjectId}
		if (actualResponse[0] == null)
			throw new Exception("Error while validating response id");
		assertEquals(actualResponse[0].getObjectId(), childObjectId);

	}

	/*@Test
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
	}*/

}
