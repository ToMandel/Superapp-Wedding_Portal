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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @Transactional(readOnly = true)
    public UserBoundary login(String userSuperApp, String userEmail) {
        //TODO fix bug
        String userId = userSuperApp + "#" + userEmail;
        UserEntity existing = this.userCrud.findById(userId).orElseThrow(()->new RuntimeException("could not find user by id: " + userId));
        return this.converter.userToBoundary(existing);
        /*Optional<UserEntity> op = this.userCrud.findById(userId);
        if (op.isPresent()){
            UserEntity entity = op.get();
            return this.converter.userToBoundary(entity);
        }
        else
            throw new RuntimeException("Could not find user by id: " + userId);
        */

    }

    @Override
    @Transactional
    public void updateUser(String userSuperApp, String userEmail, UserBoundary update) {
        //TODO check if this method need to be void or return an object
        String userId = userSuperApp + "#" + userEmail;
        UserEntity existing = this.userCrud.findById(userId).orElseThrow(()->new RuntimeException("could not find user by id: " + userId));
        //superapp and email can not be changed
        if (update.getRole() != null)
            existing.setRole(update.getRole());
        if (update.getUsername() != null)
            existing.setUsername(update.getUsername());
        if (update.getAvatar() != null)
            existing.setAvatar(update.getAvatar());

    }

    @Override
    @Transactional(readOnly = true)
    public List<UserBoundary> getAllUsers() {
        List<UserEntity> entities = this.userCrud.findAll();
        List<UserBoundary> rv = new ArrayList<UserBoundary>();
        for (UserEntity e : entities){
            rv.add(this.converter.userToBoundary(e));
        }
        return rv;
    }

    @Override
    @Transactional
    public void deleteAllUsers() {
        this.userCrud.deleteAll();
    }
}
