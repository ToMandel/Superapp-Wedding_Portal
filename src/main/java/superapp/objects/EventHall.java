package superapp.objects;

import java.util.ArrayList;

//TODO: decide if we need this object

public class EventHall {
	
	private String hallName;
	private double pricePerPerson;
	private String customerId;
	private ArrayList<String> suppliersIds;
	//TODO: manage the occupied dates somehow
	
	public EventHall() {
		super();
	}
	

	public EventHall(String hallName, double pricePerPerson, String customerId, ArrayList<String> suppliersIds) {
		super();
		this.hallName = hallName;
		this.pricePerPerson = pricePerPerson;
		this.customerId = customerId;
		this.suppliersIds = suppliersIds;
	}


	public String getHallName() {
		return hallName;
	}


	public void setHallName(String hallName) {
		this.hallName = hallName;
	}


	public double getPricePerPerson() {
		return pricePerPerson;
	}


	public void setPricePerPerson(double pricePerPerson) {
		this.pricePerPerson = pricePerPerson;
	}


	public String getCustomerId() {
		return customerId;
	}


	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}


	public ArrayList<String> getSuppliersIds() {
		return suppliersIds;
	}


	public void setSuppliersIds(ArrayList<String> suppliersIds) {
		this.suppliersIds = suppliersIds;
	}
	
	
	
	
	
	
	

}
