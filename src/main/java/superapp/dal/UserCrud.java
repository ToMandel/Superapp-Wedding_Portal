package superapp.dal;


import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import superapp.data.UserEntity;

public interface UserCrud extends ListCrudRepository<UserEntity, String>,PagingAndSortingRepository<UserEntity, String> {
	
}
