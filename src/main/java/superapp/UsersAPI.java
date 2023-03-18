package superapp;



import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


import boundries.User;
import boundries.UserId;

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
}
