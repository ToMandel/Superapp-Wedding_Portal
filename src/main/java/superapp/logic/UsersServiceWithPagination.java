package superapp.logic;

import java.util.List;

import superapp.boundries.UserBoundary;

public interface UsersServiceWithPagination extends UsersService {

    public List<UserBoundary> getAllUsers(String superAppName,String email,int size,int page);

    public void deleteAllUsers(String superAppName, String email);

}
