package superapp.apis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import superapp.boundries.NewUserBoundary;
import superapp.boundries.UserBoundary;
import superapp.logic.UsersService;

@RestController
public class UsersAPI {

	private UsersService users;
	@Autowired
	public void setUsers(UsersService users) {
		this.users = users;
	}
	
	public UsersAPI() {
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(
		path = {"/superapp/users/login/{superapp}/{email}"},
		method = {RequestMethod.GET},
		produces = {MediaType.APPLICATION_JSON_VALUE})
	public UserBoundary login (@PathVariable("superapp")String superapp, @PathVariable("email")String email) {
		return users.login(superapp, email);
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(
			path = {"/superapp/users"},
			method = {RequestMethod.POST},
			produces = {MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	public UserBoundary creatUser (@RequestBody NewUserBoundary newUser) {
			return users.createUser(newUser);
		}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(
			path = {"/superapp/users/{superapp}/{userEmail}"},
			method = {RequestMethod.PUT},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void updateUser (@PathVariable("userEmail") String userEmail,
									@PathVariable("superapp") String superapp,
									@RequestBody UserBoundary update) {
		users.updateUser(superapp, userEmail, update);
	}
			
	
}


