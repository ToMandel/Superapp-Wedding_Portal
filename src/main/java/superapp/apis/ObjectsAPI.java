package superapp.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import superapp.boundries.SuperAppObjectBoundary;
import superapp.logic.ObjectServiceWithPagination;

import java.util.List;

@RestController
public class ObjectsAPI {

	private ObjectServiceWithPagination objects;

	@Autowired
	public void setObjects(ObjectServiceWithPagination objects) {
		this.objects = objects;
	}

	public ObjectsAPI() {
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(path = { "/superapp/objects/{superapp}/{internalObjectId}" }, method = {
			RequestMethod.GET }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public SuperAppObjectBoundary getSpecificObject(@PathVariable("superapp") String superapp,
			@PathVariable("internalObjectId") String internalObjectId) {
		return objects.getSpecificObject(superapp, internalObjectId);

	}

	@CrossOrigin(origins = "*")
	@RequestMapping(path = { "/superapp/objects" }, method = { RequestMethod.GET }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public SuperAppObjectBoundary[] getAllObjects() {
		List<SuperAppObjectBoundary> rv = this.objects.getAllObjects();
		return rv.toArray(new SuperAppObjectBoundary[0]);
		// return objects.getAllObjects();

	}

	@CrossOrigin(origins = "*")
	@RequestMapping(path = { "/superapp/objects/{superapp}/{internalObjectId}" }, method = {
			RequestMethod.PUT }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public void updateObject(@PathVariable("internalObjectId") String internalObjectId,
			@PathVariable("superapp") String superapp, @RequestBody SuperAppObjectBoundary update) {
		objects.updateObject(superapp, internalObjectId, update);
	}

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
			@RequestParam(name = "userSuperapp", required = true) String superapp,
			@RequestParam(name = "userEmail", required = true) String email,
			@RequestParam(name = "size", required = false, defaultValue = "20") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		List<SuperAppObjectBoundary> rv = this.objects.searchObjectsByLocation(lat, lng, distance, distanceUnits, size,
				page);

		return rv.toArray(new SuperAppObjectBoundary[0]);
	}

}
