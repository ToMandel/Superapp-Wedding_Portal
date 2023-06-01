package superapp.miniapps.suppliers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import superapp.boundries.MiniAppCommandBoundary;
import superapp.dal.SupperAppObjectCrud;
import superapp.data.SuperAppObjectEntity;
import superapp.miniapps.MiniAppsCommand;
import superapp.objects.Supplier;

@Component
public class GetTypes implements MiniAppsCommand {

    private SupperAppObjectCrud objectCrud;

    @Autowired
    public void setObjectCrud(SupperAppObjectCrud objectCrud) {
        this.objectCrud = objectCrud;
    }

    @Override
    public Object execute(MiniAppCommandBoundary command) {
        String objectId = command.getTargetObject().getObjectId().getSuperapp() + "#"
                + command.getTargetObject().getObjectId().getInternalObjectId();
        SuperAppObjectEntity entity = this.objectCrud.findById(objectId).get();
        // we already checked that the entity exists
        return Supplier.getAllTypes();
    }
}
