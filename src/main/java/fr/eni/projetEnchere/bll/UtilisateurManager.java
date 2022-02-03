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

	public void ajouterUtilisateur(String pseudo, String nom, String prenom, String email,String tel, String rue, String codePostal, 
		String ville, String password) throws DalException {
		UtilisateurDAO ud = DAOFactory.getUtilisateurDAO();
		Utilisateur utilisateur = new Utilisateur(pseudo, nom, prenom, email, rue, codePostal, ville, password, false);
		utilisateur.setTelephone(tel);
		ud.AjouterUtilisateur(utilisateur);
	}

	
	public String chercherPseudo(String email) throws DalException {
		UtilisateurDAO ud = DAOFactory.getUtilisateurDAO();
		return ud.chercherPseudo(email);
	}
	
	public Utilisateur verifIdentifiants(String pseudo, String password) throws DalException {
		UtilisateurDAO ud = DAOFactory.getUtilisateurDAO();
		return ud.verifIdentifiants(pseudo, password);
		
	}
}


