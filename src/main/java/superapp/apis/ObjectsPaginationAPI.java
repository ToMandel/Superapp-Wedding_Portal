package superapp.apis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import superapp.boundries.SuperAppObjectBoundary;
import superapp.logic.ObjectServiceWithPagination;

public class ObjectsPaginationAPI {
	

	private ObjectServiceWithPagination objects;

	@Autowired
	public void setObjects(ObjectServiceWithPagination objects) {
		this.objects = objects;
	}
	
	public ObjectsPaginationAPI() {
	}
	
	@CrossOrigin(origins = "*")	
	@RequestMapping(
		path = {"/superapp/objects/search/byLocation/{lat}/{lng}/{distance}"},
		method = {RequestMethod.GET},
		produces = {MediaType.APPLICATION_JSON_VALUE})
	public SuperAppObjectBoundary[] searchObjectsByLocation (
		@PathVariable("lat")double lat,
		@PathVariable("lng")double lng,
		@PathVariable("distance")double distance,
		@RequestParam(name = "distanceUnits", required = false, defaultValue = "NEUTRAL") String distanceUnits,
		@RequestParam(name = "userSuperapp", required = false, defaultValue = "2023b.zohar.tzabari") String superapp,
		@RequestParam(name = "userEmail", required = true) String email,
		@RequestParam(name = "size", required = false, defaultValue = "20") int size,
		@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		List<SuperAppObjectBoundary> rv = this.objects.searchObjectsByLocation
				(lat,lng,distance,distanceUnits,superapp,email,size,page);
	
	return rv.toArray(new SuperAppObjectBoundary[0]);
}

}
