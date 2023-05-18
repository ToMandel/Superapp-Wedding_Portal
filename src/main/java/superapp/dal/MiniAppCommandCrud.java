package superapp.dal;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import superapp.data.MiniAppCommandEntity;


import java.util.List;


public interface MiniAppCommandCrud extends ListCrudRepository<MiniAppCommandEntity, String> ,PagingAndSortingRepository<MiniAppCommandEntity, String>  {
	
    public List<MiniAppCommandEntity> findAllByCommandIdLike(@Param("Id") String Id, Pageable pageable);
    
    /*public List<MiniAppCommandEntity> findAllBySuperAppNameAndMiniAppNameAndEmail (
			@Param("miniAppName") String miniAppName,
			@Param("superAppName") String superAppName,
			@Param("email") String email,
			Pageable pageable);*/
		
    
   
}
