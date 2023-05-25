package superapp;

import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;
import superapp.boundries.*;
import superapp.data.UserRole;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ApplicationTests {
	private RestTemplate restTemplate;
	private String url;
	private int port;
	private String nameFromSpringConfig;
	private UserBoundary adminUser;
	private UserBoundary superAppUser;

	@Value("${spring.application.name:defaultName}")
	public void setNameFromSpringConfig(String nameFromSpringConfig) {
		this.nameFromSpringConfig = nameFromSpringConfig;
	}


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
		// we need to delete miniapp commands and then superapps objects and then the
		// users
		String deleteAllCommandsPATH = "/superapp/admin/miniapp";
		String deleteAllObjectsPATH = "/superapp/admin/objects";
		String deleteAllUsersPATH = "/superapp/admin/users";
		this.restTemplate.delete(this.url + deleteAllCommandsPATH + "?userEmail=" + this.adminUser.getUserId().getEmail());
		this.restTemplate.delete(this.url + deleteAllObjectsPATH + "?userEmail=" + this.adminUser.getUserId().getEmail());
		this.restTemplate.delete(this.url + deleteAllUsersPATH + "?userEmail=" + this.adminUser.getUserId().getEmail());
	}

	@BeforeEach
	public void setUpAdminUser(){
		//get admin user if exists
		try {
			this.adminUser = this.restTemplate.getForObject(this.url + "/superapp/users/login/" +  this.nameFromSpringConfig + "/admin@mail.com", UserBoundary.class);
		}
		catch (Exception e) {
			NewUserBoundary newUser = new NewUserBoundary();
			newUser.setUsername("ADMIN");
			newUser.setRole(UserRole.ADMIN);
			newUser.setAvatar("A");
			newUser.setEmail("admin@mail.com");
			this.adminUser = this.restTemplate.postForObject(this.url + "/superapp/users", newUser, UserBoundary.class);
		}
	}

	@BeforeEach
	public void setUpSuperAppUser(){
		//get admin user if exists
		try {
			this.superAppUser = this.restTemplate.getForObject(this.url + "/superapp/users/login/" + this.nameFromSpringConfig + "/superapp@mail.com", UserBoundary.class);
		}
		catch (Exception e) {
			NewUserBoundary newUser = new NewUserBoundary();
			newUser.setUsername("SUPERAPP");
			newUser.setRole(UserRole.SUPERAPP_USER);
			newUser.setAvatar("SA");
			newUser.setEmail("superapp@mail.com");
			this.superAppUser = this.restTemplate.postForObject(this.url + "/superapp/users", newUser, UserBoundary.class);
		}
	}

