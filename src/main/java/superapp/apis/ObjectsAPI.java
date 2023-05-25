package superapp.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import superapp.boundries.SuperAppObjectBoundary;
import superapp.logic.ObjectServiceWithPagination;

import java.util.List;

@RestController
public class ObjectsAPI {

	private ObjectServiceWithPagination objects;
	private String nameFromSpringConfig;

	@Value("${spring.application.name:defaultName}")
	public void setNameFromSpringConfig(String nameFromSpringConfig) {
		this.nameFromSpringConfig = nameFromSpringConfig;
	}

	@Autowired
	public void setObjects(ObjectServiceWithPagination objects) {
		this.objects = objects;
	}

	public ObjectsAPI() {
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(path = { "/superapp/objects/{superapp}/{internalObjectId}" }, method = {
			RequestMethod.GET }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public SuperAppObjectBoundary getSpecificObject(
			@PathVariable("superapp") String superapp,
			@PathVariable("internalObjectId") String internalObjectId,
			@RequestParam(name = "userSuperApp", required = false) String userSuperapp,
			@RequestParam(name = "userEmail", required = true) String email) {
		if (userSuperapp == null)
			userSuperapp = this.nameFromSpringConfig;
		return objects.getSpecificObject(userSuperapp,email,superapp, internalObjectId);

	}

	@CrossOrigin(origins = "*")
	@RequestMapping(path = { "/superapp/objects" }, 
	method = { RequestMethod.GET }, 
	produces = {MediaType.APPLICATION_JSON_VALUE})
	public SuperAppObjectBoundary[] getAllObjects(
			@RequestParam(name = "userSuperApp", required = false) String userSuperapp,
			@RequestParam(name = "userEmail", required = true) String email,
			@RequestParam(name = "size", required = false, defaultValue = "20") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		if (userSuperapp == null)
			userSuperapp = this.nameFromSpringConfig;
		List<SuperAppObjectBoundary> rv = this.objects.getAllObjects(userSuperapp,email,size,page);
		return rv.toArray(new SuperAppObjectBoundary[0]);

	}

	@CrossOrigin(origins = "*")
	@RequestMapping(path = { "/superapp/objects/{superapp}/{internalObjectId}" }, method = {
			RequestMethod.PUT }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public void updateObject(
			@PathVariable("superapp") String superapp, 
			@PathVariable("internalObjectId") String internalObjectId,
			@RequestBody SuperAppObjectBoundary update,
			@RequestParam(name = "userSuperApp", required = false) String userSuperapp,
			@RequestParam(name = "userEmail", required = true) String email) {
		if (userSuperapp == null)
			userSuperapp = this.nameFromSpringConfig;
		objects.updateObject(superapp, internalObjectId, update,userSuperapp, email);
	}
	
	//WORKING

	@CrossOrigin(origins = "*")
	@RequestMapping(path = { "/superapp/objects" }, method = { RequestMethod.POST }, produces = {
			MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public SuperAppObjectBoundary createObject(@RequestBody SuperAppObjectBoundary newObject) {
		return objects.createObject(newObject);
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(path = { "/superapp/objects/search/byLocation/{lat}/{lng}/{distance}" }, method = {
			RequestMethod.GET }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public SuperAppObjectBoundary[] searchObjectsByLocation(@PathVariable("lat") double lat,
			@PathVariable("lng") double lng, @PathVariable("distance") double distance,
			@RequestParam(name = "distanceUnits", required = false, defaultValue = "NEUTRAL") String distanceUnits,
			@RequestParam(name = "userSuperApp", required = false) String userSuperapp,
			@RequestParam(name = "userEmail", required = true) String email,
			@RequestParam(name = "size", required = false, defaultValue = "20") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		if (userSuperapp == null)
			userSuperapp = this.nameFromSpringConfig;
		List<SuperAppObjectBoundary> rv = this.objects.searchObjectsByLocation(userSuperapp, email, lat, lng, distance,
				distanceUnits, size, page);

		return rv.toArray(new SuperAppObjectBoundary[0]);
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(path = { "/superapp/objects/search/byType/{type}" }, method = { RequestMethod.GET }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public SuperAppObjectBoundary[] searchObjectsByType(@PathVariable("type") String type,
			@RequestParam(name = "userSuperApp", required = false) String userSuperapp,
			@RequestParam(name = "userEmail", required = true) String userEmail,
			@RequestParam(name = "size", required = false, defaultValue = "20") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		if (userSuperapp == null)
			userSuperapp = this.nameFromSpringConfig;
		return objects.searchObjectsByType(type, size, page, userSuperapp, userEmail).toArray(new SuperAppObjectBoundary[0]);
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(path = { "/superapp/objects/search/byAlias/{alias}" }, method = { RequestMethod.GET }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public SuperAppObjectBoundary[] searchObjectsByAlias(@PathVariable("alias") String alias,
			@RequestParam(name = "userSuperApp", required = false) String userSuperapp,
			@RequestParam(name = "userEmail", required = true) String userEmail,
			@RequestParam(name = "size", required = false, defaultValue = "20") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		if (userSuperapp == null)
			userSuperapp = this.nameFromSpringConfig;
		return objects.searchObjectsByAlias(alias, size, page, userSuperapp, userEmail).toArray(new SuperAppObjectBoundary[0]);
	}

}
