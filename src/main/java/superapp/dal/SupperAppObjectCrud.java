package superapp.dal;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import superapp.data.SuperAppObjectEntity;

import java.util.List;

public interface SupperAppObjectCrud extends ListCrudRepository<SuperAppObjectEntity, String>,
		PagingAndSortingRepository<SuperAppObjectEntity, String> {

	public List<SuperAppObjectEntity> findAllByParentObject(@Param("parentId") String parentId);

	public List<SuperAppObjectEntity> findAllByType(@Param("type") String type);

	public List<SuperAppObjectEntity> findAllByTypeAndCreatedBy(@Param("type") String type, @Param("createdBy") String createdBy);

}
