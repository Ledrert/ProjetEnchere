package fr.eni.projetEnchere.dal;

import java.util.List;

import fr.eni.projetEnchere.bo.Utilisateur;

public interface UtilisateurDAO {

	public void AjouterUtilisateur(Utilisateur utilisateur) throws DalException;
	public Utilisateur selectUtilisateurByiD (int index) throws DalException;
	public void ModifierUtilisateur(Utilisateur utilisateur) throws DalException;
	public void supprimerUtilisateur (Utilisateur utilisateur) throws DalException;	
	public List<Utilisateur> selectAllUtilisateur (Utilisateur utilisateur) throws DalException;
}
