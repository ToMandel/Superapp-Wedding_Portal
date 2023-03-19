package superapp.apis;



import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import superapp.boundries.CreatedBy;
import superapp.boundries.CustomObject;
import superapp.boundries.Location;
import superapp.boundries.ObjectId;
import superapp.boundries.UserId;

import org.springframework.web.bind.annotation.PathVariable;

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
}
