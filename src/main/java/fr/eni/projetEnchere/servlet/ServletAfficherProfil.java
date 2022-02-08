package fr.eni.projetEnchere.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.projetEnchere.bll.ArticleManager;
import fr.eni.projetEnchere.bo.Utilisateur;
import fr.eni.projetEnchere.dal.DalException;

/**
 * Servlet implementation class ServletAfficherProfil
 */
@WebServlet(name="/ServletAfficherProfil", urlPatterns = "/profil")
public class ServletAfficherProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ServletAfficherProfil() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Utilisateur user = null;
				
		Map<String, String> menu = new HashMap<>();
		if(session != null) { //S'il y a une session (donc : un login a été fait)
			user = (Utilisateur)session.getAttribute("user"); //récupération des informations de l'utilisateur connecté
			if(user == null) {
				menu.put("/connexion", "Se connecter");
				menu.put("/inscription", "S'inscrire");
			} else {
				menu.put("/vendre", "Vendre un article");
				menu.put("/profil", "Mon profil");
				menu.put("/deconnexion", "Déconnexion");
			}
		} else {
			menu.put("/connexion", "Se connecter");
			menu.put("/inscription", "S'inscrire");
		}
		request.setAttribute("user", user);
		request.setAttribute("listMenu", menu);
		request.setAttribute("liensMenu", menu.keySet());
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/AfficherProfil.jsp");
		rd.forward(request, response);
		
	}
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/AfficherProfil.jsp");
		HttpSession session = request.getSession();
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
		request.setAttribute("user", utilisateur);
		rd.forward(request, response);
	}

}

