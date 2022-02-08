package fr.eni.projetEnchere.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
		
		LocalDate today = LocalDate.now();
		request.setAttribute("today", today);
				
		
		Map<String, String> menu = new HashMap<>();
		ArticleManager artM;
		if(session != null) { //S'il y a une session (donc : un login a été fait)
			Utilisateur user = (Utilisateur)session.getAttribute("user"); //récupération des informations de l'utilisateur connecté
			if(user == null) {
				menu.put("/connexion", "Se connecter");
				menu.put("/inscription", "S'inscrire");
			} else {
				menu.put("/profil", "Mon profil");
				menu.put("/deconnexion", "Déconnexion");
			}
		} else {
			menu.put("/connexion", "Se connecter");
			menu.put("/inscription", "S'inscrire");
		}
		List<String> listeCat = new ArrayList<String>();
		try {
			artM = ArticleManager.getInstance();
			listeCat = artM.listerCategorie();
		} catch (DalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/AjoutArticle.jsp");
		request.setAttribute("listMenu", menu);
		request.setAttribute("liensMenu", menu.keySet());
		request.setAttribute("listeCat", listeCat);
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
		response.sendRedirect("/ProjetEnchere/enchere");
	}
}
