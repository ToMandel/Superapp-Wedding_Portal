package superapp.boundries;

import java.util.Objects;

public class ObjectId {

	private String superapp;
	private String internalObjectId;
	private static int id;
	


	public ObjectId() {
		super();
		//String internalId = Integer.toString(id);
		//id++;
		//this.internalObjectId = internalId;
	}

	/*
	public ObjectId(String superapp) {
		super();
		this.superapp = superapp;
		String internalId = Integer.toString(id);
		id++;
		this.internalObjectId = internalId;
	}*/

	
	public ObjectId(String superapp, String internalObjectId) {
		super();
		this.superapp = superapp;
		this.internalObjectId = internalObjectId;
	}
	
	public String getSuperapp() {
		return superapp;
	}
	
	public void setSuperapp(String superapp) {
		this.superapp = superapp;
	}
	
	public String getInternalObjectId() {
		return internalObjectId;
	}
	
	public void setInternalObjectId(String internalObjectId) {
		this.internalObjectId = internalObjectId;
	}
	
	@Override
	public String toString() {
		return "ObjectId [superapp=" + superapp + ", internalObjectId=" + internalObjectId + "]";
	}
	
	public static ObjectId objectIdFromString(String str) {
	    String arr[] = str.split("#");
		if (arr.length != 2)
			return null;
		ObjectId objectId = new ObjectId();
		objectId.setSuperapp(arr[0]);
		objectId.setInternalObjectId(arr[1]);
		return objectId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ObjectId objectId = (ObjectId) o;
		return superapp.equals(objectId.superapp) && internalObjectId.equals(objectId.internalObjectId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(superapp, internalObjectId);
	}
}
