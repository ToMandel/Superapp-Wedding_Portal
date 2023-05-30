package superapp.miniapps.global;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import superapp.boundries.MiniAppCommandBoundary;
import superapp.dal.SupperAppObjectCrud;
import superapp.miniapps.CommandsInvoker;
import superapp.miniapps.MiniAppsCommand;

@Component
public class GetObjectByMail implements MiniAppsCommand {

    private SupperAppObjectCrud objectCrud;

    @Autowired
    public void setObjectCrud(SupperAppObjectCrud objectCrud) {
        this.objectCrud = objectCrud;
    }
    @Override
    public Object execute(MiniAppCommandBoundary command) {
        if (command.getCommandAttributes() == null || command.getCommandAttributes().get("mail") == null) {
            String commandName = command.getCommand();
            return CommandsInvoker.createUnknownCommandBoundary(commandName, "Please enter mail of the object");
        }
        String mail = command.getCommandAttributes().get("mail").toString();
        return this.objectCrud.findObjectByMail(mail);

    }
}
