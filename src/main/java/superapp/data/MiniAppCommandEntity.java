package superapp.data;

import superapp.boundries.MiniAppCommandBoundary;
import superapp.logic.MiniAppCommandService;

import java.util.List;

public class MiniAppCommandEntity implements MiniAppCommandService {


    @Override
    public Object invokeCommand(MiniAppCommandBoundary command){
        return null;
    }

    @Override
    public List<MiniAppCommandBoundary> getAllCommands() {
        return null;
    }

    @Override
    public List<MiniAppCommandBoundary> getAllMiniAppCommands(String miniAppName) {
        return null;
    }

    @Override
    public void deleteAllCommands() {

    }

}
