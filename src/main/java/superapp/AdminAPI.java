package superapp;

import java.util.ArrayList;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import boundries.User;
import boundries.UserId;

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
				String role = "HUSABND";
				String username = "Lebron James";
				String avatar = "LOVE";
				UserId userId = new UserId(superapp, email);
				
				User u = new User(userId, role, username, avatar);
			}
			return list;
			
		}

}
