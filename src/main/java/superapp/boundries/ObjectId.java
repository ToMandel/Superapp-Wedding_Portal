package superapp.boundries;

public class ObjectId {
	
	

	private String superapp;
	private String internalObjectId;
	private static int id;
	
	
	public ObjectId() {
		super();
	}
	
	public ObjectId(String superapp) {
		super();
		this.superapp = superapp;
		String internalId = Integer.toString(id);
		id++;
		this.internalObjectId = internalId;
	}
	
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
	

}
