package superapp.miniapps.tables;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import superapp.boundries.MiniAppCommandBoundary;
import superapp.dal.SupperAppObjectCrud;
import superapp.logic.BadRequestException;
import superapp.miniapps.MiniAppsCommand;

@Component
public class GetAllGuestsOfUser implements MiniAppsCommand {

    private SupperAppObjectCrud objectCrud;

    @Autowired
    public void setObjectCrud(SupperAppObjectCrud objectCrud) {
        this.objectCrud = objectCrud;
    }

    @Override
    public Object execute(MiniAppCommandBoundary command) {
        String type = "guest";
        if (command.getCommandAttributes() == null || command.getCommandAttributes().get("mail") == null)
            throw new BadRequestException("Please enter the mail of the user you want to get his guests");
        String mail = command.getCommandAttributes().get("mail").toString();
        String createdBy = command.getInvokedBy().getUserId().getSuperapp() + "#" + mail;
        //we look for objects that createdBy specific user
        return this.objectCrud.findAllByTypeAndCreatedBy(type, createdBy);
    }
}
