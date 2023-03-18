package classes;

import java.util.ArrayList;
import java.util.Date;


public class Event {

		private String eventId;
		private Date date;
		private ArrayList<Supplier> suppliers;
		private Customer customer;
		private int numOfGuests;
		private int numOfTables;
		
		public Event() {
			super();
		}
		
		public Event(String eventId, Date date, ArrayList<Supplier> suppliers, Customer customer, int numOfGuests,
				int numOfTables) {
			super();
			this.eventId = eventId;
			this.date = date;
			this.suppliers = suppliers;
			this.customer = customer;
			this.numOfGuests = numOfGuests;
			this.numOfTables = numOfTables;
		}

		public String getEventId() {
			return eventId;
		}

		public void setEventId(String eventId) {
			this.eventId = eventId;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public ArrayList<Supplier> getSuppliers() {
			return suppliers;
		}

		public void setSuppliers(ArrayList<Supplier> suppliers) {
			this.suppliers = suppliers;
		}

		public Customer getCustomer() {
			return customer;
		}

		public void setCustomer(Customer customer) {
			this.customer = customer;
		}

		public int getNumOfGuests() {
			return numOfGuests;
		}

		public void setNumOfGuests(int numOfGuests) {
			this.numOfGuests = numOfGuests;
		}

		public int getNumOfTables() {
			return numOfTables;
		}

		public void setNumOfTables(int numOfTables) {
			this.numOfTables = numOfTables;
		}
		
		
		
		
		
		
}
