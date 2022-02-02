package fr.eni.projetEnchere.dal;

public class DAOFactory {

	public static UtilisateurDAO getUtilisateurDAO() {
		UtilisateurDAO utilisateurDao = new UtilisateurDAOImpl();
		return utilisateurDao;
	}
	
	public static ArticleDAO getArticleDAO() {
		ArticleDAO articleDao = new ArticleDaoImpl();
		return articleDao;
	}
}
