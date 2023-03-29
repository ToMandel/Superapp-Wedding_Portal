package superapp.data;

import superapp.boundries.UserBoundary;
import superapp.logic.UsersService;

import java.util.List;

public class UserEntity implements UsersService {
    @Override
    public UserBoundary createUser(UserBoundary user) {
        return null;
    }

    @Override
    public UserBoundary login(String userSuperApp, String userEmail) {
        return null;
    }

    @Override
    public UserBoundary updateUser(String userSuperApp, String userEmail, UserBoundary update) {
        return null;
    }

    @Override
    public List<UserBoundary> getAllUsers() {
        return null;
    }

    @Override
    public void deleteAllUsers() {

    }
}
