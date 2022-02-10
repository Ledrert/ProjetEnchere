package fr.eni.projetEnchere.bll;

import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.List;

import fr.eni.projetEnchere.bo.Article;
import fr.eni.projetEnchere.bo.Categorie;
import fr.eni.projetEnchere.bo.Enchere;
import fr.eni.projetEnchere.bo.Retrait;
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
	
	/**
	 * recharge la liste gr�ce � la base de donn�es
	 * @throws DalException propagation
	 */
	private void rechargerListe() throws DalException {
		listeCat = dao.listerCategorie();
		listeArticles = dao.listerArticle();
	}
	
	private ArticleManager() throws DalException {
		dao = DAOFactory.getArticleDAO();
		try {
			rechargerListe();
			verifierFinEnchere();
		} catch (DalException e) {
			throw new DalException("erreur chargement liste article", e);
		}
	}
	
	/**
	 * au chargement, v�rifie si des ench�res se sont termin�s depuis. La proc�dure v�rifie si la vente a une ench�re et, dans ce cas, met � jour la vente
	 * @throws DalException propagation
	 */
	private void verifierFinEnchere() throws DalException {
		Date now = new Date(Calendar.getInstance().getTime().getTime());
		for(Article art : listeArticles) {
			if(art.getDateFinEncheres().before(now) && art.getUserAcheteur() == null) {
				if(art.getListeEnchere().isEmpty()) {
					art.setUtilisateurAcheteur(art.getUserVendeur());
					art.setPrixVente(art.getPrixInitial());
				} else {
					Enchere vente = dao.dernierEncherisseur(art);
					dao.updateFinEnchere(art, vente);
					art.setUtilisateurAcheteur(vente.getNoEncherisseur());
					art.setPrixVente(vente.getMontantEnchere());
					DAOFactory.getUtilisateurDAO().crediter(art.getUserVendeur(), vente.getMontantEnchere());
					rechargerListe();
				}
			}
		}
	}
	
	/**
	 * Ajoute un Article � la base de donn�e
	 * @param art : l'Article mis en vente
	 * @throws DalException propagation
	 */
	public void ajouterArticle(Article art) throws DalException {
			dao.ajouterArticle(art);
			rechargerListe();
		}
	
	/**
	 * R�cup�re la liste d'Articles en vente de la base de donn�es
	 * @return la Liste de tous les Articles
	 * @throws DalException propagation
	 */
	public List<Article> listerArticle() throws DalException {
		rechargerListe();
		return listeArticles;
	}
	
	/**
	 * r�cup�re la liste des Cat�gories d'Article
	 * @return la Liste de toutes les Cat�gories
	 * @throws DalException propagation
	 */
	public List<String> listerCategorie() throws DalException{
		List<String> liste = new ArrayList<String>();
		for(Categorie cat : listeCat) {
			liste.add(cat.getLibelle());
		}
		return liste;
	}
	
	/**
	 * rechercher la Cat�gorie via son libell�
	 * @param libelle : le libell� de la Cat�gorie
	 * @return l'objet Cat�gorie associ�
	 * @throws DalException propagation
	 */
	public Categorie rechercherCategorie(String libelle) throws DalException {
		for(Categorie cat : listeCat) {
			if(cat.getLibelle().equals(libelle)) {
				return cat;
			}
		}
		return null;
	}
	
	/**
	 * r�cup�re la derni�re Enchere d'un Article
	 * @param art : un Article
	 * @return la derni�re Ench�re de l'Article associ�
	 * @throws DalException propagation
	 */
	public Enchere getDernierEnchere(Article art) throws DalException {
		return dao.dernierEncherisseur(art);
	}
	
	
	/**
	 * R�cup�re la liste d'Article disponible � l'achat pour l'utilisateur
	 * @param user : l'Utilisateur actuel
	 * @return une Liste d'Article disponible � l'achat pour l'utilisateur
	 * @throws DalException propagation
	 */
	public List<Article> mesAchats(Utilisateur user) throws DalException {
		return dao.listerAchats(user);
	}
	
	/**
	 * R�cup�re la liste d'Article disponible � l'achat et dont les ench�res sont en cours
	 * @param listeArt : la Liste d'Article o� les articles trouv�s seront ajout�s
	 * @param user : l'Utilisateur actuel
	 * @return une Liste d'Article 
	 * @throws DalException propagation
	 */
	public List<Article> encheresOuvertes(List<Article> listeArt, Utilisateur user) throws DalException {
		List<Article> liste = dao.listerEnchereOuvertes(user);
		if(!liste.isEmpty())
		for(Article art : liste) listeArt.add(art);
		return listeArt;
	}
	
	/**
	 * R�cup�re la liste d'Article disponible � l'achat et dont l'Utilisateur a fait une ench�re
	 * @param listeArt : la Liste d'Article o� les articles trouv�s seront ajout�s
	 * @param user : l'Utilisateur actuel
	 * @return une Liste d'Article 
	 * @throws DalException propagation
	 */
	public List<Article> mesEncheresEnCours(List<Article> listeArt, Utilisateur user) throws DalException{
		List<Article> liste = dao.listerEnchereEnCours(user);
		if(!liste.isEmpty())
		for(Article art : liste) listeArt.add(art);
		return listeArt;
	}
	
	/**
	 * R�cup�re la liste d'Article dont l'utilisateur a remport� l'ench�re
	 * @param listeArt : la Liste d'Article o� les articles trouv�s seront ajout�s
	 * @param user : l'Utilisateur actuel
	 * @return une Liste d'Article 
	 * @throws DalException propagation
	 */
	public List<Article> mesEncheresGagnes(List<Article> listeArt, Utilisateur user) throws DalException{
		List<Article> liste = dao.chercherEnchereRemportee(user);
		if(!liste.isEmpty())
		for(Article art : liste) listeArt.add(art);
		return listeArt;
	}
	
	/**
	 * r�cup�re la liste d'Article vendu par l'utilisateur
	 * @param user : l'utilisateur actuel
	 * @return une Liste d'Article 
	 * @throws DalException propagation
	 */
	public List<Article> mesVentes(Utilisateur user) throws DalException{
		return dao.listerVentes(user);
	}
	
	/**
	 * r�cup�re la liste d'Article en cours d'ench�res vendu par l'utilisateur
	 * @param listeArt : la Liste d'Article o� les articles trouv�s seront ajout�s
	 * @param user : l'utilisateur actuel
	 * @return une Liste d'Article 
	 * @throws DalException propagation
	 */
	public List<Article> mesVentesEnCours(List<Article> listeArt, Utilisateur user) throws DalException{
		List<Article> liste = dao.listerVentesEnCours(user);
		if(!liste.isEmpty())
		for(Article art : liste) listeArt.add(art);
		return listeArt;
	}
	
	/**
	 * r�cup�re la liste d'Article dont les ench�res n'ont pas d�but�s et vendu par l'utilisateur
	 * @param listeArt : la Liste d'Article o� les articles trouv�s seront ajout�s
	 * @param user : l'utilisateur actuel
	 * @return une Liste d'Article 
	 * @throws DalException propagation
	 */
	public List<Article> mesVentesNonDebutees(List<Article> listeArt, Utilisateur user) throws DalException{
		List<Article> liste = dao.listerVentesNonDebut(user);
		if(!liste.isEmpty())
		for(Article art : liste) listeArt.add(art);
		return listeArt;
	}
	
	/**
	 * r�cup�re la liste d'Article dont les ench�res sont termin�s et vendu par l'utilisateur
	 * @param listeArt : la Liste d'Article o� les articles trouv�s seront ajout�s
	 * @param user : l'utilisateur actuel
	 * @return une Liste d'Article 
	 * @throws DalException propagation
	 */
	public List<Article> mesVentesTerminees(List<Article> listeArt, Utilisateur user) throws DalException{
		List<Article> liste = dao.listerVentesTerminees(user);
		if(!liste.isEmpty())
		for(Article art : liste) listeArt.add(art);
		return listeArt;
	}

	/**
	 * R�cup�re l'article via son num�ro
	 * @param id : le num�ro de l'article recherch�e
	 * @return l'article recherch�e
	 */
	public Article getById(int id) throws DalException{
		return dao.selectByID(id);
	}
	
	/**
	 * ajoute une adresse de Retrait � l'article associ�
	 * @param ret : l'adresse de Retrait
	 * @throws DalException propagation
	 */
	public void ajoutRetrait(Retrait ret) throws DalException {
		dao.ajoutRetrait(ret);
	}
	
	/**
	 * ajoute une ench�re � l'Article associ�
	 * @param enc : l'Ench�re sur l'Article
	 * @throws DalException propagation
	 */
	public void ajouterEnchere(Enchere enc) throws DalException {
		dao.ajoutEnchere(enc);
		rechargerListe();
	}
}
