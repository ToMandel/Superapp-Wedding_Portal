package superapp.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import superapp.Converter;
import superapp.boundries.UserBoundary;
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
    public UserBoundary createUser(UserBoundary user) {
        user.setUserId(user.getUserId());
        UserEntity entity =  this.converter.userToEntity(user);
        entity = this.userCrud.save(entity);
        UserBoundary rv = this.converter.userToBoundary(entity);
        return rv;
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
