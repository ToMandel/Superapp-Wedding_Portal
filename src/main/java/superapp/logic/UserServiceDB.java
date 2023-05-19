package superapp.logic;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import superapp.Converter;
import superapp.boundries.NewUserBoundary;
import superapp.boundries.UserBoundary;
import superapp.boundries.UserId;
import superapp.dal.UserCrud;
import superapp.data.UserEntity;
import superapp.data.UserRole;

import java.util.Optional;
import java.util.List;

@Service
public class UserServiceDB implements UsersServiceWithPagination {

	private UserCrud userCrud;
	private Converter converter;
	private String nameFromSpringConfig;

	@Value("${spring.application.name:defaultName}")
	public void setNameFromSpringConfig(String nameFromSpringConfig) {
		this.nameFromSpringConfig = nameFromSpringConfig;
	}

	@PostConstruct
	public void init() {
		System.err.println("**** spring.application.name = " + this.nameFromSpringConfig + " ****");
	}

	@Autowired
	public void setUserCrud(UserCrud userCrud) {
		this.userCrud = userCrud;
	}

	@Autowired
	public void setConverter(Converter converter) {
		this.converter = converter;
	}

	private UserEntity getUser (String superAppName, String email){
		String id = superAppName + "#" + email;
		return this.userCrud.findById(id)
				.orElseThrow(() -> new UnauthorizedException("There is no user with email: " + email + " in " + superAppName + " superapp"));
	}

	@Override
	public UserBoundary createUser(NewUserBoundary newUser) {
		UserBoundary user = new UserBoundary();
		String id = this.nameFromSpringConfig + "#" + newUser.getEmail();
		// To check if there is a user with the same email already
		Optional<UserEntity> existing = this.userCrud.findById(id);
		if (existing.isPresent())
			throw new BadRequestException("User with same email already exists");
		UserId userId = new UserId(this.nameFromSpringConfig, newUser.getEmail());
		user.setUserId(userId);
		user.setRole(newUser.getRole());
		user.setUsername(newUser.getUsername());
		user.setAvatar(newUser.getAvatar());
		UserEntity entity = this.converter.userToEntity(user);
		entity = this.userCrud.save(entity);
		return this.converter.userToBoundary(entity);
	}

	@Override
	public UserBoundary login(String userSuperApp, String userEmail) {
		String userId = userSuperApp + "#" + userEmail;
		UserEntity existing = this.userCrud.findById(userId)
				.orElseThrow(() -> new NotFoundException("could not find user by id: " + userId));
		return this.converter.userToBoundary(existing);
	}

	@Override
	public UserBoundary updateUser(String userSuperApp, String userEmail, UserBoundary update) {
		String userId = userSuperApp + "#" + userEmail;
		UserEntity existing = this.userCrud.findById(userId)
				.orElseThrow(() -> new RuntimeException("could not find user by id: " + userId));
		// superapp and email can not be changed so don't need to modify them
		if (update.getRole() != null)
			existing.setRole(update.getRole());
		if (update.getUsername() != null)
			existing.setUsername(update.getUsername());
		if (update.getAvatar() != null)
			existing.setAvatar(update.getAvatar());
		this.userCrud.save(existing);
		return this.converter.userToBoundary(existing);

	}

	@Override
	@Deprecated
	public List<UserBoundary> getAllUsers() {
		throw new DeprecatedOperationException();
	}

	@Override
	public List<UserBoundary> getAllUsers(String superAppName, String email, int page, int size) {
		UserEntity user = getUser(superAppName, email);
		if (user.getRole() != null && user.getRole() == UserRole.ADMIN)
			return this.userCrud
					.findAll(PageRequest.of(page, size, Direction.ASC, "role", "username", "avatar", "userId"))
					.stream()
					.map(this.converter::userToBoundary)
					.toList();
		else
			throw new ForbiddenException("Operation is not allowed, the user is not ADMIN");
	}

	@Override
	@Deprecated
	public void deleteAllUsers() {
		throw new DeprecatedOperationException();
	}

	@Override
	public void deleteAllUsers(String superAppName, String email) {
		UserEntity user = getUser(superAppName, email);
		if (user.getRole() != null && user.getRole() == UserRole.ADMIN) {
			this.userCrud.deleteAll();
		}
		else
			throw new ForbiddenException("Operation is not allowed, the user is not ADMIN");
	}

}
