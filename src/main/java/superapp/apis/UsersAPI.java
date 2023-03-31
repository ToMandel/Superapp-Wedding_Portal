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
	public UserBoundary creatUser (@RequestBody NewUserBoundary newUser) {
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


