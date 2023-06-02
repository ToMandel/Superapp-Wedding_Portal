package superapp.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import superapp.boundries.ObjectId;
import superapp.boundries.SuperAppObjectBoundary;
import superapp.logic.ObjectServiceWithPagination;


@RestController
public class RelationshipController {

    private ObjectServiceWithPagination objects;
    private String nameFromSpringConfig;

    @Value("${spring.application.name:defaultName}")
    public void setNameFromSpringConfig(String nameFromSpringConfig) {
        this.nameFromSpringConfig = nameFromSpringConfig;
    }

    @Autowired
    public RelationshipController(ObjectServiceWithPagination objects){
        super();
        this.objects = objects;
    }
    
	@CrossOrigin(origins = "*")
    @RequestMapping(
            path = {"/superapp/objects/{superapp}/{internalObjectId}/children"},
            method = {RequestMethod.PUT},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void relateParentToChild(@PathVariable("superapp") String superapp,
                                    @PathVariable("internalObjectId") String internalObjectId,
                                    @RequestBody ObjectId childObjectId,
                        			@RequestParam(name = "userSuperApp", required = false) String userSuperApp,
                        			@RequestParam(name = "userEmail", required = true) String email){
        ObjectId parentObjectId = new ObjectId(superapp, internalObjectId);
        if (userSuperApp == null)
            userSuperApp = this.nameFromSpringConfig;
        this.objects.relateParentToChild(parentObjectId, childObjectId,userSuperApp,email);

    }

	@CrossOrigin(origins = "*")
    @RequestMapping(
            path = {"/superapp/objects/{superapp}/{internalObjectId}/children"},
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuperAppObjectBoundary[] getAllChildrenOfObject(
    		@PathVariable("superapp") String superapp,
    		@PathVariable("internalObjectId") String internalObjectId,
            @RequestParam(name = "userSuperApp", required = false) String userSuperApp,
            @RequestParam(name = "userEmail", required = true) String email,
			@RequestParam(name = "size", required = false, defaultValue = "20") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page){
        if (userSuperApp == null)
            userSuperApp = this.nameFromSpringConfig;
        return objects.getAllChildrenOfObject(new ObjectId(superapp, internalObjectId),userSuperApp,email,size,page)
        		.toArray(new SuperAppObjectBoundary[0]);
    }

	@CrossOrigin(origins = "*")
    @RequestMapping(
            path = {"/superapp/objects/{superapp}/{internalObjectId}/parents"},
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuperAppObjectBoundary[] getAllParentsOfObject(
    		@PathVariable("superapp") String superapp,
            @PathVariable("internalObjectId") String internalObjectId,
            @RequestParam(name = "userSuperApp", required = false) String userSuperApp,
            @RequestParam(name = "userEmail", required = true) String email,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "20") int size){
        if (userSuperApp == null)
            userSuperApp = this.nameFromSpringConfig;
        return objects.getAllParentsOfObject(new ObjectId(superapp, internalObjectId),userSuperApp,email,page,size)
        		.toArray(new SuperAppObjectBoundary[0]);
    }
}
