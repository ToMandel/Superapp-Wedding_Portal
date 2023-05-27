package superapp.miniapps;

import superapp.boundries.MiniAppCommandBoundary;

public interface MiniAppsCommand {

    public Object execute(MiniAppCommandBoundary command);

    enum MINI_APPS {
        UNKNOWN_MINIAPP,
        SUPPLIERS,
        CUSTOMERS,
        TABLES
    }

    public static MINI_APPS getMiniAppName(String miniAppName) {
        try {
            return MINI_APPS.valueOf(miniAppName);
        } catch (Exception e) {
            return MINI_APPS.UNKNOWN_MINIAPP;
        }
    }


}
