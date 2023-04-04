package superapp.logic;

import superapp.boundries.NewUserBoundary;
import superapp.boundries.UserBoundary;

import java.util.List;

public interface UsersService {

    public UserBoundary createUser (NewUserBoundary user);

    public UserBoundary login (String userSuperApp, String userEmail);

    public UserBoundary updateUser (String userSuperApp, String userEmail, UserBoundary update);

    public List<UserBoundary> getAllUsers();

    public void deleteAllUsers ();

}
