package superapp.miniapps.customers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import superapp.boundries.MiniAppCommandBoundary;
import superapp.dal.SupperAppObjectCrud;
import superapp.data.SuperAppObjectEntity;
import superapp.miniapps.MiniAppsCommand;

@Component
public class GetSupplierFreeDates implements MiniAppsCommand {

    private SupperAppObjectCrud objectCrud;

    @Autowired
    public void setObjectCrud(SupperAppObjectCrud objectCrud) {
        this.objectCrud = objectCrud;
    }
    @Override
    public Object execute(MiniAppCommandBoundary command) {
        String objectId = command.getTargetObject().getObjectId().getSuperapp() + "#" + command.getTargetObject().getObjectId().getInternalObjectId();
        SuperAppObjectEntity supplier = this.objectCrud.findById(objectId).get();
        if (supplier.getObjectDetails() == null || supplier.getObjectDetails().get("freeDates") == null){
            return new Object[0];
        }
        return supplier.getObjectDetails().get("freeDates");
    }
}
