package superapp;



import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import boundries.User;
import boundries.UserId;

import org.springframework.web.bind.annotation.PathVariable;

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
		String username = "Michel Jordan ";
		String avatar = "LOVE";
		UserId userId = new UserId(superapp, email);
		
		User u = new User(userId, role, username, avatar);
		return u;
		
		
	}
}
