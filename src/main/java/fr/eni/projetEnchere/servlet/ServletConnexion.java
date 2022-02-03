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

import fr.eni.projetEnchere.bll.UtilisateurManager;
import fr.eni.projetEnchere.bo.Utilisateur;
import fr.eni.projetEnchere.dal.DalException;
import fr.eni.projetEnchere.helpers.HashPassword;

/**
 * Servlet implementation class ServletConnexion
 */
@WebServlet(name="/ServletConnexion", urlPatterns = "/connexion")
public class ServletConnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletConnexion() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Connexion.jsp");
		Map<String, String> menu = new HashMap<>();
		menu.put("/connexion", "Se connecter");
		menu.put("/inscription", "S'inscrire");
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
		UtilisateurManager um = UtilisateurManager.getInstance();
		Utilisateur utilisateur = new Utilisateur();
		if (identifiant.contains("@")) {
			try {
				identifiant = um.chercherPseudo(identifiant);
				utilisateur.setPassword(HashPassword.hashpassword(password));
				
			} catch (DalException e) {
					e.printStackTrace();
			}
		} else { 			
			
		} 
	}
}

	