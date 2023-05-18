package superapp.logic;

import java.util.List;

import superapp.boundries.MiniAppCommandBoundary;

public interface MiniAppCommandServiceWithPagination extends MiniAppCommandServiceWithAsyncSupport {
	public List<MiniAppCommandBoundary> getAllMiniAppCommands(String superAppName, String miniAppName,String email,int page, int size);

	public List<MiniAppCommandBoundary> getAllCommands(String superAppName, String email, int page, int size);

	public void deleteAllCommands (String superAppName, String email);
	

}
