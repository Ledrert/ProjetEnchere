package fr.eni.projetEnchere.dal;

import java.util.List;

import fr.eni.projetEnchere.bo.Article;
import fr.eni.projetEnchere.bo.Categorie;

public interface ArticleDAO {
	
	public List<Article> listerArticle() throws DalException;
	public List<Categorie> listerCategorie() throws DalException;
	public Categorie rechercherCategorie(String nom) throws DalException;
	public void modifierArticle(Article art) throws DalException;
	public void ajouterArticle(Article art) throws DalException;
	public void supprimerArticle(int index) throws DalException;
}