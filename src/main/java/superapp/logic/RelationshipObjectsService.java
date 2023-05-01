package superapp.logic;

import superapp.boundries.ObjectId;
import superapp.boundries.SuperAppObjectBoundary;

public interface RelationshipObjectsService extends ObjectsService{

    public void relateOriginToChild (ObjectId parent, ObjectId child);

    public SuperAppObjectBoundary[] getAllChildrenOfObject(ObjectId parent);

    public SuperAppObjectBoundary[] getAllParentsOfObject(ObjectId child);
}
