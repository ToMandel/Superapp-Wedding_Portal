package superapp.apis;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import superapp.boundries.MiniAppCommandBoundary;
import superapp.logic.MiniAppCommandService;

@RestController
public class CommandAPI {

	private MiniAppCommandService commands;
	
	public CommandAPI() {
	}
	

	@RequestMapping(
			path = {"/superapp/miniapp/{MiniAppName}"},
			method = {RequestMethod.POST},
			produces = {MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	public Object invokeCommand (@PathVariable("MiniAppName")String MiniAppName, @RequestBody MiniAppCommandBoundary miniAppCommandBoundary) {
			//miniAppCommandObject.setInvocationTimestamp(new Date());
			//return miniAppCommandObject;
		return commands.invokeCommand(miniAppCommandBoundary);
	}
	
}
