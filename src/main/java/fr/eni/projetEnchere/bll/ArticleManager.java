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
	 * recharge la liste grâce à la base de données
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
	 * au chargement, vérifie si des enchères se sont terminés depuis. La procédure vérifie si la vente a une enchère et, dans ce cas, met à jour la vente
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
	 * Ajoute un Article à la base de donnée
	 * @param art : l'Article mis en vente
	 * @throws DalException propagation
	 */
	public void ajouterArticle(Article art) throws DalException {
			dao.ajouterArticle(art);
			rechargerListe();
		}
	
	/**
	 * Récupère la liste d'Articles en vente de la base de données
	 * @return la Liste de tous les Articles
	 * @throws DalException propagation
	 */
	public List<Article> listerArticle() throws DalException {
		rechargerListe();
		return listeArticles;
	}
	
	/**
	 * récupère la liste des Catégories d'Article
	 * @return la Liste de toutes les Catégories
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
	 * rechercher la Catégorie via son libellé
	 * @param libelle : le libellé de la Catégorie
	 * @return l'objet Catégorie associé
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
	 * récupère la dernière Enchere d'un Article
	 * @param art : un Article
	 * @return la dernière Enchère de l'Article associé
	 * @throws DalException propagation
	 */
	public Enchere getDernierEnchere(Article art) throws DalException {
		return dao.dernierEncherisseur(art);
	}
	
	
	/**
	 * Récupère la liste d'Article disponible à l'achat pour l'utilisateur
	 * @param user : l'Utilisateur actuel
	 * @return une Liste d'Article disponible à l'achat pour l'utilisateur
	 * @throws DalException propagation
	 */
	public List<Article> mesAchats(Utilisateur user) throws DalException {
		return dao.listerAchats(user);
	}
	
	/**
	 * Récupère la liste d'Article disponible à l'achat et dont les enchères sont en cours
	 * @param listeArt : la Liste d'Article où les articles trouvés seront ajoutés
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
	 * Récupère la liste d'Article disponible à l'achat et dont l'Utilisateur a fait une enchère
	 * @param listeArt : la Liste d'Article où les articles trouvés seront ajoutés
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
	 * Récupère la liste d'Article dont l'utilisateur a remporté l'enchère
	 * @param listeArt : la Liste d'Article où les articles trouvés seront ajoutés
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
	 * récupère la liste d'Article vendu par l'utilisateur
	 * @param user : l'utilisateur actuel
	 * @return une Liste d'Article 
	 * @throws DalException propagation
	 */
	public List<Article> mesVentes(Utilisateur user) throws DalException{
		return dao.listerVentes(user);
	}
	
	/**
	 * récupère la liste d'Article en cours d'enchères vendu par l'utilisateur
	 * @param listeArt : la Liste d'Article où les articles trouvés seront ajoutés
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
	 * récupère la liste d'Article dont les enchères n'ont pas débutés et vendu par l'utilisateur
	 * @param listeArt : la Liste d'Article où les articles trouvés seront ajoutés
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
	 * récupère la liste d'Article dont les enchères sont terminés et vendu par l'utilisateur
	 * @param listeArt : la Liste d'Article où les articles trouvés seront ajoutés
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
	 * Récupère l'article via son numéro
	 * @param id : le numéro de l'article recherchée
	 * @return l'article recherchée
	 */
	public Article getById(int id) throws DalException{
		return dao.selectByID(id);
	}
	
	/**
	 * ajoute une adresse de Retrait à l'article associé
	 * @param ret : l'adresse de Retrait
	 * @throws DalException propagation
	 */
	public void ajoutRetrait(Retrait ret) throws DalException {
		dao.ajoutRetrait(ret);
	}
	
	/**
	 * ajoute une enchère à l'Article associé
	 * @param enc : l'Enchère sur l'Article
	 * @throws DalException propagation
	 */
	public void ajouterEnchere(Enchere enc) throws DalException {
		dao.ajoutEnchere(enc);
		rechargerListe();
	}
}
