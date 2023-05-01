package superapp.logic;

import superapp.boundries.ObjectId;
import superapp.boundries.SuperAppObjectBoundary;

import java.util.List;

public interface RelationshipObjectsService extends ObjectsService{

    public void relateParentToChild (ObjectId parent, ObjectId child);

    public List<SuperAppObjectBoundary> getAllChildrenOfObject(ObjectId parent);

    public List<SuperAppObjectBoundary> getAllParentsOfObject(ObjectId child);
}
