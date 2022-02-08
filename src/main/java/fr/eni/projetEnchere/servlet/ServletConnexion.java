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
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Connexion.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String identifiant = request.getParameter("identifiant");
		String password = request.getParameter("MDP");
//		String check = request.getParameter("saveMDP");
//		if(check.equals("save")) {
//			//creation de cookie
//			
//			   //Creation de cookies pour le pseudo et le mot de passe       
//		     Cookie cpseudo = new Cookie("pseudo",request.getParameter("pseudo"));
//		     Cookie cpassword = new Cookie("password",request.getParameter("password"));
//
//		      //Date d'expiration des cookies
//		     cpseudo.setMaxAge(60*60*24*30); 
//		     cpassword.setMaxAge(60*60*24*30); 
//
//		     response.addCookie( cpseudo );
//		     response.addCookie( cpassword );
//		}
		UtilisateurManager um;
		Utilisateur utilisateur = null;
		RequestDispatcher rd = null;
		try {
			um = UtilisateurManager.getInstance();
			if (identifiant.contains("@")) { // Si c'est un mail
				identifiant = um.chercherPseudo(identifiant); // Récupération du pseudo via le mail
				if (identifiant.isEmpty()) { // N'a pas renvoyé de pseudo car pas de mail reconnu
					response.sendRedirect("connexion?error=1");
					return;
				}
			}

			utilisateur = um.verifIdentifiants(identifiant, password);
			if (password.isEmpty()) {
				response.sendRedirect("connexion?error=1");
				return;
			} else if (utilisateur == null) { // si pas d'utilisateur reconnu via le pseudo
				response.sendRedirect("connexion?error=1");
				return;

			} else {
				HttpSession session = request.getSession();
				session.setAttribute("user", utilisateur);
			}

		} catch (DalException e) {
			e.printStackTrace();
		}
		response.sendRedirect("/ProjetEnchere/enchere");
	}
}
