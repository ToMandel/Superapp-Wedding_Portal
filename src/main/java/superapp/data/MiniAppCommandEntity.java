package superapp.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MINI_APPS")
public class MiniAppCommandEntity {
	@Id
	////TODO: change id to userId
	private String commandId;
	private String command;
	
	
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
	@Override
	public String toString() {
		return "MiniAppCommandEntity [commandId=" + commandId + ", command=" + command + "]";
	}
	
	
	
	
	
	
	



}
