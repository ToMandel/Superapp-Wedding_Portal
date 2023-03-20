package superapp.objects;

import java.util.ArrayList;


public class Supplier{
	
	public enum eServiceType{
		FLOWERS, PHOTOGRAPHER, DJ
		//TODO: add types
	}
	

	private String name;
	private eServiceType serviceType;
	//TODO: manage the occupied dates somehow
	
	
	public Supplier() {
		
	}

	public Supplier(String name, eServiceType serviceType) {
		super();
		this.name = name;
		this.serviceType = serviceType;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public eServiceType getServiceType() {
		return serviceType;
	}


	public void setServiceType(eServiceType serviceType) {
		this.serviceType = serviceType;
	}
	
}
