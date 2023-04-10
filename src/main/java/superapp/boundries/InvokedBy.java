package superapp.boundries;

public class InvokedBy {
	
	private UserId userId;

	public InvokedBy() {
		super();
	}
	

	public InvokedBy(UserId userId) {
		super();
		this.userId = userId;
	}


	public UserId getUserId() {
		return userId;
	}

	public void setUserId(UserId userId) {
		this.userId = userId;
	}


	@Override
	public String toString() {
		return "InvokedBy [userId=" + userId.toString() + "]";
	}
	
	public static InvokedBy fromString(String str) {
	    if (str == null || str.isEmpty()) {
	        return null;
	    }
	    String[] parts = str.split("\\[|\\]");
	    if (parts.length != 2) {
	        return null;
	    }
	    String userIdStr = parts[1].substring(parts[1].indexOf("=") + 1, parts[1].length() - 1);
	    UserId userId = UserId.fromString(userIdStr);
	    if (userId == null) {
	        return null;
	    }
	    return new InvokedBy(userId);
	}

}
