package superapp.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import superapp.boundries.CommandId;
import superapp.boundries.MiniAppCommandBoundary;
import superapp.logic.MiniAppCommandService;

@RestController
public class CommandAPI {

	private MiniAppCommandService commands;

	@Autowired
	public void setCommands(MiniAppCommandService commands) {
		this.commands = commands;
	}
	
	public CommandAPI() {
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(
			path = {"/superapp/miniapp/{miniAppName}"},
			method = {RequestMethod.POST},
			produces = {MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	public Object invokeCommand (@PathVariable("miniAppName")String miniAppName, @RequestBody MiniAppCommandBoundary miniAppCommandBoundary) {
		CommandId commandId = new CommandId();
		commandId.setMiniapp(miniAppName);
		miniAppCommandBoundary.setCommandId(commandId);
		return commands.invokeCommand(miniAppCommandBoundary);
	}
	
}
