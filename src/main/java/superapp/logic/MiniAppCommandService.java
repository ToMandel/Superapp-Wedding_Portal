package superapp.logic;

import superapp.boundries.MiniAppCommandBoundary;

import java.util.List;

public interface MiniAppCommandService {

    @Deprecated
    public Object invokeCommand(MiniAppCommandBoundary command);
    public List<MiniAppCommandBoundary> getAllCommands();
    @Deprecated
    public List<MiniAppCommandBoundary> getAllMiniAppCommands(String miniAppName);
    public void deleteAllCommands();

}
