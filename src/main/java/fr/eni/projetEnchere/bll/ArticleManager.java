package fr.eni.projetEnchere.bll;

public class ArticleManager {

	private static ArticleManager instance;
	
	public static ArticleManager getInstance() {
		if(instance == null) {
			instance = new ArticleManager();
		}
		return instance;
	}
	
	private ArticleManager() {
		
	}
	
}
