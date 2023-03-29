package superapp.data;

import superapp.boundries.SuperAppObjectBoundary;
import superapp.logic.ObjectsService;

import java.util.List;

public class SuperAppObjectEntity implements ObjectsService {

    @Override
    public SuperAppObjectBoundary createObject(SuperAppObjectBoundary object) {
        return null;
    }

    @Override
    public SuperAppObjectBoundary updateObject(String objectSuperApp, String internalObjectId, SuperAppObjectBoundary update) {
        return null;
    }

    @Override
    public SuperAppObjectBoundary getSpecificObject(String objectSuperApp, String internalId) {
        return null;
    }

    @Override
    public List<SuperAppObjectBoundary> getAllObjects() {
        return null;
    }

    @Override
    public void deleteAllObjects() {

    }
}
