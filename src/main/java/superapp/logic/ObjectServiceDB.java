package superapp.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import superapp.Converter;
import superapp.boundries.SuperAppObjectBoundary;
import superapp.dal.SupperAppObjectCrud;
import superapp.dal.UserCrud;

import java.util.List;

@Service
public class ObjectServiceDB implements ObjectsService{

    private SupperAppObjectCrud objectCrud;
    private Converter converter;


    @Autowired
    public void setObjectCrud(SupperAppObjectCrud userCrud){
        this.objectCrud = objectCrud;
    }

    @Autowired
    public void setConverter(Converter converter){
        this.converter = converter;
    }

    @Override
    public SuperAppObjectBoundary createObject(SuperAppObjectBoundary object) {
        return null;
    }

    @Override
    public SuperAppObjectBoundary updateObject(String objectSuperApp, String internalObjectId, SuperAppObjectBoundary update) {
        return null;
    }

    @Override
    public SuperAppObjectBoundary getSpecificObject(String objectSuperApp, String internalId) {
        return null;
    }

    @Override
    public List<SuperAppObjectBoundary> getAllObjects() {
        return null;
    }

    @Override
    @Transactional
    public void deleteAllObjects() {
        this.objectCrud.deleteAll();
    }
}
