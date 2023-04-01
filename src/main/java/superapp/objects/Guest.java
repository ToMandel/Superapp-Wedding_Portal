package superapp.objects;

public class Guest {
	
	public enum eGuestType {
		HUSBAND_CLOSE_FAMILY, WIFE_CLOSE_FAMILY,
		HUSBAND_FAR_FAMILY, WIFE_FAR_FAMILY,
		HUSBAND_WORK, WIFE_WORK,
		HUSBAND_FRIEND, WIFE_FRIEND 
	}
	
	private String eventId;
	private String tableId;
	private eGuestType guestType;
	private boolean attending;
	
	
	public Guest() {
		super();
	}

	public Guest(String eventId, String tableId, eGuestType guestType, boolean attending) {
		super();
		this.eventId = eventId;
		this.tableId = tableId;
		this.guestType = guestType;
		this.attending = attending;
	}


	public String getEventId() {
		return eventId;
	}

	
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	
	public String getTableId() {
		return tableId;
	}


	public void setTableId(String tableId) {
		this.tableId = tableId;
	}


	public eGuestType getGuestType() {
		return guestType;
	}


	public void setGuestType(eGuestType guestType) {
		this.guestType = guestType;
	}


	public boolean getAttending() {
		return attending;
	}


	public void setAttending(boolean attending) {
		this.attending = attending;
	}
	
	
	
	
	

}
