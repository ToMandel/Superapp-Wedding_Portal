package superapp.apis;



import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


//import demo.Message;
import superapp.boundries.User;
import superapp.boundries.UserId;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class UsersAPI {
	
	public UsersAPI() {
	}
	
	@RequestMapping(
		path = {"/superapp/users/login/{superapp}/{email}"},
		method = {RequestMethod.GET},
		produces = {MediaType.APPLICATION_JSON_VALUE})
	public User GetUser (@PathVariable("superapp")String superapp, @PathVariable("email")String email) {
		String role = "HUSABND";
		String username = "Lebron Liri";
		String avatar = "LOVE";
		UserId userId = new UserId(superapp, email);
		
		User u = new User(userId, role, username, avatar);
		return u;
	}
	
	@RequestMapping(
			path = {"/superapp/users"},
			method = {RequestMethod.POST},
			produces = {MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
		public User CreateNewUser (@RequestBody User newUser) {
			String role = newUser.getRole();
			String username = newUser.getUsername();
			String avatar = newUser.getAvatar();
			UserId userId = newUser.getUserId();
			
			User u = new User(userId, role, username, avatar);
			return u;
		}
	
	
	@RequestMapping(
			path = {"/superapp/users/{superapp}/{userEmail}"},
			method = {RequestMethod.PUT},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void updateSpecificUser (
			@PathVariable("userEmail") String userEmail,
			@PathVariable("superapp") String superapp,
			@RequestBody User update) {
		System.err.println("userEmail: " + userEmail);
		System.err.println("update: " + update);
	}
			
	
}


