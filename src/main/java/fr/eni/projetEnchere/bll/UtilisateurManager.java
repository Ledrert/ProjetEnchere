package fr.eni.projetEnchere.bll;

import java.util.List;

import fr.eni.projetEnchere.bo.Utilisateur;
import fr.eni.projetEnchere.dal.DAOFactory;
import fr.eni.projetEnchere.dal.DalException;
import fr.eni.projetEnchere.dal.UtilisateurDAO;

public class UtilisateurManager {

	private static UtilisateurManager instance;
	private UtilisateurDAO dao;
	private static List<Utilisateur> listeUser;
	
	public static UtilisateurManager getInstance() throws DalException {
		if(instance == null) {
			instance = new UtilisateurManager();
		}
		return instance;
	}
	
	private UtilisateurManager() throws DalException {	
		dao = DAOFactory.getUtilisateurDAO();
		try {
			listeUser = dao.selectAllUtilisateur();
		} catch (DalException e) {
			throw new DalException("erreur chargement liste utilisateur", e);
		}
	}
	
	public List<Utilisateur> listerUtilisateur() {
		return listeUser;
	}

	public void ajouterUtilisateur(String pseudo, String nom, String prenom, String email,String tel, String rue, String codePostal, 
		String ville, String password) throws DalException {
		Utilisateur utilisateur = new Utilisateur(pseudo, nom, prenom, email, rue, codePostal, ville, password, false);
		utilisateur.setTelephone(tel);
		dao.AjouterUtilisateur(utilisateur);
		listeUser.add(utilisateur);
	}
	
	public String chercherPseudo(String email) throws DalException {
		return dao.chercherPseudo(email);
	}
	
	public Utilisateur verifIdentifiants(String pseudo, String password) throws DalException {
		return dao.verifIdentifiants(pseudo, password);
	}

	public void ModifierUtilisateur(Utilisateur utilisateur) throws DalException {
		dao.ModifierUtilisateur(utilisateur);
		listeUser.set(utilisateur.getNoUtilisateur()-1, utilisateur);
	}

	public void supprimerUtilisateur (Utilisateur utilisateur) throws DalException {
		dao.supprimerUtilisateur(utilisateur);
		listeUser.remove(utilisateur.getNoUtilisateur()-1);
	}


}



