package superapp.logic;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import superapp.Converter;
import superapp.boundries.ObjectId;
import superapp.boundries.SuperAppObjectBoundary;
import superapp.dal.SupperAppObjectCrud;
import superapp.data.SuperAppObjectEntity;

import java.util.*;

@Service
public class ObjectServiceDB implements RelationshipObjectsService{

    private SupperAppObjectCrud objectCrud;
    private Converter converter;
    private String nameFromSpringConfig;
    

    @Value("${spring.application.name:defaultName}")
    public void setNameFromSpringConfig(String nameFromSpringConfig) {
        this.nameFromSpringConfig = nameFromSpringConfig;
    }

    @PostConstruct
    public void init (){
        System.err.println("**** spring.application.name = " + this.nameFromSpringConfig + " ****");
    }


    @Autowired
    public void setObjectCrud(SupperAppObjectCrud objectCrud){
        this.objectCrud = objectCrud;
    }

    @Autowired
    public void setConverter(Converter converter){
        this.converter = converter;
    }


    @Override
    public SuperAppObjectBoundary createObject(SuperAppObjectBoundary object) {
        //List<SuperAppObjectEntity> entities = this.objectCrud.findAll();
        //String internalObjectId;
//        if (entities.isEmpty())
//            internalObjectId = "1";
//        else
         //  internalObjectId = Integer.toString(entities.size() + 1);
        String internalObjectId=UUID.randomUUID().toString(); // the value is random so the size of entities isn't relevant
        //object.setObjectId(new ObjectId(nameFromSpringConfig));
        object.setObjectId(new ObjectId(nameFromSpringConfig, internalObjectId));
        object.setCreationTimestamp(new Date());
        if (object.getObjectDetails() == null)
            object.setObjectDetails(new HashMap<>());
        SuperAppObjectEntity entity = this.converter.superAppObjectToEntity(object);
        entity = this.objectCrud.save(entity);
        return this.converter.superAppObjectToBoundary(entity);
    }

    @Override
    public SuperAppObjectBoundary updateObject(String objectSuperApp, String internalObjectId, SuperAppObjectBoundary update) {
        String objectId = objectSuperApp + "#" + internalObjectId;
        SuperAppObjectEntity existing = this.objectCrud.findById(objectId).orElseThrow(()->new RuntimeException("could not find object by id: " + objectId));
        if (update.getType() != null)
            existing.setType(update.getType());
        if (update.getAlias() != null)
            existing.setAlias(update.getAlias());
        if (update.getActive() != null)
            existing.setActive(update.getActive());
        if (update.getLocation() != null){
            existing.setLng(update.getLocation().getLng());
            existing.setLat(update.getLocation().getLat());
        }
        if (update.getObjectDetails() != null)
            //existing.setObjectDetails(this.converter.toEntity(update.getObjectDetails()));
            existing.setObjectDetails(update.getObjectDetails());
        this.objectCrud.save(existing);
        return this.converter.superAppObjectToBoundary(existing);
    }

    @Override
    public SuperAppObjectBoundary getSpecificObject(String objectSuperApp, String internalId) {
        String objectId = objectSuperApp + "#" + internalId;
        SuperAppObjectEntity existing = this.objectCrud.findById(objectId).orElseThrow(()->new RuntimeException("could not find object by id: " + objectId));
        return this.converter.superAppObjectToBoundary(existing);
    }

    @Override
    public List<SuperAppObjectBoundary> getAllObjects() {
        List<SuperAppObjectEntity> entities = this.objectCrud.findAll();
        List<SuperAppObjectBoundary> rv = new ArrayList<SuperAppObjectBoundary>();
        for(SuperAppObjectEntity e : entities){
            rv.add(this.converter.superAppObjectToBoundary(e));
        }
        return rv;
    }


    @Override
    public void deleteAllObjects() {
        this.objectCrud.deleteAll();
    }

    @Override
    public void relateParentToChild(ObjectId parentObjectId, ObjectId childObjectId) {
        String parentId = parentObjectId.getSuperapp() + "#" + parentObjectId.getInternalObjectId();
        SuperAppObjectEntity parent = this.objectCrud
                .findById(parentId)
                .orElseThrow(()->new NotFoundException("could not find origin object by id: " + parentObjectId));

        String childId = childObjectId.getSuperapp() + "#" + childObjectId.getInternalObjectId();
        SuperAppObjectEntity child = this.objectCrud
                .findById(childId)
                .orElseThrow(()->new NotFoundException("could not find response object by id: " + childId));

        child.setParentObject(parent);

        this.objectCrud
                .save(child);
    }

    @Override
    public List<SuperAppObjectBoundary> getAllChildrenOfObject(ObjectId parent) {
        //TODO: implement method
        return null;
    }

    @Override
    public List<SuperAppObjectBoundary> getAllParentsOfObject(ObjectId child) {
        List<SuperAppObjectBoundary> allParents = new ArrayList<SuperAppObjectBoundary>();
        String childId = child.getSuperapp() + "#" + child.getInternalObjectId();
        SuperAppObjectEntity childEntity = this.objectCrud
                .findById(childId)
                .orElseThrow(()->new NotFoundException("could not find object by id: " + childId));

        SuperAppObjectEntity parentEntity = childEntity.getParentObject();
        if (parentEntity != null) {
            allParents.add(Optional.of(this.converter
                    .superAppObjectToBoundary(parentEntity)).get());
        }else {
             //allParents.add(new SuperAppObjectBoundary());
        }
        return allParents;
    }
}
