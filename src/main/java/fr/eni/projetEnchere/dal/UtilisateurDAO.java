package fr.eni.projetEnchere.dal;

import java.util.List;

import fr.eni.projetEnchere.bo.Utilisateur;

public interface UtilisateurDAO {

	public void AjouterUtilisateur(Utilisateur utilisateur);
	public Utilisateur selectUtilisateurByiD (int index);
	public void ModifierUtilisateur(Utilisateur utilisateur);
	public void supprimerUtilisateur (Utilisateur utilisateur);	
	public List<Utilisateur> selectAllUtilisateur (Utilisateur utilisateur);
}

