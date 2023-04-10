package superapp.boundries;

import java.util.Date;
import java.util.Map;
import java.text.ParseException;
import java.text.SimpleDateFormat;



public class MiniAppCommandBoundary {
	
	private CommandId commandId;
	private String command;
	private TargetObject targetObject;
	private Date invocationTimestamp;
	private InvokedBy invokedBy;
	private Map<String, Object> commandAttributes;
	
	public MiniAppCommandBoundary() {
		super();
	}
	
	
	public MiniAppCommandBoundary(CommandId commandId, String command, TargetObject targetObject,
								  Date invocationTimestamp, InvokedBy invokedBy, Map<String, Object> commandAttributes) {
		super();
		this.commandId = commandId;
		this.command = command;
		this.targetObject = targetObject;
		this.invocationTimestamp = invocationTimestamp;
		this.invokedBy = invokedBy;
		this.commandAttributes = commandAttributes;
	}

	public CommandId getCommandId() {
		return commandId;
	}

	public void setCommandId(CommandId commandId) {
		this.commandId = commandId;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public TargetObject getTargetObject() {
		return targetObject;
	}

	public void setTargetObject(TargetObject targetObject) {
		this.targetObject = targetObject;
	}

	public Date getInvocationTimestamp() {
		return invocationTimestamp;
	}

	public void setInvocationTimestamp(Date invocationTimestamp) {
		this.invocationTimestamp = invocationTimestamp;
	}

	public InvokedBy getInvokedBy() {
		return invokedBy;
	}

	public void setInvokedBy(InvokedBy invokedBy) {
		this.invokedBy = invokedBy;
	}

	public Map<String, Object> getCommandAttributes() {
		return commandAttributes;
	}
	

	public void setCommandAttributes(Map<String, Object> commandAttributes) {
		this.commandAttributes = commandAttributes;
	}
	
	
	
	public Date DateParser(String dateString) throws Exception {
	        //date string in "yyyy-MM-dd HH:mm:ss" format
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Date date = format.parse(dateString); // parse the string into a Date object
	        return date; // print the Date object
	}


	@Override
	public String toString() {
		return "MiniAppCommandBoundary [commandId=" + commandId + ", command=" + command + ", targetObject="
				+ targetObject + ", invocationTimestamp=" + invocationTimestamp + ", invokedBy=" + invokedBy
				+ ", commandAttributes=" + commandAttributes + "]";
	}
}
