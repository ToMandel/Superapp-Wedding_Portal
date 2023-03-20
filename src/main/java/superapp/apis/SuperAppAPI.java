package superapp.apis;



import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import superapp.boundries.CreatedBy;
import superapp.boundries.CustomObject;
import superapp.boundries.Location;
import superapp.boundries.ObjectId;
import superapp.boundries.User;
import superapp.boundries.UserId;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SuperAppAPI {
	
	public SuperAppAPI() {
	}
	
	@RequestMapping(
		path = {"/superapp/objects/{superapp}/{internalObjectId}"},
		method = {RequestMethod.GET},
		produces = {MediaType.APPLICATION_JSON_VALUE})
	public CustomObject GetObjectById (@PathVariable("superapp")String superapp, @PathVariable("internalObjectId")String internalObjectId) {
		ObjectId objectId = new ObjectId(superapp, internalObjectId);
		String type = "Customer";
		String alias = "demo customer"; 
		Date creationTimestamp = new Date();
		boolean active = true;
		Location location = new Location(10.10, 11.11);
		CreatedBy createdBy = new CreatedBy(new UserId(superapp, "e-mail@demo.com"));
		Map<String, Object> objectDetails = new HashMap<String, Object>();
		objectDetails.put("name", "liri");
		objectDetails.put("phone", "0525511");
		CustomObject customObject = new CustomObject(objectId, type, alias, active, creationTimestamp, location, createdBy, objectDetails);
		return customObject;
	
	}
	
	
	@RequestMapping(
			path = {"/superapp/objects"},
			method = {RequestMethod.GET},
			produces = {MediaType.APPLICATION_JSON_VALUE})
		public ArrayList<CustomObject> GetAllObjects () {
			String superapp = "wedding";
			ArrayList<CustomObject> list = new ArrayList<CustomObject>();
			for (int i = 0; i < 3; i++) {
				String internalObjectId = i + "";
				ObjectId objectId = new ObjectId(superapp, internalObjectId);
				String type = "Customer";
				String alias = "customer" + i; 
				Date creationTimestamp = new Date();
				boolean active = true;
				Location location = new Location(10.10, 11.11);
				CreatedBy createdBy = new CreatedBy(new UserId(superapp, "e-mail@demo.com"));
				Map<String, Object> objectDetails = new HashMap<String, Object>();
				objectDetails.put("name", "liri");
				objectDetails.put("phone", "0525511");
				list.add(new CustomObject(objectId, type, alias, active, creationTimestamp, location, createdBy, objectDetails));
			}
			return list;
		
		}
	@RequestMapping(
			path = {"/superapp/objects/{superapp}/{internalObjectId}"},
			method = {RequestMethod.PUT},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void updateSpecificObject (
			@PathVariable("internalObjectId") String internalObjectId,
			@PathVariable("superapp") String superapp,
			@RequestBody ObjectId update) {
		System.err.println("internalObjectId: " + internalObjectId);
		System.err.println("update: " + update);
	}
	@RequestMapping(
			path = {"/superapp/objects"},
			method = {RequestMethod.POST},
			produces = {MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
		public CustomObject CreateNewObject (@RequestBody CustomObject newObject) {
		ObjectId objectId = newObject.getObjectId();
		String type= newObject.getType();
		String alias=newObject.getAlias();
		boolean active=newObject.isActive();
	    Date creationTimestamp=newObject.getCreationTimestamp();
	    Location location=newObject.getLocation();
	    CreatedBy createdBy=newObject.getCreatedBy();
	    Map<String, Object> objectDetails=newObject.getObjectDetails();
			
		 CustomObject o = new CustomObject( objectId,  type,  alias, active,  creationTimestamp,
					 location,  createdBy,objectDetails);
			return o;
		}
	
}
