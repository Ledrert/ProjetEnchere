package fr.eni.projetEnchere.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.projetEnchere.bll.ArticleManager;
import fr.eni.projetEnchere.bll.UtilisateurManager;
import fr.eni.projetEnchere.bo.Categorie;
import fr.eni.projetEnchere.bo.Utilisateur;
import fr.eni.projetEnchere.dal.DalException;

/**
 * Servlet implementation class AjoutArticle
 */
@WebServlet(name="/AjoutArticle", urlPatterns = "/vendre")
public class ServletAjoutArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAjoutArticle() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/AjoutArticle.jsp");
		
		
		LocalDate today = LocalDate.now();
		request.setAttribute("today", today);
		
		rd.forward(request, response);
		
	}
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
		ArticleManager am = ArticleManager.getInstance();
		String article = request.getParameter("article");
		String description = request.getParameter("description");
		String categorie = request.getParameter("categorie");
		Categorie cat=null;
		
			cat = am.rechercherCategorie(categorie);
		
		int prix = Integer.valueOf(request.getParameter("prix"));
		String debut = request.getParameter("debut");
		java.util.Date jdated = new SimpleDateFormat("yyyy-MM-dd").parse(debut);
        Date dateDebut = new Date(jdated.getTime());
		String fin = request.getParameter("fin");
		java.util.Date jdatef = new SimpleDateFormat("yyyy-MM-dd").parse(fin);
        Date dateFin = new Date(jdatef.getTime());
		HttpSession session = request.getSession();
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");

			am.ajouterArticle(article, description, cat, prix, dateDebut, dateFin, utilisateur);
		} catch (DalException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		RequestDispatcher rd = request.getRequestDispatcher("/enchere");
		rd.forward(request, response);
	

	}
}
