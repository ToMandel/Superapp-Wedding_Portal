package superapp.apis;



import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


//import demo.Message;
import superapp.boundries.UserBoundary;
import superapp.boundries.UserId;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import superapp.logic.UsersService;

@RestController
public class UsersAPI {

	private UsersService users;

	//TODO: define autowired function
	
	public UsersAPI() {
	}
	
	@RequestMapping(
		path = {"/superapp/users/login/{superapp}/{email}"},
		method = {RequestMethod.GET},
		produces = {MediaType.APPLICATION_JSON_VALUE})
	public UserBoundary login (@PathVariable("superapp")String superapp, @PathVariable("email")String email) {
		/*String role = "HUSABND";
		String username = "Lebron James 1";
		String avatar = "LOVE";
		UserId userId = new UserId(superapp, email);
		
		UserBoundary u = new UserBoundary(userId, role, username, avatar);
		return u;*/
		return users.login(superapp, email);
	}
	
	@RequestMapping(
			path = {"/superapp/users"},
			method = {RequestMethod.POST},
			produces = {MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	public UserBoundary CreatUser (@RequestBody UserBoundary newUser) {
			/*String role = newUser.getRole();
			String username = newUser.getUsername();
			String avatar = newUser.getAvatar();
			UserId userId = newUser.getUserId();
			
			UserBoundary u = new UserBoundary(userId, role, username, avatar);
			return u;*/
			return users.createUser(newUser);
		}
	
	
	@RequestMapping(
			path = {"/superapp/users/{superapp}/{userEmail}"},
			method = {RequestMethod.PUT},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void updateUser (@PathVariable("userEmail") String userEmail,
									@PathVariable("superapp") String superapp,
									@RequestBody UserBoundary update) {
		users.updateUser(superapp, userEmail, update);

		//System.err.println("userEmail: " + userEmail);
		//System.err.println("update: " + update);
	}
			
	
}