//	@Test
//	public void contextLoads() {
//	}

	@Test
	public void createNewUser() {
//		Given: The server is running
		
		NewUserBoundary user = new NewUserBoundary("hello@afeka.com",UserRole.ADMIN,"Tomiix","abc");
		user = this.restTemplate
			.postForObject(this.url + "/superapp/users", user, NewUserBoundary.class);
//		When: POST request is made to APPURL/superapp/users
//		Then: The response will be status code“2XX”
//		And: A new user will be created in the database
		UserBoundary actualResponse = this.restTemplate
				.getForObject(this.url + "/superapp/users/login/" + this.nameFromSpringConfig + "/hello@afeka.com", UserBoundary.class,user.getEmail());
			
		assertThat(actualResponse)
		.usingRecursiveComparison();
			}

	@Test
	public void testRelationsBetweenChildToParent() throws Exception {
		// GIVEN the server contains two objects with ids {parentObjectId} and
		// {childObjectId}
		// AND the object with {parentObjectId} is the parent of the object with
		// {childObjectId}

		UserId userId = new UserId();
		userId.setSuperapp(this.superAppUser.getUserId().getSuperapp());
		userId.setEmail(this.superAppUser.getUserId().getEmail());

		// create two objects - obj1, obj2
		SuperAppObjectBoundary parent = new SuperAppObjectBoundary();
		parent.setAlias("TestObj1");
		parent.setType("TEST");
		parent.setCreatedBy(new CreatedBy(userId));
		parent = this.restTemplate.postForObject(this.url + "/superapp/objects", parent, SuperAppObjectBoundary.class);

		SuperAppObjectBoundary child = new SuperAppObjectBoundary();
		child.setAlias("TestObj2");
		child.setType("TEST");
		child.setCreatedBy(new CreatedBy(userId));
		child = this.restTemplate.postForObject(this.url + "/superapp/objects", child, SuperAppObjectBoundary.class);

		// set attributes to the parent and child ObjectIds
		ObjectId parentObjectId = new ObjectId();
		parentObjectId.setSuperapp(parent.getObjectId().getSuperapp());
		parentObjectId.setInternalObjectId(parent.getObjectId().getInternalObjectId());

		ObjectId childObjectId = new ObjectId();
		childObjectId.setSuperapp(child.getObjectId().getSuperapp());
		childObjectId.setInternalObjectId(child.getObjectId().getInternalObjectId());

		String bindUrl = this.url + "/superapp/objects/{superapp}/{parentInternalId}/children?userEmail=" + this.superAppUser.getUserId().getEmail();
		String getUrl = this.url + "/superapp/objects/{superapp}/{internalObjectId}/parents?userEmail=" + this.superAppUser.getUserId().getEmail();

		this.restTemplate.put(bindUrl , childObjectId,
				parent.getObjectId().getSuperapp(), parent.getObjectId().getInternalObjectId());

		// WHEN I GET /superapp/objects/{childSuperapp}/{childInternalObjectId}/parents
		SuperAppObjectBoundary[] actualResponse;
		actualResponse = this.restTemplate.getForObject(
				getUrl, SuperAppObjectBoundary[].class,
				childObjectId.getSuperapp(), childObjectId.getInternalObjectId());

		// THEN the server responds with STATUs 2xx
		// AND the server returns the a list with one object with {parentObjectId}
		if (actualResponse != null && actualResponse[0] == null)
			throw new Exception("Error while validating response id");
		assertEquals(actualResponse[0].getObjectId(), parentObjectId);
	}

	@Test
	public void testRelationsBetweenParentToChild() throws Exception {
		// GIVEN the server contains two objects with ids {parentObjectId} and
		// {childObjectId}
		// AND the object with {parentObjectId} is the parent of the object with
		// {childObjectId}

		UserId userId = new UserId();
		userId.setSuperapp(this.superAppUser.getUserId().getSuperapp());
		userId.setEmail(this.superAppUser.getUserId().getEmail());

		// create two objects - obj1, obj2
		SuperAppObjectBoundary parent = new SuperAppObjectBoundary();
		parent.setAlias("TestObj1");
		parent.setType("TEST");
		parent.setCreatedBy(new CreatedBy(userId));
		parent = this.restTemplate.postForObject(this.url + "/superapp/objects", parent, SuperAppObjectBoundary.class);

		SuperAppObjectBoundary child = new SuperAppObjectBoundary();
		child.setAlias("TestObj2");
		child.setType("TEST");
		child.setCreatedBy(new CreatedBy(userId));
		child = this.restTemplate.postForObject(this.url + "/superapp/objects", child, SuperAppObjectBoundary.class);

		// set attributes to the parent and child ObjectIds
		ObjectId parentObjectId = new ObjectId();
		parentObjectId.setSuperapp(parent.getObjectId().getSuperapp());
		parentObjectId.setInternalObjectId(parent.getObjectId().getInternalObjectId());

		ObjectId childObjectId = new ObjectId();
		childObjectId.setSuperapp(child.getObjectId().getSuperapp());
		childObjectId.setInternalObjectId(child.getObjectId().getInternalObjectId());

		String bindUrl = this.url + "/superapp/objects/{superapp}/{parentInternalId}/children?userEmail=" + this.superAppUser.getUserId().getEmail();
		String getUrl = this.url + "/superapp/objects/{superapp}/{internalObjectId}/children?userEmail=" + this.superAppUser.getUserId().getEmail();

		this.restTemplate.put(bindUrl, childObjectId,
				parent.getObjectId().getSuperapp(), parent.getObjectId().getInternalObjectId());

		// WHEN I GET
		// /superapp/objects/{parentSuperapp}/{parentInternalObjectId}/children
		SuperAppObjectBoundary[] actualResponse;
		actualResponse = this.restTemplate.getForObject(
				getUrl, SuperAppObjectBoundary[].class,
				parentObjectId.getSuperapp(), parentObjectId.getInternalObjectId());

		// THEN the server responds with STATUs 2xx
		// AND the server returns the a list with one object with {parentObjectId}
		if (actualResponse != null && actualResponse[0] == null)
			throw new Exception("Error while validating response id");
		assertEquals(actualResponse[0].getObjectId(), childObjectId);

	}

	@Test
	public void checkSuperAppUserGetAllObjects(){
		// GIVEN the server contains two objects
		// AND there is a user with SUPERAPP_USER role

		UserId userId = new UserId();
		userId.setSuperapp(this.superAppUser.getUserId().getSuperapp());
		userId.setEmail(this.superAppUser.getUserId().getEmail());


		//create two objects - obj1, obj2
		SuperAppObjectBoundary obj1 = new SuperAppObjectBoundary();
		obj1.setAlias("TestObj1");
		obj1.setType("TEST");
		obj1.setCreatedBy(new CreatedBy(userId));
		obj1 = this.restTemplate.postForObject(this.url + "/superapp/objects", obj1, SuperAppObjectBoundary.class);

		SuperAppObjectBoundary obj2 = new SuperAppObjectBoundary();
		obj2.setAlias("TestObj2");
		obj2.setType("TEST");
		obj2.setCreatedBy(new CreatedBy(userId));
		obj2 = this.restTemplate.postForObject(this.url + "/superapp/objects", obj2, SuperAppObjectBoundary.class);

		// WHEN A GET request is made to superapp/objects/{parentSuperapp}/{parentInternalObjectId}/children

	}
}
