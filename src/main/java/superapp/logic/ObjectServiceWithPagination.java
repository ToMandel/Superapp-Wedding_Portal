package superapp.logic;

import java.util.List;

import superapp.boundries.SuperAppObjectBoundary;

public interface ObjectServiceWithPagination extends RelationshipObjectsService {

	public List<SuperAppObjectBoundary> searchObjectsByLocation(double lat, double lng, double distance,
			String distanceUnits, int size, int page);

}
