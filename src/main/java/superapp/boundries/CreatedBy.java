package superapp.boundries;

public class CreatedBy {
	
	private UserId userId;

	
	public CreatedBy() {
		super();
	}


	public CreatedBy(UserId userId) {
		super();
		this.userId = userId;
	}


	public UserId getUserId() {
		return userId;
	}


	public void setUserId(UserId userId) {
		this.userId = userId;
	}

	public static CreatedBy createdByFromString(String str) {
		String arr[] = str.split("#");
		if (arr.length != 2)
			return null;
		UserId userId = new UserId();
		userId.setSuperapp(arr[0]);
		userId.setEmail(arr[1]);
		return new CreatedBy(userId);
	}
	
	
	
	

}
