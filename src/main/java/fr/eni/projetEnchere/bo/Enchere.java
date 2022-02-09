package fr.eni.projetEnchere.bo;

import java.sql.Date;

public class Enchere {

	//Declare
	private Date dateEnchere;
	private int montantEnchere;
	private Article articleVendu;
    private Utilisateur encherisseur;
	
	//Constructors	
	public Enchere() {		
	}
	
    public Enchere(Utilisateur encherisseur, Article articleVendu, Date dateEnchere, int montantEnchere) {
        this.encherisseur = encherisseur;
        this.articleVendu = articleVendu;
        this.dateEnchere = dateEnchere;
        this.montantEnchere = montantEnchere;
    }

    
    //Getters and Setters
	public Utilisateur getNoEncherisseur() {
		return encherisseur;
	}

	public void setNoEncherisseur(Utilisateur noEncherisseur) {
		this.encherisseur = noEncherisseur;
	}

	public Article getArticleVendu() {
		return articleVendu;
	}

	public void getArticleVendu(Article articleVendu) {
		this.articleVendu = articleVendu;
	}

	public Date getDateEnchere() {
		return dateEnchere;
	}

	public void setDateEnchere(Date dateEnchere) {
		this.dateEnchere = dateEnchere;
	}

	public int getMontantEnchere() {
		return montantEnchere;
	}

	public void setMontantEnchere(int montantEnchere) {
		this.montantEnchere = montantEnchere;
	}

	@Override
	public String toString() {
		return "Enchere [dateEnchere=" + dateEnchere + ", montantEnchere=" + montantEnchere + ", articleVendu="
				+ articleVendu + ", encherisseur=" + encherisseur + "]";
	}   
}