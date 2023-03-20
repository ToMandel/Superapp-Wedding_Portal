package superapp.objects;

import java.util.Date;

public class Order {
	
	private String customerId;
	private String supplierId;
	private double price;
	private Date date;
	
	
	public Order() {
		super();
	}
	
	public Order(String customerId, String supplierId, double price, Date date) {
		super();
		this.customerId = customerId;
		this.supplierId = supplierId;
		this.price = price;
		this.date = date;
	}


	public String getCustomerId() {
		return customerId;
	}


	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}


	public String getSupplierId() {
		return supplierId;
	}


	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}
	
	

	

}
