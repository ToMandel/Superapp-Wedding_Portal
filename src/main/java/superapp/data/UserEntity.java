package superapp.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
//import superapp.boundries.UserId;

@Entity
@Table(name = "USERS")
public class UserEntity  {

    //TODO: change id to userId
    @Id
    private String userId;
    private String role;
    private String username;
    private String avatar;

    public UserEntity() {
    }

    public UserEntity(String userId, String role, String username, String avatar) {
        this.userId = userId;
        this.role = role;
        this.username = username;
        this.avatar = avatar;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
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
}
