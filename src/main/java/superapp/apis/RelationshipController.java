package superapp.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import superapp.boundries.ObjectId;
import superapp.boundries.SuperAppObjectBoundary;
import superapp.logic.ObjectServiceWithPagination;
import superapp.logic.RelationshipObjectsService;


@RestController
public class RelationshipController {

    private ObjectServiceWithPagination objects;
    @Autowired
    public RelationshipController(ObjectServiceWithPagination objects){
        super();
        this.objects = objects;
    }

    @RequestMapping(
            path = {"/superapp/objects/{superapp}/{internalObjectId}/children"},
            method = {RequestMethod.PUT},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void relateParentToChild(@PathVariable("superapp") String superapp,
                                    @PathVariable("internalObjectId") String internalObjectId,
                                    @RequestBody ObjectId childObjectId,
                        			@RequestParam(name = "userSuperApp", required = false, defaultValue = "2023b.zohar.tzabari") String userSuperApp,
                        			@RequestParam(name = "userEmail", required = true) String email){
        ObjectId parentObjectId = new ObjectId(superapp, internalObjectId);
        this.objects.relateParentToChild(parentObjectId, childObjectId,userSuperApp,email);

    }

    @RequestMapping(
            path = {"/superapp/objects/{superapp}/{internalObjectId}/children"},
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuperAppObjectBoundary[] getAllChildrenOfObject(@PathVariable("superapp") String superapp,
                                       @PathVariable("internalObjectId") String internalObjectId){

        return objects.getAllChildrenOfObject(new ObjectId(superapp, internalObjectId)).toArray(new SuperAppObjectBoundary[0]);
    }

    @RequestMapping(
            path = {"/superapp/objects/{superapp}/{internalObjectId}/parents"},
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuperAppObjectBoundary[] getAllParentsOfObject(@PathVariable("superapp") String superapp,
                                                        @PathVariable("internalObjectId") String internalObjectId){
        return objects.getAllParentsOfObject(new ObjectId(superapp, internalObjectId)).toArray(new SuperAppObjectBoundary[0]);
    }
}
