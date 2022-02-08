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
import fr.eni.projetEnchere.bo.Article;
import fr.eni.projetEnchere.bo.Utilisateur;
import fr.eni.projetEnchere.dal.ArticleDAO;
import fr.eni.projetEnchere.dal.ArticleDaoImpl;
import fr.eni.projetEnchere.dal.DalException;

/**
 * Servlet implementation class ServletDetailEnchere
 */
@WebServlet(name="/ServletDetailEnchere", urlPatterns = "/detailEnchere")
public class ServletDetailEnchere extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletDetailEnchere() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> menu = new HashMap<>();
		HttpSession session = request.getSession();
		if(session != null) { //S'il y a une session (donc : un login a �t� fait)
			Utilisateur user = (Utilisateur)session.getAttribute("user"); //r�cup�ration des informations de l'utilisateur connect�
			if(user == null) {
				menu.put("/connexion", "Se connecter");
				menu.put("/inscription", "S'inscrire");
			} else {
				menu.put("/enchere", "Ench�res");
				menu.put("/vendre", "Vendre un article");
				menu.put("/profil", "Mon profil");
				menu.put("/deconnexion", "D�connexion");
			}
		} else {
			menu.put("/connexion", "Se connecter");
			menu.put("/inscription", "S'inscrire");
		}
		
		try {
			
			int id =  Integer.valueOf(request.getParameter("id"));
			Article article = ArticleManager.getInstance().getById(id);
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/DetailEnchere.jsp");
			request.setAttribute("listMenu", menu);
			request.setAttribute("article", article);
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
		doGet(request, response);
	}

}
