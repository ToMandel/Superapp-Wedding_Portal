package superapp.logic;

import superapp.boundries.ObjectId;
import superapp.boundries.SuperAppObjectBoundary;

import java.util.List;

public interface RelationshipObjectsService extends ObjectsService{
	
	@Deprecated
    public void relateParentToChild (ObjectId parentObjectId, ObjectId childrenObjectId);

    public List<SuperAppObjectBoundary> getAllChildrenOfObject(ObjectId parent);

    public List<SuperAppObjectBoundary> getAllParentsOfObject(ObjectId child);
}
