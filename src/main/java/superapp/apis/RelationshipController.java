package superapp.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import superapp.boundries.ObjectId;
import superapp.boundries.SuperAppObjectBoundary;
import superapp.logic.RelationshipObjectsService;


@RestController
public class RelationshipController {

    private RelationshipObjectsService objects;
    @Autowired
    public RelationshipController(RelationshipObjectsService objects){
        super();
        this.objects = objects;
    }

    @RequestMapping(
            path = {"/superapp/objects/{superapp}/{internalObjectId}/children"},
            method = {RequestMethod.PUT},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void relateOriginToChild(@PathVariable("superapp") String superapp,
                                    @PathVariable("internalObjectId") String internalObjectId,
                                    @RequestBody ObjectId responseIdWrapper){
        this.objects.relateParentToChild(new ObjectId(superapp, internalObjectId), responseIdWrapper);

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
