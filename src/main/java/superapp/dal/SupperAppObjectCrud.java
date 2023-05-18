package superapp.dal;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import superapp.data.SuperAppObjectEntity;

import java.util.List;

public interface SupperAppObjectCrud extends ListCrudRepository<SuperAppObjectEntity, String>,
		PagingAndSortingRepository<SuperAppObjectEntity, String> {

	public List<SuperAppObjectEntity> findAllByParentObject(@Param("parentId") String parentId);

	public List<SuperAppObjectEntity> findAllByType(@Param("type") String type, Pageable pageable);

	public List<SuperAppObjectEntity> findAllByTypeAndCreatedBy(@Param("type") String type,
			@Param("createdBy") String createdBy);

	public List<SuperAppObjectEntity> findAllByAlias(@Param("alias") String alias, Pageable pageable);

//	@Query("SELECT obj FROM ObjectEntity obj WHERE obj.latitude BETWEEN :minLatitude AND :maxLatitude AND obj.longitude BETWEEN :minLongitude AND :maxLongitude")
	public List<SuperAppObjectEntity> findByLatBetweenAndLngBetween(
			  double minLat, double maxLat, double minLng, double maxLng, Pageable pageable);

}
