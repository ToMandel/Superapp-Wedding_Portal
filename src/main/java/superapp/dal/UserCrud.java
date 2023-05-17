package superapp.dal;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import superapp.data.UserEntity;

public interface UserCrud extends ListCrudRepository<UserEntity, String>,PagingAndSortingRepository<UserEntity, String> {
	
	public List<UserEntity> findAll (
			@Param("userSuperApp") String sueprapp,
			@Param("userEmail") String email,
			Pageable pageable);

	public UserEntity findBySuperappAndEmail (
			@Param("userSuperApp") String sueprapp,
			@Param("userEmail") String email);
	
}
