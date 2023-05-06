package superapp.boundries;

import java.util.regex.Pattern;

import superapp.data.UserRole;

public class NewUserBoundary {

    private String email;
    private UserRole role;
    private String username;
    private String avatar;


    public NewUserBoundary() {
    }

    public NewUserBoundary(String email, UserRole role, String username, String avatar) {
        this.email = email;
        this.role = role;
        this.username = username;
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
    	if (isValid(email))
            this.email = email;
    	else 
    	    this.email = "";
    	
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    
    private boolean isValid(String email) {
		String emailRegex = "([a-zA-Z0-9_+&.-]+)@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";

		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pat.matcher(email).matches();
	}
}
