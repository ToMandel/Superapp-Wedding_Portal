package superapp.logic;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private String nameFromSpringConfig;

    @Value("${spring.application.name:defaultName}")
    public void setNameFromSpringConfig(String nameFromSpringConfig) {
        this.nameFromSpringConfig = nameFromSpringConfig;
    }

    @PostConstruct
    public void init (){
        System.err.println("**** spring.application.name = " + this.nameFromSpringConfig + " ****");
    }
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
        UserId userId = new UserId(this.nameFromSpringConfig, newUser.getEmail());
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
