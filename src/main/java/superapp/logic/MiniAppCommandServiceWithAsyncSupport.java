package superapp.logic;

import superapp.boundries.MiniAppCommandBoundary;

public interface MiniAppCommandServiceWithAsyncSupport extends MiniAppCommandService{

    public Object invokeMiniAppCommandAsync (MiniAppCommandBoundary command, boolean isAsync);
}
