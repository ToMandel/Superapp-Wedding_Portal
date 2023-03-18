package classes;

import java.util.Date;

public class Order {
	
	private String orderId;
	private double price;
	private Date date;
	private Supplier supplier;
	private Customer customer;
	
	public Order() {
		super();
	}

	public Order(String orderId, double price, Date date, Supplier supplier, Customer customer) {
		super();
		this.orderId = orderId;
		this.price = price;
		this.date = date;
		this.supplier = supplier;
		this.customer = customer;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
}
