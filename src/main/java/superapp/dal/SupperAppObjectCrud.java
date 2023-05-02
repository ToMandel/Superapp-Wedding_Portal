package superapp.dal;


import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import superapp.data.SuperAppObjectEntity;

import java.util.List;

public interface SupperAppObjectCrud extends ListCrudRepository<SuperAppObjectEntity, String > {

    public List<SuperAppObjectEntity> findAllByType(@Param("type") String type);

}
