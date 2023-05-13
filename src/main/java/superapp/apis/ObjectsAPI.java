package superapp.apis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import superapp.boundries.SuperAppObjectBoundary;
import superapp.logic.ObjectsService;

import java.util.List;

@RestController
public class ObjectsAPI {

	private ObjectsService objects;

	@Autowired
	public void setObjects(ObjectsService objects) {
		this.objects = objects;
	}
	
	public ObjectsAPI() {
	}
	
	@CrossOrigin(origins = "*")	
	@RequestMapping(
		path = {"/superapp/objects/{superapp}/{internalObjectId}"},
		method = {RequestMethod.GET},
		produces = {MediaType.APPLICATION_JSON_VALUE})
	public SuperAppObjectBoundary getSpecificObject (@PathVariable("superapp")String superapp, @PathVariable("internalObjectId")String internalObjectId) {
		return objects.getSpecificObject(superapp, internalObjectId);
	
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(
			path = {"/superapp/objects"},
			method = {RequestMethod.GET},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public SuperAppObjectBoundary[] getAllObjects () {
		List<SuperAppObjectBoundary> rv = this.objects.getAllObjects();
		return rv.toArray(new SuperAppObjectBoundary[0]);
		//return objects.getAllObjects();
		
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(
			path = {"/superapp/objects/{superapp}/{internalObjectId}"},
			method = {RequestMethod.PUT},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void updateObject (@PathVariable("internalObjectId") String internalObjectId,
										@PathVariable("superapp") String superapp,
										@RequestBody SuperAppObjectBoundary update) {
		objects.updateObject(superapp, internalObjectId, update);
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(
			path = {"/superapp/objects"},
			method = {RequestMethod.POST},
			produces = {MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	public SuperAppObjectBoundary createObject (@RequestBody SuperAppObjectBoundary newObject) {
		return objects.createObject(newObject);
	}

	
}
