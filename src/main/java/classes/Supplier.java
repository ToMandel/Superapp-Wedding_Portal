package classes;

import java.util.ArrayList;

public class Supplier {
	
	public enum eType{
		FLOWERS, PHOTOGRAPHER, DJ
	}
	
	
	private String supplierID;
	private String name;
	private eType type;
	
	
	public Supplier() {
		
	}


	public Supplier(String supplierID, String name, eType type) {
		super();
		this.supplierID = supplierID;
		this.name = name;
		this.type = type;
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


	public eType getType() {
		return type;
	}


	public void setType(eType type) {
		this.type = type;
	}
	
	
	
	
	
}
