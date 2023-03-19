package classes;

import boundries.CustomObject;
import boundries.ObjectId;

public class Rating extends CustomObject{
	
	private ObjectId customerId;
	private ObjectId supplierId;
	private double professionalityRate;
	private double availabilityRate;
	private double serviceRate;
	private double valueForMoneyRate;
	private double finalRate;
	
	
	public Rating() {
		super();
	}
	
	
	
	public Rating(ObjectId customerId, ObjectId supplierId, double professionalityRate, double availabilityRate,
			double serviceRate, double valueForMoneyRate) {
		super();
		this.customerId = customerId;
		this.supplierId = supplierId;
		this.professionalityRate = professionalityRate;
		this.availabilityRate = availabilityRate;
		this.serviceRate = serviceRate;
		this.valueForMoneyRate = valueForMoneyRate;
		this.finalRate = setFinalRate();
	}



	public ObjectId getCustomerId() {
		return customerId;
	}
	public void setCustomerId(ObjectId customerId) {
		this.customerId = customerId;
	}
	public ObjectId getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(ObjectId supplierId) {
		this.supplierId = supplierId;
	}
	public double getProfessionalityRate() {
		return professionalityRate;
	}
	public void setProfessionalityRate(double professionalityRate) {
		this.professionalityRate = professionalityRate;
	}
	public double getAvailabilityRate() {
		return availabilityRate;
	}
	public void setAvailabilityRate(double availabilityRate) {
		this.availabilityRate = availabilityRate;
	}
	public double getServiceRate() {
		return serviceRate;
	}
	public void setServiceRate(double serviceRate) {
		this.serviceRate = serviceRate;
	}
	public double getValueForMoneyRate() {
		return valueForMoneyRate;
	}
	public void setValueForMoneyRate(double valueForMoneyRate) {
		this.valueForMoneyRate = valueForMoneyRate;
	}
	public double getFinalRate() {
		return finalRate;
	}
	public double setFinalRate() {
		return (this.availabilityRate + this.serviceRate + this.professionalityRate + this.valueForMoneyRate) / 4;
	}
	
	
	
	
	
	

}
