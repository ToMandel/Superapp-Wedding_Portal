package superapp.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import superapp.boundries.MiniAppCommandBoundary;
import superapp.boundries.UserBoundary;
import superapp.logic.MiniAppCommandService;
import superapp.logic.ObjectsService;
import superapp.logic.UsersService;

import java.util.List;

@RestController
public class AdminAPI {

	private ObjectsService objects;
	private MiniAppCommandService commands;
	private UsersService users;

	@Autowired
	public void setUsers(UsersService users) {
		this.users = users;
	}

	@Autowired
	public void setObjects(ObjectsService objects) {
		this.objects = objects;
	}

	@Autowired
	public void setCommands(MiniAppCommandService commands) {
		this.commands = commands;
	}

	
	public AdminAPI() {
	}
	
	
	@RequestMapping(
			path = {"/superapp/admin/users"},
			method = {RequestMethod.GET},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public UserBoundary[] getAllUsers () {
		List<UserBoundary> rv = this.users.getAllUsers();
		//return users.getAllUsers();
		return rv.toArray(new UserBoundary[0]);
	}
	
	@RequestMapping(
			path = {"/superapp/admin/miniapp"},
			method = {RequestMethod.GET},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public MiniAppCommandBoundary[] getAllCommands () {
		List<MiniAppCommandBoundary> rv = this.commands.getAllCommands();
		//return commands.getAllCommands();
		return rv.toArray(new MiniAppCommandBoundary[0]);
	}
	
	
	@RequestMapping(
			path = {"/superapp/admin/miniapp/{MiniAppName}"},
			method = {RequestMethod.GET},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public MiniAppCommandBoundary[] getMiniAppCommands (@PathVariable("MiniAppName")String MiniAppName) {
		List<MiniAppCommandBoundary> rv = commands.getAllMiniAppCommands(MiniAppName);
		//return commands.getAllMiniAppCommands(MiniAppName);
		return rv.toArray(new MiniAppCommandBoundary[0]);
	}
	
	@RequestMapping(
			path = "/superapp/admin/users",
			method = {RequestMethod.DELETE})
	public void deleteAllUsers() {
		users.deleteAllUsers();
	}
	@RequestMapping(
			path = "/superapp/admin/objects",
			method = {RequestMethod.DELETE})
	public void deleteAllObjects() {
		objects.deleteAllObjects();
	}
	@RequestMapping(
			path = "/superapp/admin/miniapp",
			method = {RequestMethod.DELETE})
	public void deleteAllCommandsHistory() {
		commands.deleteAllCommands();
	}

}
