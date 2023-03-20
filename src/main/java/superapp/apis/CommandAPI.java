package superapp.apis;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import superapp.boundries.MiniAppCommandObject;
import superapp.boundries.User;
import superapp.boundries.UserId;

@RestController
public class CommandAPI {
	
	public CommandAPI() {
	}
	

	@RequestMapping(
			path = {"/superapp/miniapp/{MiniAppName}"},
			method = {RequestMethod.POST},
			produces = {MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
		public MiniAppCommandObject InvokeCommand (@PathVariable("MiniAppName")String MiniAppName, @RequestBody MiniAppCommandObject miniAppCommandObject) {
			return miniAppCommandObject;
		}
	
}
