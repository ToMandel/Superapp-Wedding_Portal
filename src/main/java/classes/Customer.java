package classes;

import boundries.ObjectId;

public class Customer {
	
	private ObjectId objectId;
	private String name;
	private String mail;
	private String phone;
	private int budget;
	

	public Customer() {
		
	}


	public Customer(ObjectId objectId, String name, String mail, String phone, int budget) {
		super();
		this.objectId = objectId;
		this.name = name;
		this.mail = mail;
		this.phone = phone;
		this.budget = budget;
	}



	public ObjectId getObjectId() {
		return objectId;
	}



	public void setObjectId(ObjectId objectId) {
		this.objectId = objectId;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getBudget() {
		return budget;
	}

	public void setBudget(int budget) {
		this.budget = budget;
	}
	
	

	
	
	
	
	
	
}
