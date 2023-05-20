package superapp.dal;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import superapp.data.SuperAppObjectEntity;

import java.util.List;

public interface SupperAppObjectCrud extends ListCrudRepository<SuperAppObjectEntity, String>,
		PagingAndSortingRepository<SuperAppObjectEntity, String> {

	public List<SuperAppObjectEntity> findAllByParentObject(@Param("parentId") String parentId,Pageable pageable);
	
	public List<SuperAppObjectEntity> findAllByParentObjectAndActiveIsTrue(@Param("parentId") String parentId,Pageable pageable);

	public List<SuperAppObjectEntity> findAllByType(@Param("type") String type, Pageable pageable);

	public List<SuperAppObjectEntity> findAllByTypeAndActiveIsTrue(@Param("type") String type, Pageable pageable);

	public List<SuperAppObjectEntity> findAllByTypeAndCreatedBy(@Param("type") String type,
			@Param("createdBy") String createdBy);

	public List<SuperAppObjectEntity> findAllByAlias(@Param("alias") String alias, Pageable pageable);

	public List<SuperAppObjectEntity> findAllByAliasAndActiveIsTrue(@Param("alias") String alias, Pageable pageable);

	public List<SuperAppObjectEntity> findByLatBetweenAndLngBetweenAndActiveIsTrue(
			@Param("minLat") double minLat,
			@Param("maxLat") double maxLat,
			@Param("minLng") double minLng,
			@Param("maxLng") double maxLng,
			Pageable pageable);
	public List<SuperAppObjectEntity> findByLatBetweenAndLngBetween(
			@Param("minLat") double minLat,
			@Param("maxLat") double maxLat,
			@Param("minLng") double minLng,
			@Param("maxLng") double maxLng,
			Pageable pageable);

	public List<SuperAppObjectEntity> findAllByActiveIsTrue(Pageable pageable);


}
