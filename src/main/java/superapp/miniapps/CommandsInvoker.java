package superapp.miniapps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import superapp.miniapps.customers.GetSupplierFreeDates;
import superapp.miniapps.suppliers.GetTypes;
import superapp.miniapps.tables.GetAllGuestsOfUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CommandsInvoker {

    private GetAllGuestsOfUser getAllGuestsOfUser;
    private GetSupplierFreeDates getSupplierFreeDates;
    private GetTypes getTypes;

    private Map<MiniAppsCommand.MINI_APPS, List<String>> commandsInMiniApp;

    private List<String> supplierCommands;
    private List<String> customersCommands;
    private List<String> tablesCommands;
    @Autowired
    public CommandsInvoker(GetAllGuestsOfUser getAllGuestsOfUser,
                           GetSupplierFreeDates getSupplierFreeDates,
                           GetTypes getTypes){
        supplierCommands = new ArrayList<String>();
        customersCommands = new ArrayList<String>();
        tablesCommands = new ArrayList<String>();
        commandsInMiniApp = new HashMap<>();

        this.getAllGuestsOfUser = getAllGuestsOfUser;
        this.getSupplierFreeDates = getSupplierFreeDates;
        this.getTypes = getTypes;

        setMiniAppCommands();

    }

    private void setMiniAppCommands(){
        supplierCommands.add("getTypes");

        customersCommands.add("getSupplierFreeDates");

        tablesCommands.add("getAllGuestsOfUser");

        commandsInMiniApp.put(MiniAppsCommand.MINI_APPS.SUPPLIERS, supplierCommands);
        commandsInMiniApp.put(MiniAppsCommand.MINI_APPS.CUSTOMERS, customersCommands);
        commandsInMiniApp.put(MiniAppsCommand.MINI_APPS.TABLES, tablesCommands);
    }

    public MiniAppsCommand createCommandClass(String commandName){
        switch (commandName){
            case("getAllGuestsOfUser"):
                return getAllGuestsOfUser;
            case("getSupplierFreeDates"):
                return getSupplierFreeDates;
            case ("getTypes"):
                return getTypes;
            default:
                return null;
        }
    }

    public Map<MiniAppsCommand.MINI_APPS, List<String>> getCommandsInMiniApp() {
        return commandsInMiniApp;
    }

}