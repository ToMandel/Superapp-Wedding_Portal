package superapp.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import superapp.boundries.MiniAppCommandBoundary;
import superapp.boundries.UserBoundary;
import superapp.logic.*;

import java.util.List;

@RestController
public class AdminAPI {

	private ObjectServiceWithPagination objects;
	private MiniAppCommandServiceWithPagination commands;
	private UsersServiceWithPagination users;

	private String nameFromSpringConfig;

	@Value("${spring.application.name:defaultName}")
	public void setNameFromSpringConfig(String nameFromSpringConfig) {
		this.nameFromSpringConfig = nameFromSpringConfig;
	}

	@Autowired
	public void setUsers(UsersServiceWithPagination users) {
		this.users = users;
	}

	@Autowired
	public void setObjects(ObjectServiceWithPagination objects) {
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
			@RequestParam(name = "userSuperApp", required = false) String superAppName,
			@RequestParam(name = "userEmail", required = true) String email,
			@RequestParam(name = "size", required = false, defaultValue = "20") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		if (superAppName == null)
			superAppName = this.nameFromSpringConfig;
		List<UserBoundary> rv = this.users.getAllUsers(superAppName, email, page, size);
		return rv.toArray(new UserBoundary[0]);
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(
			path = {"/superapp/admin/miniapp"},
			method = {RequestMethod.GET},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public MiniAppCommandBoundary[] getAllCommands (
			@RequestParam(name = "userSuperApp", required = false) String superAppName,
			@RequestParam(name = "userEmail", required = true) String email,@RequestParam(name = "size", required = false, defaultValue = "20") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page)  {
		if (superAppName == null)
			superAppName = this.nameFromSpringConfig;
		List<MiniAppCommandBoundary> rv = this.commands.getAllCommands(superAppName, email, page, size);
		return rv.toArray(new MiniAppCommandBoundary[0]);
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(
			path = {"/superapp/admin/miniapp/{miniAppName}"},
			method = {RequestMethod.GET},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public MiniAppCommandBoundary[] getMiniAppCommands (
			@PathVariable("miniAppName")String miniAppName,
			@RequestParam(name = "userSuperApp", required = false) String superAppName,
			@RequestParam(name = "userEmail", required = true) String email,
			@RequestParam(name = "size", required = false, defaultValue = "20") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		if (superAppName == null)
			superAppName = this.nameFromSpringConfig;
		List<MiniAppCommandBoundary> rv = commands.getAllMiniAppCommands(superAppName, miniAppName,email,size,page);
		return rv.toArray(new MiniAppCommandBoundary[0]);
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(
			path = "/superapp/admin/users",
			method = {RequestMethod.DELETE})
	public void deleteAllUsers(
			@RequestParam(name = "userSuperApp", required = false) String superAppName,
			@RequestParam(name = "userEmail", required = true) String email) {
		if (superAppName == null)
			superAppName = this.nameFromSpringConfig;
		users.deleteAllUsers(superAppName,email);
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(
			path = "/superapp/admin/objects",
			method = {RequestMethod.DELETE})
	public void deleteAllObjects(
			@RequestParam(name = "userSuperApp", required = false) String superAppName,
			@RequestParam(name = "userEmail", required = true) String email) {
		if (superAppName == null)
			superAppName = this.nameFromSpringConfig;
		objects.deleteAllObjects(superAppName,email);
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(
			path = "/superapp/admin/miniapp",
			method = {RequestMethod.DELETE})
	public void deleteAllCommandsHistory(
			@RequestParam(name = "userSuperApp", required = false) String superAppName,
			@RequestParam(name = "userEmail", required = true) String email) {
		if (superAppName == null)
			superAppName = this.nameFromSpringConfig;
		commands.deleteAllCommands(superAppName, email);
	}

}
