package superapp;



import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import boundries.CreatedBy;
import boundries.CustomObject;
import boundries.Location;
import boundries.ObjectId;
import boundries.UserId;

import org.springframework.web.bind.annotation.PathVariable;

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
	public CustomObject GetObject (@PathVariable("superapp")String superapp, @PathVariable("internalObjectId")String internalObjectId) {
		ObjectId objectId = new ObjectId(superapp, internalObjectId);
		String type = "Customer";
		String alias = "demo customer"; 
		Date creationTimestamp = new Date();
		boolean active = true;
		Location location = new Location(10.10, 11.11);
		CreatedBy createdBy = new CreatedBy(new UserId(superapp, "mail@demo.com"));
		Map<String, Object> objectDetails = new HashMap<String, Object>();
		objectDetails.put("name", "dean");
		objectDetails.put("phone", "0525511");
		CustomObject customObject = new CustomObject(objectId, type, alias, active, creationTimestamp, location, createdBy, objectDetails);
		return customObject;
	
	}
}
