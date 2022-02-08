package fr.eni.projetEnchere.bo;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Article {
	
	//Declare
    private int noArticle;
    private String nomArticle;
    private String description;
    private Date dateDebutEncheres;
    private Date dateFinEncheres;
    private int prixInitial;
    private int prixVente;
    
    private Utilisateur userVendeur;
    private Utilisateur userAcheteur;
	private Categorie categorie;
	
    //Constructors
    public Article() {
    }

    public Article(String nomArticle, String description, int prix, Date dateDebutEncheres,Date dateFinEncheres, Utilisateur userVendeur, Categorie categorie) {
        this.nomArticle = nomArticle;
        this.description = description;
        this.prixInitial = prix;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.userVendeur = userVendeur;
        this.categorie = categorie;
    }
    
    //Getters And Setters
    public int getNoArticle() {
        return noArticle;
    }

    public void setNoArticle(int noArticle) {
        this.noArticle = noArticle;
    }

    public String getNomArticle() {
        return nomArticle;
    }

    public void setNomArticle(String nomArticle) {
        this.nomArticle = nomArticle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateDebutEncheres() {
        return dateDebutEncheres;
    }

    public void setDateDebutEncheres(Date dateDebutEncheres) {
        this.dateDebutEncheres = dateDebutEncheres;
    }

    public Date getDateFinEncheres() {
        return dateFinEncheres;
    }

    public void setDateFinEncheres(Date dateFinEncheres) {
        this.dateFinEncheres = dateFinEncheres;
    }

    public int getPrixInitial() {
        return prixInitial;
    }

    public void setPrixInitial(int prixInitial) {
        this.prixInitial = prixInitial;
    }

    public int getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(int prixVente) {
        this.prixVente = prixVente;
    }

    public Utilisateur getUserVendeur() {
        return userVendeur;
    }

    public void setUtilisateurVendeur(Utilisateur userVendeur) {
        this.userVendeur = userVendeur;
    }

    public Utilisateur getUserAcheteur() {
        return userAcheteur;
    }

    public void setUtilisateurAcheteur(Utilisateur userAcheteur) {
        this.userAcheteur = userAcheteur;
    }
    
    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
}