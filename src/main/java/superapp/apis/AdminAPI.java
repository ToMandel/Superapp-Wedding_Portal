package superapp.apis;

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

	//TODO: define autowired functions
	/*
	@Autowired
	public void setObjects(ObjectsService objects) {
		this.objects = objects;
	}

	@Autowired
	public void setCommands(MiniAppCommandService commands) {
		this.commands = commands;
	}

	@Autowired
	public void setUsers(UsersService users) {
		this.users = users;
	}*/
	
	public AdminAPI() {
	}
	
	
	@RequestMapping(
			path = {"/superapp/admin/users"},
			method = {RequestMethod.GET},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<UserBoundary> getAllUsers () {
			/*ArrayList<UserBoundary> list = new ArrayList<UserBoundary>();
			String superapp = "wedding";   
			String email = "test@mail.com";
			for (int i = 0; i < 3; i++) {
				String role = "HUSABND ";
				String username = "Lebron James";
				String avatar = "LOVE";
				UserId userId = new UserId(superapp, email);
				
				UserBoundary u = new UserBoundary(userId, role, username, avatar);
				list.add(u);
			}
			return list;*/
		return users.getAllUsers();
			
	}
	
	@RequestMapping(
			path = {"/superapp/admin/miniapp"},
			method = {RequestMethod.GET},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<MiniAppCommandBoundary> getAllMiniAppCommands () {
			/*ArrayList<MiniAppCommandBoundary> list = new ArrayList<MiniAppCommandBoundary>();
			for (int i = 0; i < 3; i++) {
				String superapp = "WedPortal";
				CommandId commandId = new CommandId(superapp, "minapp" + i, Integer.toString(i));
				String command = "foo" + i;
				TargetObject targetObject = new TargetObject(new ObjectId(superapp));
				Date invocationTimestamp = new Date();
				InvokedBy invokedBy = new InvokedBy(new UserId(superapp, "mail@gmail.com"));
				Map<String, Object> commandArrtibutes = new HashMap<String, Object>();
				commandArrtibutes.put("key1", "command" + i);
				list.add(new MiniAppCommandBoundary(commandId, command, targetObject, invocationTimestamp, invokedBy, commandArrtibutes));
				
			}
			return list;*/
		return commands.getAllCommands();
			
	}
	
	
	@RequestMapping(
			path = {"/superapp/admin/miniapp/{MiniAppName}"},
			method = {RequestMethod.GET},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<MiniAppCommandBoundary> getMiniAppCommands (@PathVariable("MiniAppName")String MiniAppName) {
		/*ArrayList<MiniAppCommandBoundary> list = new ArrayList<MiniAppCommandBoundary>();
		for (int i = 0; i < 3; i++) {
			String superapp = "WedPortal";
			CommandId commandId = new CommandId(superapp, MiniAppName, Integer.toString(i));
			String command = "foo" + i;
			TargetObject targetObject = new TargetObject(new ObjectId(superapp));
			Date invocationTimestamp = new Date();
			InvokedBy invokedBy = new InvokedBy(new UserId(superapp, "mail@gmail.com"));
			Map<String, Object> commandArrtibutes = new HashMap<String, Object>();
			commandArrtibutes.put("key1", "command" + i);
			list.add(new MiniAppCommandBoundary(commandId, command, targetObject, invocationTimestamp, invokedBy, commandArrtibutes));
			
		}*/
		return commands.getAllMiniAppCommands(MiniAppName);
			
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
