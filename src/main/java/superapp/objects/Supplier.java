package superapp.objects;

import java.util.ArrayList;

import superapp.boundries.CustomObject;

public class Supplier extends CustomObject{
	
	public enum eServiceType{
		FLOWERS, PHOTOGRAPHER, DJ
	}
	
	
	private String supplierID;
	private String name;
	private eServiceType serviceType;
	
	
	public Supplier() {
		
	}

	public Supplier(String supplierID, String name, eServiceType serviceType) {
		super();
		this.supplierID = supplierID;
		this.name = name;
		this.serviceType = serviceType;
	}


	public String getSupplierID() {
		return supplierID;
	}

	public void setSupplierID(String supplierID) {
		this.supplierID = supplierID;
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
