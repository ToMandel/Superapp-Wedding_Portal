package superapp.logic;

import java.util.List;

import superapp.boundries.MiniAppCommandBoundary;

public interface MiniAppCommandServiceWithPagination extends MiniAppCommandService {
	public List<MiniAppCommandBoundary> getAllMiniAppCommands(String miniAppName,String superAppName,String email,int size, int page);
	

}
