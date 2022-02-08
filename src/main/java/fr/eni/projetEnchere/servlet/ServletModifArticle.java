package fr.eni.projetEnchere.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.projetEnchere.bll.ArticleManager;
import fr.eni.projetEnchere.bll.UtilisateurManager;
import fr.eni.projetEnchere.bo.Article;
import fr.eni.projetEnchere.bo.Utilisateur;
import fr.eni.projetEnchere.dal.DalException;

/**
 * Servlet implementation class ServletModifArticle
 */
@WebServlet(name = "/ServletModifArticle", urlPatterns = "/modifArticle")
public class ServletModifArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> menu = new HashMap<>();
		HttpSession session = request.getSession();
		if (session != null) { // S'il y a une session (donc : un login a été fait)
			Utilisateur user = (Utilisateur) session.getAttribute("user"); // récupération des informations de
																			// l'utilisateur connecté
			if (user == null) {
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

		try {

			int id = Integer.valueOf(request.getParameter("id"));
			Article article = ArticleManager.getInstance().getById(id);
			request.setAttribute("listMenu", menu);
			request.setAttribute("article", article);
			request.setAttribute("liensMenu", menu.keySet());
			request.getRequestDispatcher("/WEB-INF/ModifArticle.jsp").forward(request, response);

		} catch (DalException e) {
			e.printStackTrace();
			response.sendRedirect("/ProjetEnchere/");
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/profil").forward(request, response);

	}

}
