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
	
	/**
	 * Permet d'actualiser la liste d'utilisateur
	 * @throws DalException
	 */
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
	
	/**
	 * Permet de lister l'ensemble des utilisateurs
	 * @return
	 */
	public List<Utilisateur> listerUtilisateur() {
		return listeUser;
		
	}
	/**
	 * Permet d'ajouter un utilisateur avec ses différentes informations sur la base de donnée
	 * @param pseudo
	 * @param nom
	 * @param prenom
	 * @param email
	 * @param tel
	 * @param rue
	 * @param codePostal
	 * @param ville
	 * @param password
	 * @throws DalException
	 */
	public void ajouterUtilisateur(String pseudo, String nom, String prenom, String email,String tel, String rue, String codePostal, 
		String ville, String password) throws DalException {
		Utilisateur utilisateur = new Utilisateur(pseudo, nom, prenom, email, rue, codePostal, ville, password, false);
		utilisateur.setTelephone(tel);
		dao.AjouterUtilisateur(utilisateur);
		listeUser.add(utilisateur);
	}
	/**
	 * Permet de récupérer le pseudo d'un utilisateur grâce à son email
	 * @param email
	 * @return
	 * @throws DalException
	 */
	public String chercherPseudo(String email) throws DalException {
		return dao.chercherPseudo(email);
	}
	
	/**
	 * Permet de vérifier la correspondance entre le pseudo et le mot de passe dans la base de donnée
	 * @param pseudo
	 * @param password
	 * @return
	 * @throws DalException
	 */
	public Utilisateur verifIdentifiants(String pseudo, String password) throws DalException {
		return dao.verifIdentifiants(pseudo, password);
		
	}

	/**
	 * Permet de modifier les données utilisateur dans la base de donnée
	 * @param utilisateur
	 * @throws DalException
	 */
	public void ModifierUtilisateur(Utilisateur utilisateur) throws DalException {
		dao.ModifierUtilisateur(utilisateur);
		rechargerListe();
		
	}

	/**
	 * Permet de supprimer un utilisateur
	 * @param utilisateur
	 * @throws DalException
	 */
	public void supprimerUtilisateur (Utilisateur utilisateur) throws DalException {
		dao.supprimerUtilisateur(utilisateur);
		rechargerListe();
		
	}
	
	/**
	 * Permet de récupérer un utilisateur à partir de son ID
	 * @param id
	 * @return
	 * @throws DalException
	 */
	public Utilisateur getById(int id) throws DalException{
		try {
			return dao.selectUtilisateurByiD(id);
		} catch (DalException e) {
			throw new DalException("erreur chargement de l'utilisateur", e);
		}
	}

	/**
	 * Permet de vérifier que le pseudo entré est présent dans la base de donnée
	 * @param pseudo
	 * @return
	 * @throws DalException
	 */
	public boolean verifPseudoExist (String pseudo) throws DalException {
		for (Utilisateur user : listeUser)
			if (pseudo.equals(user.getPseudo())) {
				return true;
			}
		return false;
		}
	
	/**
	 * Permet de vérifier que l'email entré est présent dans la base de donnée
	 * @param email
	 * @return
	 * @throws DalException
	 */
	public boolean verifEmailExist (String email) throws DalException {
		for (Utilisateur user : listeUser)
			if (email.equals(user.getEmail())) {
				return true;
			}
		return false;
		}
	
	/**
	 * Permet de vérifier que les deux mots de passe entrés lors de l'insciption sont identiques
	 * @param password
	 * @param verif
	 * @return
	 * @throws DalException
	 */
	public boolean verifPassword(String password, String verif) throws DalException {
		if(password.equals(verif)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Permet de créditer l'ancien encherisseur et débiter le nouvel encherisseur
	 * @param oldEncherisseur
	 * @param newEncherisseur
	 * @param sommeOld
	 * @param SommeNew
	 * @throws DalException
	 */
	public void paiementEnchere(Utilisateur oldEncherisseur, Utilisateur newEncherisseur, int sommeOld, int SommeNew) throws DalException {
		if(oldEncherisseur.getNoUtilisateur() == newEncherisseur.getNoUtilisateur()) {
			dao.debiter(newEncherisseur, SommeNew-sommeOld);
			} else {
			dao.crediter(oldEncherisseur, sommeOld);
			dao.debiter(newEncherisseur, SommeNew);
		}
		listerUtilisateur();
	}
	
}