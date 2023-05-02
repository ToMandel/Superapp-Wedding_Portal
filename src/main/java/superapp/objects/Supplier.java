package superapp.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Supplier{

	public enum eServiceType{
		FLOWERS, PHOTOGRAPHER, DJ
		//TODO: add types
	}


	private String name;
	private eServiceType serviceType;
	private ArrayList<String> eventIds;
	private Rating rating;
	//TODO: manage the occupied dates somehow


	public Supplier() {

	}

	public Supplier(String name, eServiceType serviceType) {
		super();
		this.name = name;
		this.serviceType = serviceType;
		this.eventIds = new ArrayList<String>();
		this.rating = new Rating();
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public eServiceType getServiceType() {
		return serviceType;
	}


	public void setServiceType(eServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public ArrayList<String> getEventIds() {
		return eventIds;
	}

	public void setEventIds(ArrayList<String> eventIds) {
		this.eventIds = eventIds;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public static List<String> getAllTypes(){
		List<String> types = Arrays.stream(eServiceType.values())
				.map(Enum::name)
				.collect(Collectors.toList());
		return types;
	}


}
