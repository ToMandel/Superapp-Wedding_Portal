package superapp.dal;


import org.springframework.data.repository.ListCrudRepository;
import superapp.data.SuperAppObjectEntity;

public interface SupperAppObjectCrud extends ListCrudRepository<SuperAppObjectEntity, String > {

}
