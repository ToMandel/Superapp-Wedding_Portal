package superapp.miniapps.suppliers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import superapp.boundries.MiniAppCommandBoundary;
import superapp.dal.SupperAppObjectCrud;
import superapp.miniapps.CommandsInvoker;
import superapp.miniapps.MiniAppsCommand;

@Component
public class GetSupplierServices implements MiniAppsCommand {

    private SupperAppObjectCrud objectCrud;

    @Autowired
    public void setObjectCrud(SupperAppObjectCrud objectCrud) {
        this.objectCrud = objectCrud;
    }

    @Override
    public Object execute(MiniAppCommandBoundary command) {
        if (command.getCommandAttributes() == null || command.getCommandAttributes().get("supplierMail") == null) {
            String commandName = command.getCommand();
            return CommandsInvoker.createUnknownCommandBoundary(commandName, "Please enter supplier mail");
        }
        String supplierMail = command.getCommandAttributes().get("supplierMail").toString();
        return this.objectCrud.findAllSupplierEvents(supplierMail);
    }
}
