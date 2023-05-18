package superapp.logic;

import superapp.boundries.SuperAppObjectBoundary;

import java.util.List;

public interface ObjectsService {

    public SuperAppObjectBoundary createObject(SuperAppObjectBoundary object);
    
    @Deprecated
    public SuperAppObjectBoundary updateObject (String objectSuperApp, String internalObjectId, SuperAppObjectBoundary update);

    public SuperAppObjectBoundary getSpecificObject (String objectSuperApp, String internalObjectId);

    public List<SuperAppObjectBoundary> getAllObjects();

    @Deprecated
    public void deleteAllObjects();


}
