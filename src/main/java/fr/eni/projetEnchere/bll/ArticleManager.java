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
	private ArticleDAO dao;
	private static List<Article> listeArticles;
	private static List<Categorie> listeCat;
	
	public static ArticleManager getInstance() throws DalException {
		if(instance == null) {
			instance = new ArticleManager();
		}
		return instance;
	}
	
	private ArticleManager() throws DalException {
		dao = DAOFactory.getArticleDAO();
		try {
			listeCat = dao.listerCategorie();
			listeArticles = dao.listerArticle();
		} catch (DalException e) {
			throw new DalException("erreur chargement liste", e);
		}
	}
	
	
	public void ajouterArticle(String article, String description, Categorie categorie, int prix, Date dateDebut, Date dateFin, Utilisateur utilisateur) throws DalException {
			Article art = new Article(article, description, prix, dateDebut, dateFin, utilisateur, categorie);
			dao.ajouterArticle(art);
			listeArticles.add(art);
		}
	
	public List<Article> listerArticle() throws DalException {
		return listeArticles;
	}
	
	public List<String> listerCategorie() throws DalException{
		List<String> liste = new ArrayList<String>();
		for(Categorie cat : listeCat) {
			liste.add(cat.getLibelle());
		}
		return liste;
	}
	
	public Categorie rechercherCategorie(String libelle) throws DalException {
		for(Categorie cat : listeCat) {
			if(cat.getLibelle().equals(libelle)) {
				return cat;
			}
		}
		return null;
	}
	
}
