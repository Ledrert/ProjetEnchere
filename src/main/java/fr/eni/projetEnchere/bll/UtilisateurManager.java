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
	
	private void rechargerListe() throws DalException {
		listeUser = dao.selectAllUtilisateur();
	}
	
	private UtilisateurManager() throws DalException {	
		dao = DAOFactory.getUtilisateurDAO();
		try {
			rechargerListe();
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
		rechargerListe();
		
	}

	public void supprimerUtilisateur (Utilisateur utilisateur) throws DalException {
		dao.supprimerUtilisateur(utilisateur);
		rechargerListe();
		
	}
	
	public Utilisateur getById(int id) throws DalException{
		try {
			return dao.selectUtilisateurByiD(id);
		} catch (DalException e) {
			throw new DalException("erreur chargement de l'utilisateur", e);
		}
	}

	public boolean verifPseudoExist (String pseudo) throws DalException {
		for (Utilisateur user : listeUser)
			if (pseudo.equals(user.getPseudo())) {
				return true;
			}
		return false;
		}
	
	public boolean verifEmailExist (String email) throws DalException {
		for (Utilisateur user : listeUser)
			if (email.equals(user.getEmail())) {
				return true;
			}
		return false;
		}
	
	public boolean verifPassword(String password, String verif) throws DalException {
		if(password.equals(verif)) {
			return false;
		}
		return true;
	}
	
	public void paiementEnchere(Utilisateur oldEncherisseur, Utilisateur newEncherisseur, int sommeOld, int SommeNew) throws DalException {
		dao.crediter(oldEncherisseur, sommeOld);
		dao.debiter(newEncherisseur, SommeNew);
		listerUtilisateur();
	}
	
}