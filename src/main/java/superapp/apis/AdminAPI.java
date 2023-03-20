package superapp.apis;

import java.util.ArrayList;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import superapp.boundries.CommandId;
import superapp.boundries.InvokedBy;
import superapp.boundries.MiniAppCommandObject;
import superapp.boundries.ObjectId;
import superapp.boundries.TargetObject;
import superapp.boundries.User;
import superapp.boundries.UserId;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AdminAPI {
	
	public AdminAPI() {
	}
	
	
	@RequestMapping(
			path = {"/superapp/admin/users"},
			method = {RequestMethod.GET},
			produces = {MediaType.APPLICATION_JSON_VALUE})
		public ArrayList<User> GetAllUsers () {
			ArrayList<User> list = new ArrayList<User>();
			String superapp = "wedding";   
			String email = "test@mail.com";
			for (int i = 0; i < 3; i++) {
				String role = "HUSABND ";
				String username = "Lebron James";
				String avatar = "LOVE";
				UserId userId = new UserId(superapp, email);
				
				User u = new User(userId, role, username, avatar);
				list.add(u);
			}
			return list;
			
		}
	
	@RequestMapping(
			path = {"/superapp/admin/miniapp"},
			method = {RequestMethod.GET},
			produces = {MediaType.APPLICATION_JSON_VALUE})
		public ArrayList<MiniAppCommandObject> GetAllMiniAppCommands () {
			ArrayList<MiniAppCommandObject> list = new ArrayList<MiniAppCommandObject>();
			for (int i = 0; i < 3; i++) {
				String superapp = "WedPortal";
				CommandId commandId = new CommandId(superapp, "minapp" + i, Integer.toString(i));
				String command = "foo" + i;
				TargetObject targetObject = new TargetObject(new ObjectId(superapp));
				Date invocationTimestamp = new Date();
				InvokedBy invokedBy = new InvokedBy(new UserId(superapp, "mail@gmail.com"));
				Map<String, Object> commandArrtibutes = new HashMap<String, Object>();
				commandArrtibutes.put("key1", "command" + i);
				list.add(new MiniAppCommandObject(commandId, command, targetObject, invocationTimestamp, invokedBy, commandArrtibutes));
				
			}
			return list;
			
		}
	
	
	@RequestMapping(
			path = {"/superapp/admin/miniapp/{MiniAppName}"},
			method = {RequestMethod.GET},
			produces = {MediaType.APPLICATION_JSON_VALUE})
		public ArrayList<MiniAppCommandObject> GetMiniAppCommands (@PathVariable("MiniAppName")String MiniAppName) {
		ArrayList<MiniAppCommandObject> list = new ArrayList<MiniAppCommandObject>();
		for (int i = 0; i < 3; i++) {
			String superapp = "WedPortal";
			CommandId commandId = new CommandId(superapp, MiniAppName, Integer.toString(i));
			String command = "foo" + i;
			TargetObject targetObject = new TargetObject(new ObjectId(superapp));
			Date invocationTimestamp = new Date();
			InvokedBy invokedBy = new InvokedBy(new UserId(superapp, "mail@gmail.com"));
			Map<String, Object> commandArrtibutes = new HashMap<String, Object>();
			commandArrtibutes.put("key1", "command" + i);
			list.add(new MiniAppCommandObject(commandId, command, targetObject, invocationTimestamp, invokedBy, commandArrtibutes));
			
		}
		return list;
			
		}
	
	@RequestMapping(
			path = "/superapp/admin/users",
			method = {RequestMethod.DELETE})
	public void deleteAllUsers() {
		// do nothing
	}
	@RequestMapping(
			path = "/superapp/admin/objects",
			method = {RequestMethod.DELETE})
	public void deleteAllObjects() {
		// do nothing
	}
	@RequestMapping(
			path = "/superapp/admin/miniapp",
			method = {RequestMethod.DELETE})
	public void deleteAllCommandsHistory() {
		// do nothing
	}
	
	
	
	

}
