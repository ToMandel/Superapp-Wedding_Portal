package superapp.objects;

import java.util.ArrayList;
import java.util.Date;


public class Event {

		//private String eventId;
		private Date date;
		private ArrayList<String> suppliersIds;
		private String customerId;
		private int numOfGuests;
		private int numOfTables;
		

		public Event() {
			super();
		}


		public Event(Date date, ArrayList<String> suppliersIds, String customerId, int numOfGuests, int numOfTables) {
			super();
			this.date = date;
			this.suppliersIds = suppliersIds;
			this.customerId = customerId;
			this.numOfGuests = numOfGuests;
			this.numOfTables = numOfTables;
		}


		public Date getDate() {
			return date;
		}


		public void setDate(Date date) {
			this.date = date;
		}


		public ArrayList<String> getSuppliersIds() {
			return suppliersIds;
		}


		public void setSuppliersIds(ArrayList<String> suppliersIds) {
			this.suppliersIds = suppliersIds;
		}


		public String getCustomerId() {
			return customerId;
		}


		public void setCustomerId(String customerId) {
			this.customerId = customerId;
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
