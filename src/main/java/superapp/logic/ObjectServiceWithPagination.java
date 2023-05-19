package superapp.logic;

import java.util.List;

import superapp.boundries.SuperAppObjectBoundary;

public interface ObjectServiceWithPagination extends RelationshipObjectsService {

	public List<SuperAppObjectBoundary> searchObjectsByLocation(String superAppName, String email,double lat, double lng, double distance,
			String distanceUnits, int size, int page);

	public List<SuperAppObjectBoundary> searchObjectsByType (String type, int size, int page, String superAppName,  String email);

	public List<SuperAppObjectBoundary> searchObjectsByAlias (String type, int size, int page, String superAppName, String email);

	public void deleteAllObjects (String superAppName, String email);
	
	public SuperAppObjectBoundary updateObject(String objectSuperApp, String internalObjectId, SuperAppObjectBoundary update,
			String userSuperapp, String email);
	
	  public List<SuperAppObjectBoundary> getAllObjects(String userSuperapp, String email, int size, int page);
	  
	  public SuperAppObjectBoundary getSpecificObject(String userSuperapp, String email,String objectSuperApp, String internalObjectId);

}
