package fr.eni.projetEnchere.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.projetEnchere.bo.Utilisateur;

/**
 * Servlet implementation class ServletAccueil
 */
public class ServletAccueil extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAccueil() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> menu = new HashMap<>();
		HttpSession session = request.getSession();
		boolean isConnected = true;
		if(session != null) { //S'il y a une session (donc : un login a �t� fait)
			Utilisateur user = (Utilisateur)session.getAttribute("user"); //r�cup�ration des informations de l'utilisateur connect�
			if(user == null) {
				isConnected = false;
				menu.put("/connexion", "Se connecter");
				menu.put("/inscription", "S'inscrire");
			} else {
				menu.put("/vendre", "Vendre un article");
				menu.put("/profil", "Mon profil");
				menu.put("/deconnexion", "D�connexion");
			}
		} else {
			isConnected = false;
			menu.put("/connexion", "Se connecter");
			menu.put("/inscription", "S'inscrire");
		}
//		System.out.println(session.getAttribute("user").toString());
		String[] categories = {"Toutes", "Informatique", "Ameublement", "V�tement", "Sport & loisirs"};
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/accueil.jsp");
		request.setAttribute("listMenu", menu);
		request.setAttribute("liensMenu", menu.keySet());
		request.setAttribute("listeCat", categories);
		rd.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	
}
