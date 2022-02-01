package fr.eni.projetEnchere.bo;

public class Categorie {

	//Declare
	private int noCategorie;
	private String libelle;
	
	
	//Constructors
	public Categorie() {
	}
 	
	
	public Categorie(String libelle) {
		this.libelle = libelle;
	}
	
		
	//Getters & Setters
	public int getNoCategorie() {
		return noCategorie;
	}
	public void setNoCategorie(int noCategorie) {
		this.noCategorie = noCategorie;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}	
}
