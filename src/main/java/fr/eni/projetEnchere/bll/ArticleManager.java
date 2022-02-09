package fr.eni.projetEnchere.bll;

import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.List;

import fr.eni.projetEnchere.bo.Article;
import fr.eni.projetEnchere.bo.Categorie;
import fr.eni.projetEnchere.bo.Enchere;
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
			verifierFinEnchere();
		} catch (DalException e) {
			throw new DalException("erreur chargement liste article", e);
		}
	}
	
	private void verifierFinEnchere() throws DalException {
		Date now = new Date(Calendar.getInstance().getTime().getTime());
		for(Article art : listeArticles) {
			if(art.getDateFinEncheres().after(now) && art.getUserAcheteur() != null) {
				Enchere vente = dao.dernierEncherisseur(art);
				System.out.println(vente);
				dao.updateFinEnchere(art, vente);
				art.setUtilisateurAcheteur(vente.getNoEncherisseur());
				art.setPrixVente(vente.getMontantEnchere());
			}
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
	
	public List<Article> mesAchats(Utilisateur user) throws DalException {
		return dao.listerAchats(user);
	}
	
	public List<Article> encheresOuvertes(List<Article> listeArt, Utilisateur user) throws DalException {
		List<Article> liste = dao.listerEnchereOuvertes(user);
		if(!liste.isEmpty())
		for(Article art : liste) listeArt.add(art);
		return listeArt;
	}
	
	public List<Article> mesEncheresEnCours(List<Article> listeArt, Utilisateur user) throws DalException{
		List<Article> liste = dao.listerEnchereEnCours(user);
		if(!liste.isEmpty())
		for(Article art : liste) listeArt.add(art);
		return listeArt;
	}
	
	public List<Article> mesEncheresGagnes(List<Article> listeArt, Utilisateur user) throws DalException{
		List<Article> liste = dao.chercherEnchereRemportee(user);
		if(!liste.isEmpty())
		for(Article art : liste) listeArt.add(art);
		return listeArt;
	}
	
	public List<Article> mesVentes(Utilisateur user) throws DalException{
		return dao.listerVentes(user);
	}
	
	public List<Article> mesVentesEnCours(List<Article> listeArt, Utilisateur user) throws DalException{
		List<Article> liste = dao.listerVentesEnCours(user);
		if(!liste.isEmpty())
		for(Article art : liste) listeArt.add(art);
		return listeArt;
	}
	
	public List<Article> mesVentesNonDebutees(List<Article> listeArt, Utilisateur user) throws DalException{
		List<Article> liste = dao.listerVentesNonDebut(user);
		if(!liste.isEmpty())
		for(Article art : liste) listeArt.add(art);
		return listeArt;
	}
	
	public List<Article> mesVentesTerminees(List<Article> listeArt, Utilisateur user) throws DalException{
		List<Article> liste = dao.listerVentesTerminees(user);
		if(!liste.isEmpty())
		for(Article art : liste) listeArt.add(art);
		return listeArt;
	}

	public Article getById(int id) throws DalException{
		dao = DAOFactory.getArticleDAO();
		try {
			return dao.selectByID(id);
		} catch (DalException e) {
			throw new DalException("erreur chargement de l'article", e);
		}
	}
}
