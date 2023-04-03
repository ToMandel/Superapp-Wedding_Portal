package superapp.apis;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import superapp.boundries.SuperAppObjectBoundary;
import superapp.logic.ObjectsService;

import java.util.List;

@RestController
public class ObjectsAPI {

	private ObjectsService objects;

	//TODO: define autowired function
	
	public ObjectsAPI() {
	}
	
	@RequestMapping(
		path = {"/superapp/objects/{superapp}/{internalObjectId}"},
		method = {RequestMethod.GET},
		produces = {MediaType.APPLICATION_JSON_VALUE})
	public SuperAppObjectBoundary getSpecificObject (@PathVariable("superapp")String superapp, @PathVariable("internalObjectId")String internalObjectId) {
		/*ObjectId objectId = new ObjectId(superapp, internalObjectId);
		String type = "Customer";
		String alias = "demo customer"; 
		Date creationTimestamp = new Date();
		boolean active = true;
		Location location = new Location(10.10, 11.11);
		CreatedBy createdBy = new CreatedBy(new UserId(superapp, "e-mail@demo.com"));
		Map<String, Object> objectDetails = new HashMap<String, Object>();
		objectDetails.put("name", "liri");
		objectDetails.put("phone", "0525511");
		SuperAppObjectBoundary customObject = new SuperAppObjectBoundary(objectId, type, alias, active, creationTimestamp, location, createdBy, objectDetails);
		return customObject;*/
		return objects.getSpecificObject(superapp, internalObjectId);
	
	}
	
	
	@RequestMapping(
			path = {"/superapp/objects"},
			method = {RequestMethod.GET},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<SuperAppObjectBoundary> getAllObjects () {
		/*String superapp = "wedding";
		ArrayList<SuperAppObjectBoundary> list = new ArrayList<SuperAppObjectBoundary>();
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
			list.add(new SuperAppObjectBoundary(objectId, type, alias, active, creationTimestamp, location, createdBy, objectDetails));
		}
		return list;*/
		return objects.getAllObjects();
		
	}
	@RequestMapping(
			path = {"/superapp/objects/{superapp}/{internalObjectId}"},
			method = {RequestMethod.PUT},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void updateObject (@PathVariable("internalObjectId") String internalObjectId,
										@PathVariable("superapp") String superapp,
										@RequestBody SuperAppObjectBoundary update) {
		//System.err.println("internalObjectId: " + internalObjectId);
		//System.err.println("update: " + update);
		objects.updateObject(superapp, internalObjectId, update);
	}
	@RequestMapping(
			path = {"/superapp/objects"},
			method = {RequestMethod.POST},
			produces = {MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	public SuperAppObjectBoundary createObject (@RequestBody SuperAppObjectBoundary newObject) {
		/*ObjectId objectId = new ObjectId();
		String type = newObject.getType();
		String alias = newObject.getAlias();
		Boolean active= newObject.GetActive();
	    Date creationTimestamp = new Date();
	    Location location = newObject.getLocation();
	    CreatedBy createdBy = newObject.getCreatedBy();
	    Map<String, Object> objectDetails = newObject.getObjectDetails();
			
		 SuperAppObjectBoundary o = new SuperAppObjectBoundary( objectId,  type,  alias, active,  creationTimestamp,
					 location,  createdBy,objectDetails);
			return o;*/
		return objects.createObject(newObject);
	}
	
}
