package superapp.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import superapp.boundries.CommandId;
import superapp.boundries.MiniAppCommandBoundary;
import superapp.logic.MiniAppCommandServiceWithAsyncSupport;

@RestController
public class CommandAPI {

	private MiniAppCommandServiceWithAsyncSupport commands;

	@Autowired
	public void setCommands(MiniAppCommandServiceWithAsyncSupport commands) {
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
	public Object invokeCommandAsync (@PathVariable("miniAppName")String miniAppName, @RequestBody MiniAppCommandBoundary miniAppCommandBoundary,
									  @RequestParam(name = "async", required = false, defaultValue = "false") boolean async) {
		CommandId commandId = new CommandId();
		commandId.setMiniapp(miniAppName);
		miniAppCommandBoundary.setCommandId(commandId);
		if (async)
			return commands.invokeMiniAppCommandAsync(miniAppCommandBoundary, true);
		return commands.invokeMiniAppCommandAsync(miniAppCommandBoundary, false);
		//return commands.invokeCommand(miniAppCommandBoundary);
	}
	
}
