package superapp.miniapps.suppliers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import superapp.boundries.MiniAppCommandBoundary;
import superapp.dal.SupperAppObjectCrud;
import superapp.miniapps.CommandsInvoker;
import superapp.miniapps.MiniAppsCommand;

@Component
public class GetSupplierServicesByStatusAndMail implements MiniAppsCommand {

    private SupperAppObjectCrud objectCrud;

    @Autowired
    public void setObjectCrud(SupperAppObjectCrud objectCrud) {
        this.objectCrud = objectCrud;
    }

    @Override
    public Object execute(MiniAppCommandBoundary command) {
        String commandName = command.getCommand();
        String status = command.getCommandAttributes().get("status").toString();
        System.err.println(status);

        if (command.getCommandAttributes() == null || command.getCommandAttributes().get("supplierMail") == null)
            return CommandsInvoker.createUnknownCommandBoundary(commandName, "Please enter supplier mail");
        if (command.getCommandAttributes() == null || command.getCommandAttributes().get("status") == null)
            return CommandsInvoker.createUnknownCommandBoundary(commandName, "Please enter status of service");
        //if (status != "NOT YET" && status != "REJECTED" && status != "APPROVED")
        //   return CommandsInvoker.createUnknownCommandBoundary(commandName, "Please enter legal status");

        String supplierMail = command.getCommandAttributes().get("supplierMail").toString();
        return this.objectCrud.findServicesByMailAndStatus(supplierMail, status);
    }
}

