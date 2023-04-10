package superapp.boundries;

public class TargetObject {
	
	private ObjectId objectId;

	public TargetObject() {
		super();
	}

	public TargetObject(ObjectId objectId) {
		super();
		this.objectId = objectId;
	}

	public ObjectId getObjectId() {
		return objectId;
	}

	public void setObjectId(ObjectId objectId) {
		this.objectId = objectId;
	}

	@Override
	public String toString() {
		return "TargetObject [objectId=" + objectId.toString() + "]";
	}
	
	public static TargetObject fromString(String str) {
	    if (str == null || str.isEmpty()) {
	        return null;
	    }
	    String[] parts = str.split("\\[|\\]");
	    if (parts.length != 2) {
	        return null;
	    }
	    String objectIdStr = parts[1].substring(parts[1].indexOf("=") + 1, parts[1].length() - 1);
	    ObjectId objectId = ObjectId.fromString(objectIdStr);
	    if (objectId == null) {
	        return null;
	    }
	    return new TargetObject(objectId);
	}
	
	
}
