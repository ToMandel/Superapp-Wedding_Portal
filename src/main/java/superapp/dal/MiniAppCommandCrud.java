package superapp.dal;
import org.springframework.data.repository.ListCrudRepository;

import org.springframework.data.repository.query.Param;
import superapp.data.MiniAppCommandEntity;

import java.util.List;


public interface MiniAppCommandCrud extends ListCrudRepository<MiniAppCommandEntity, String>  {

    public List<MiniAppCommandEntity> findAllByCommandIdLike(@Param("Id") String Id);

}
