package superapp.boundries;

public class ObjectId {
	
	private String superapp;
	private String internalObjectId;
	
	
	public ObjectId() {
		super();
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
	
	
	

}
