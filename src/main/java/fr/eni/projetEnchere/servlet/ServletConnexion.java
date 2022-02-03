package fr.eni.projetEnchere.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.projetEnchere.bo.Utilisateur;

import fr.eni.projetEnchere.bll.UtilisateurManager;
import fr.eni.projetEnchere.bo.Utilisateur;
import fr.eni.projetEnchere.dal.DalException;
import fr.eni.projetEnchere.helpers.HashPassword;

/**
 * Servlet implementation class ServletConnexion
 */
@WebServlet(name = "/ServletConnexion", urlPatterns = "/connexion")
public class ServletConnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletConnexion() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, String> menu = new HashMap<>();
		HttpSession session = request.getSession();
		boolean isConnected = true;
		if (session != null) { // S'il y a une session (donc : un login a été fait)
			Utilisateur user = (Utilisateur) session.getAttribute("user"); // récupération des informations de
																			// l'utilisateur connecté
			if (user == null) {
				isConnected = false;
				menu.put("/connexion", "Se connecter");
				menu.put("/inscription", "S'inscrire");
			} else {
				menu.put("/vendre", "Vendre un article");
				menu.put("/profil", "Mon profil");
				menu.put("/deconnexion", "Déconnexion");
			}
		} else {
			isConnected = false;
			menu.put("/connexion", "Se connecter");
			menu.put("/inscription", "S'inscrire");
		}
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Connexion.jsp");
		request.setAttribute("listMenu", menu);
		request.setAttribute("liensMenu", menu.keySet());
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String identifiant = request.getParameter("pseudo");
		String password = request.getParameter("mot_de_passe");
		String check = request.getParameter("saveMDP");
		if(check.equals("save")) {
			//creation de cookie
			
			   //Creation de cookies pour le pseudo et le mot de passe       
		     Cookie cpseudo = new Cookie("pseudo",request.getParameter("pseudo"));
		     Cookie cpassword = new Cookie("password",request.getParameter("password"));

		      //Date d'expiration des cookies
		     cpseudo.setMaxAge(60*60*24*30); 
		     cpassword.setMaxAge(60*60*24*30); 

		     response.addCookie( cpseudo );
		     response.addCookie( cpassword );
		}
		UtilisateurManager um = UtilisateurManager.getInstance();
		Utilisateur utilisateur = null;
		RequestDispatcher rd = null;
		try {
		if (identifiant.contains("@")) {
				
					identifiant = um.chercherPseudo(identifiant);
				
		if (identifiant.isEmpty()) {
			rd = request.getRequestDispatcher("/connexion");
			System.err.println("Votre adresse mail ne correspond à un aucun pseudo.");
		} else {
			utilisateur = um.verifIdentifiants(identifiant, password);
		}
		} else {
			utilisateur = um.verifIdentifiants(identifiant, password);
			if(utilisateur == null) {
				rd = request.getRequestDispatcher("/connexion");
				System.err.println("Votre adresse mail ne correspond à un aucun pseudo.");
			}
		}
			HttpSession session = request.getSession();
			session.setAttribute("user", utilisateur);
		} catch (DalException e) {
			e.printStackTrace();
		}
				rd = request.getRequestDispatcher("/enchere");
		rd.forward(request, response);
		
	}
}

