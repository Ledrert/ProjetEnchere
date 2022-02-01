package fr.eni.projetEnchere.bo;

public class Categorie {

	//Declare
	private String nomCategorie;
	private String libelle;
	
	
	//Constructors
	public Categorie() {
	}
 	
	
	public Categorie(String libelle) {
		this.libelle = libelle;
	}
	
		
	//Getters & Setters
	public String getNomCategorie() {
		return nomCategorie;
	}
	public void setNomCategorie(String nomCategorie) {
		this.nomCategorie = nomCategorie;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}	
}
