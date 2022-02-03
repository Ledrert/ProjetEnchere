package fr.eni.projetEnchere.bll;

import java.util.ArrayList;
import java.util.List;

import fr.eni.projetEnchere.bo.Categorie;
import fr.eni.projetEnchere.dal.ArticleDAO;
import fr.eni.projetEnchere.dal.DAOFactory;
import fr.eni.projetEnchere.dal.DalException;

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
	
	public List<String> listerCategorie() throws DalException{
		ArticleDAO dao = DAOFactory.getArticleDAO();
		List<Categorie> cats = dao.listerCategorie();
		List<String> liste = new ArrayList<String>();
		for(Categorie cat : cats) {
			liste.add(cat.getLibelle());
		}
		return liste;
	}
	
}
