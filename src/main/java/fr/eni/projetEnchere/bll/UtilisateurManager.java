package fr.eni.projetEnchere.bll;

import fr.eni.projetEnchere.bo.Utilisateur;
import fr.eni.projetEnchere.dal.DAOFactory;
import fr.eni.projetEnchere.dal.DalException;
import fr.eni.projetEnchere.dal.UtilisateurDAO;

public class UtilisateurManager {

	private static UtilisateurManager instance;
	
	public static UtilisateurManager getInstance() {
		if(instance == null) {
			instance = new UtilisateurManager();
		}
		return instance;
	}
	
	private UtilisateurManager() {	
	}

	public void addUtilisateur(String pseudo, String nom, String prenom, String email, String rue, String codePostal, 
		String ville, String password) throws DalException {
		UtilisateurDAO ud = DAOFactory.getUtilisateurDAO();
		Utilisateur utilisateur = new Utilisateur(pseudo, nom, prenom, email, rue, codePostal, ville, password, false);
		ud.AjouterUtilisateur(utilisateur);
	}

	
	
}
