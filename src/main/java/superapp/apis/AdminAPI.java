package superapp.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import superapp.boundries.MiniAppCommandBoundary;
import superapp.boundries.UserBoundary;
import superapp.logic.MiniAppCommandService;
import superapp.logic.MiniAppCommandServiceWithPagination;
import superapp.logic.ObjectsService;
import superapp.logic.UsersService;
import superapp.logic.UsersServiceWithPagination;

import java.util.List;

@RestController
public class AdminAPI {

	private ObjectsService objects;
	private MiniAppCommandServiceWithPagination commands;
	private UsersServiceWithPagination users;

	@Autowired
	public void setUsers(UsersServiceWithPagination users) {
		this.users = users;
	}

	@Autowired
	public void setObjects(ObjectsService objects) {
		this.objects = objects;
	}

	@Autowired
	public void setCommands(MiniAppCommandServiceWithPagination commands) {
		this.commands = commands;
	}

	
	public AdminAPI() {
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(
			path = {"/superapp/admin/users"},
			method = {RequestMethod.GET},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public UserBoundary[] getAllUsers (
			@RequestParam(name = "userSuperApp", required = false, defaultValue = "2023b.zohar.tzabari") String superAppName,
			@RequestParam(name = "userEmail", required = true) String email,
			@RequestParam(name = "size", required = false, defaultValue = "20") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		List<UserBoundary> rv = this.users.getAllUsers(superAppName,email,size,page);
		//return users.getAllUsers();
		return rv.toArray(new UserBoundary[0]);
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(
			path = {"/superapp/admin/miniapp"},
			method = {RequestMethod.GET},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public MiniAppCommandBoundary[] getAllCommands (@RequestParam(name = "userSuperApp", required = false, defaultValue = "2023b.zohar.tzabari") String superAppName,
			@RequestParam(name = "userEmail", required = true) String email,@RequestParam(name = "size", required = false, defaultValue = "20") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page)  {
		List<MiniAppCommandBoundary> rv = this.commands.getAllCommands(superAppName,email,size,page);
		//return commands.getAllCommands();
		return rv.toArray(new MiniAppCommandBoundary[0]);
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(
			path = {"/superapp/admin/miniapp/{MiniAppName}"},
			method = {RequestMethod.GET},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public MiniAppCommandBoundary[] getMiniAppCommands (@PathVariable("MiniAppName")String MiniAppName,@RequestParam(name = "userSuperApp", required = false, defaultValue = "2023b.zohar.tzabari") String superAppName,
			@RequestParam(name = "userEmail", required = true) String email,@RequestParam(name = "size", required = false, defaultValue = "20") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		List<MiniAppCommandBoundary> rv = commands.getAllMiniAppCommands(MiniAppName,superAppName,email,size,page);
		//return commands.getAllMiniAppCommands(MiniAppName);
		return rv.toArray(new MiniAppCommandBoundary[0]);
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(
			path = "/superapp/admin/users",
			method = {RequestMethod.DELETE})
	public void deleteAllUsers( @RequestParam(name = "userSuperApp", required = false, defaultValue = "2023b.zohar.tzabari") String superAppName,
			@RequestParam(name = "userEmail", required = true) String email) {
		users.deleteAllUsers(superAppName,email);
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(
			path = "/superapp/admin/objects",
			method = {RequestMethod.DELETE})
	public void deleteAllObjects(@RequestParam(name = "userSuperApp", required = false, defaultValue = "2023b.zohar.tzabari") String superAppName,
			@RequestParam(name = "userEmail", required = true) String email) {
		objects.deleteAllObjects(superAppName,email);
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(
			path = "/superapp/admin/miniapp",
			method = {RequestMethod.DELETE})
	public void deleteAllCommandsHistory(@RequestParam(name = "userSuperApp", required = false, defaultValue = "2023b.zohar.tzabari") String superAppName,
			@RequestParam(name = "userEmail", required = true) String email) {
		commands.deleteAllCommands(superAppName,email);
	}

}
