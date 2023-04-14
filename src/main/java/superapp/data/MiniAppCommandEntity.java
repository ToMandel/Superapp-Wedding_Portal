package superapp.data;

import java.util.Date;
import java.util.Map;


import jakarta.persistence.*;
import superapp.boundries.CommandId;
import superapp.boundries.InvokedBy;
import superapp.boundries.TargetObject;

@Entity
@Table(name = "MINI_APPS")
public class MiniAppCommandEntity {
	@Id
	////TODO: change id to userId
	private String commandId;
	private String command;
	private String targetObject;
	@Temporal(TemporalType.TIMESTAMP)
	private Date invocationTimestamp;
	private String invokedBy;
	@Lob
	private String commandAttributes;



	public MiniAppCommandEntity()
	{
		
	}
	
	
	public String getCommandId() {
		return commandId;
	}
	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}


	public String getInvokedBy() {
		return invokedBy;
	}
	public void setInvokedBy(String invokedBy) {
		this.invokedBy = invokedBy;
	}
	public Date getInvocationTimestamp() {
		return invocationTimestamp;
	}
	public void setInvocationTimestamp(Date invocationTimestamp) {
		this.invocationTimestamp = invocationTimestamp;
	}
	public String getTargetObject() {
		return targetObject;
	}
	public void setTargetObject(String targetObject) {
		this.targetObject = targetObject;
	}
	
	
	public String getCommandAttributes() {
		return commandAttributes;
	}


	public void setCommandAttributes(String commandAttributes) {
		this.commandAttributes = commandAttributes;
	}
	
	@Override
	public String toString() {
		return "MiniAppCommandEntity [commandId=" + commandId + ", command=" + command + ", targetObject="
				+ targetObject + ", invocationTimestamp=" + invocationTimestamp + ", invokedBy=" + invokedBy
				+ ", commandAttributes=" + commandAttributes + "]";
	}



}
