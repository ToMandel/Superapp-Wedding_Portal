package superapp.boundries;

public class CommandId {
	
	private String superapp;
	private String miniapp;
	private String internalCommandId;
	
	public CommandId () {
	}

	public CommandId(String superapp, String miniapp, String internalCommandId) {
		super();
		this.superapp = superapp;
		this.miniapp = miniapp;
		this.internalCommandId = internalCommandId;
	}

	public String getSuperapp() {
		return superapp;
	}

	public void setSuperapp(String superapp) {
		this.superapp = superapp;
	}

	public String getMiniapp() {
		return miniapp;
	}

	public void setMiniapp(String miniapp) {
		this.miniapp = miniapp;
	}

	public String getInternalCommandId() {
		return internalCommandId;
	}

	public void setInternalCommandId(String internalCommandId) {
		this.internalCommandId = internalCommandId;
	}

	@Override
	public String toString() {
		return "CommandId [superapp=" + superapp + ", miniapp=" + miniapp + ", internalCommandId=" + internalCommandId
				+ "]";
	}
	
	public static CommandId commandIdFromString(String str) {
	    CommandId commandId = new CommandId();
		String arr[] = str.split("#");
		if (arr.length != 3)
			return null;
		commandId.setSuperapp(arr[0]);
		commandId.setMiniapp(arr[1]);
		commandId.setInternalCommandId(arr[2]);
	    /*int startIndex = str.indexOf("[");
	    int endIndex = str.indexOf("]");
	    if (startIndex == -1 || endIndex == -1 || endIndex <= startIndex) {
	        // Invalid string format
	        return null;
	    }
	    str = str.substring(startIndex + 1, endIndex); // Extract field values from within square brackets
	    String[] fields = str.split(",");
	    for (String field : fields) {
	        String[] parts = field.trim().split("=");
	        if (parts.length != 2) {
	            // Invalid field format
	            return null;
	        }
	        String fieldName = parts[0].trim().toLowerCase();
	        String fieldValue = parts[1].trim();
	        switch (fieldName) {
	            case "superapp":
	                commandId.setSuperapp(fieldValue);
	                break;
	            case "miniapp":
	                commandId.setMiniapp(fieldValue);
	                break;
	            case "internalcommandid":
	                commandId.setInternalCommandId(fieldValue);
	                break;
	            default:
	                // Unknown field
	                return null;
	        }
	    }*/
	    return commandId;
	}
	
	

}
