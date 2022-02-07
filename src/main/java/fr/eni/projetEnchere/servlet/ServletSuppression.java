package fr.eni.projetEnchere.servlet;

import java.io.IOException;
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
 * Servlet implementation class ServletSuppression
 */
@WebServlet(name="/ServletSuppression", urlPatterns="/SuppressionProfil")
public class ServletSuppression extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
		request.setAttribute("user", utilisateur);
		request.getRequestDispatcher("/WEB-INF/Suppression.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Utilisateur user = (Utilisateur) session.getAttribute("user");
		UtilisateurManager um = UtilisateurManager.getInstance();
		Utilisateur utilisateur = new Utilisateur();
		
		utilisateur.getNoUtilisateur();
		
		try {
			um.supprimerUtilisateur(utilisateur);
			session.invalidate();
		} catch (DalException e) {
			System.out.println("erreur la méthode SupprimerUtilisateur()");
			e.printStackTrace();
		}
		request.getRequestDispatcher("/enchere").forward(request, response);		
	}


}
