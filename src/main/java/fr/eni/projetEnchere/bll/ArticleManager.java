package fr.eni.projetEnchere.bll;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import fr.eni.projetEnchere.bo.Article;
import fr.eni.projetEnchere.bo.Categorie;
import fr.eni.projetEnchere.bo.Utilisateur;
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
	
	
	public void ajouterArticle(String article, String description, Categorie categorie, int prix, Date dateDebut, Date dateFin, Utilisateur utilisateur) throws DalException {
			ArticleDAO ad = DAOFactory.getArticleDAO();
			Article art = new Article(article, description, dateDebut, dateFin, utilisateur, categorie);
			art.setPrixInitial(prix);
			ad.ajouterArticle(art);
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
	
	public Categorie rechercherCategorie(String libelle) throws DalException {
		ArticleDAO dao = DAOFactory.getArticleDAO();
		return dao.rechercherCategorieParLibelle(libelle);
	}
	
}
