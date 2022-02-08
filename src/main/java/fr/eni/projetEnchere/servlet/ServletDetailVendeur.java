package fr.eni.projetEnchere.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.projetEnchere.bll.UtilisateurManager;
import fr.eni.projetEnchere.bo.Utilisateur;
import fr.eni.projetEnchere.dal.DalException;

/**
 * Servlet implementation class ServletDetailVendeur
 */
@WebServlet(name="/ServletDetailVendeur", urlPatterns = "/detailVendeur")
public class ServletDetailVendeur extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletDetailVendeur() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> menu = new HashMap<>();
		HttpSession session = request.getSession();
		if(session != null) { //S'il y a une session (donc : un login a été fait)
			Utilisateur user = (Utilisateur)session.getAttribute("user"); //récupération des informations de l'utilisateur connecté
			if(user == null) {
				menu.put("/connexion", "Se connecter");
				menu.put("/inscription", "S'inscrire");
			} else {
				menu.put("/enchere", "Enchères");
				menu.put("/vendre", "Vendre un article");
				menu.put("/profil", "Mon profil");
				menu.put("/deconnexion", "Déconnexion");
			}
		} else {
			menu.put("/connexion", "Se connecter");
			menu.put("/inscription", "S'inscrire");
		}
		
		try {
			
			int id =  Integer.valueOf(request.getParameter("id"));
			Utilisateur utilisateur = UtilisateurManager.getInstance().getById(id);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/DetailVendeur.jsp");
			request.setAttribute("listMenu", menu);
			request.setAttribute("utilisateur", utilisateur);
			request.setAttribute("liensMenu", menu.keySet());
			rd.forward(request, response);
			
		} catch (DalException e) {
			e.printStackTrace();
			response.sendRedirect("/ProjetEnchere/");
			return;
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	
}
