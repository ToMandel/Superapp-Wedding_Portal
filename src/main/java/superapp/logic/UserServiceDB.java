package superapp.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import superapp.Converter;
import superapp.boundries.NewUserBoundary;
import superapp.boundries.UserBoundary;
import superapp.boundries.UserId;
import superapp.dal.UserCrud;
import superapp.data.UserEntity;

import java.util.List;

@Service
public class UserServiceDB implements UsersService{


    private UserCrud userCrud;
    private Converter converter;

    @Autowired
    public void setUserCrud(UserCrud userCrud){
        this.userCrud = userCrud;
    }

    @Autowired
    public void setConverter(Converter converter){
        this.converter = converter;
    }

    @Transactional
    @Override
    public UserBoundary createUser(NewUserBoundary newUser) {
        UserBoundary user = new UserBoundary();
        //TODO: change superapp name (?)
        //TODO: for some reason the supername is "wed" - find where this assign
        UserId userId = new UserId("wed portal", newUser.getEmail());
        user.setUserId(userId);
        user.setRole(newUser.getRole());
        user.setUsername(newUser.getUsername());
        user.setAvatar(newUser.getAvatar());
        UserEntity entity =  this.converter.userToEntity(user);
        entity = this.userCrud.save(entity);
        return this.converter.userToBoundary(entity);
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
