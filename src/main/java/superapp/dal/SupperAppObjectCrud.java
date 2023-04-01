package superapp.dal;
import org.springframework.data.repository.CrudRepository;

import superapp.data.SuperAppObjectEntity;

public interface SupperAppObjectCrud extends CrudRepository<SuperAppObjectEntity, String > {

}
