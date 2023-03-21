package superapp.objects;

import java.util.ArrayList;

public class Table {
	
	private ArrayList<Guest> tableGusets;
	private int maxGuests;
	
	
	public Table() {
		super();
	}
	
	public Table(int maxGuests) {
		super();
		this.maxGuests = maxGuests;
		this.tableGusets = new ArrayList<Guest>();
	}

	public ArrayList<Guest> getTableGusets() {
		return tableGusets;
	}

	public void setTableGusets(ArrayList<Guest> tableGusets) {
		this.tableGusets = tableGusets;
	}

	public int getMaxGuests() {
		return maxGuests;
	}

	public void setMaxGuests(int maxGuests) {
		this.maxGuests = maxGuests;
	}
	
}
