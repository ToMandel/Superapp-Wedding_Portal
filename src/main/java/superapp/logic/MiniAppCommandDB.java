package superapp.logic;

import org.springframework.stereotype.Service;
import superapp.boundries.MiniAppCommandBoundary;

import java.util.List;

@Service
public class MiniAppCommandDB implements MiniAppCommandService{


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
