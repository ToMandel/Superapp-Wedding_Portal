package superapp.miniapps.global;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import superapp.Converter;
import superapp.boundries.MiniAppCommandBoundary;
import superapp.dal.SupperAppObjectCrud;
import superapp.data.SuperAppObjectEntity;
import superapp.miniapps.CommandsInvoker;
import superapp.miniapps.MiniAppsCommand;

@Component
public class GetObjectBoundaryByMail implements MiniAppsCommand {

    private SupperAppObjectCrud objectCrud;
    private Converter converter;

    @Autowired
    public void setObjectCrud(SupperAppObjectCrud objectCrud) {
        this.objectCrud = objectCrud;
    }

    @Autowired
    public void setConverter(Converter converter) {
        this.converter = converter;
    }

    @Override
    public Object execute(MiniAppCommandBoundary command) {
        if (command.getCommandAttributes() == null || command.getCommandAttributes().get("mail") == null) {
            String commandName = command.getCommand();
            return CommandsInvoker.createUnknownCommandBoundary(commandName, "Please enter mail of the object");
        }
        String mail = command.getCommandAttributes().get("mail").toString();
        //return this.objectCrud.findObjectByMail(mail);
        String objectId = this.objectCrud.findObjectByMail(mail).getObjectId();
        return converter.superAppObjectToBoundary(this.objectCrud.findById(objectId).get());
    }
}
