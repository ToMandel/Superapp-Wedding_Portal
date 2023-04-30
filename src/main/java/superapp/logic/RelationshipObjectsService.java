package superapp.logic;

import superapp.boundries.ObjectId;
import superapp.boundries.SuperAppObjectBoundary;

public interface RelationshipObjectsService extends ObjectsService{

    public void relateOriginToChild (ObjectId objectId);

    public SuperAppObjectBoundary[] getAllChildrenOfObject();

    public SuperAppObjectBoundary[] getAllParentsOfObject();
}
