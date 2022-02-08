package fr.eni.projetEnchere.dal;

import java.util.List;

import fr.eni.projetEnchere.bo.Article;
import fr.eni.projetEnchere.bo.Categorie;
import fr.eni.projetEnchere.bo.Utilisateur;

public interface ArticleDAO {
	
	public List<Article> listerArticle() throws DalException;
	public List<Categorie> listerCategorie() throws DalException;
	public Categorie rechercherCategorieParNom(String nom) throws DalException;
	public Categorie rechercherCategorieParLibelle(String libelle) throws DalException;
	public void modifierArticle(Article art) throws DalException;
	public void ajouterArticle(Article art) throws DalException;
	public void supprimerArticle(int index) throws DalException;
	public List<Article> listerEnchereEnCours(Utilisateur user) throws DalException;
	public List<Article> chercherVenteEnCours(Utilisateur user) throws DalException;
	List<Article> listerAchats(Utilisateur user) throws DalException;
	List<Article> chercherEnchereRemportee(Utilisateur user) throws DalException;
}
