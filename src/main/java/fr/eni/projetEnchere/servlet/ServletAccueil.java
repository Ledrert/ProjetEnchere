package fr.eni.projetEnchere.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.projetEnchere.bll.ArticleManager;
import fr.eni.projetEnchere.bo.Article;
import fr.eni.projetEnchere.bo.Utilisateur;
import fr.eni.projetEnchere.dal.DalException;

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
		ArticleManager artM;
		List<Article> listeArt = new ArrayList<Article>();
		try {
			artM = ArticleManager.getInstance();
			listeArt = artM.listerArticle();
		} catch (DalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		doRedirect(request, response, listeArt);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Article> articles = new ArrayList<Article>();
		List<Article> listArtFiltre = null;
		ArticleManager artM;
		
		try {
			artM = ArticleManager.getInstance();
			articles = artM.listerArticle();
			listArtFiltre = filtrerListe(articles, request.getParameter("catSelect"), request.getParameter("keyword").trim().toLowerCase());
			
		} catch (DalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		doRedirect(request, response, listArtFiltre);
		
	}
	
	private List<Article> filtrerListe(List<Article> liste , String catSelect, String keywords){
		List<Article> listArtFiltre = new ArrayList<Article>();
		for(Article art : liste) {
			
		if(art.getNomArticle().trim().toLowerCase().contains(keywords)) {
			if(!(catSelect.equals("Toutes"))) { //categorie sélectionnée autre que "Toutes"
				if(art.getCategorie().getLibelle().equals(catSelect)) {
						listArtFiltre.add(art);
					}
				} else {
					listArtFiltre.add(art);
				}
			}
		}
		
		return listArtFiltre;
	}
	
	private void doRedirect(HttpServletRequest request, HttpServletResponse response, List<Article> liste) throws ServletException, IOException {
		Map<String, String> menu = new HashMap<>();
		HttpSession session = request.getSession();
		ArticleManager artM;
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
		List<String> listeCat = new ArrayList<String>();
		try {
			artM = ArticleManager.getInstance();
			listeCat = artM.listerCategorie();
		} catch (DalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/accueil.jsp");
		request.setAttribute("listMenu", menu);
		request.setAttribute("liensMenu", menu.keySet());
		request.setAttribute("listeCat", listeCat);
		request.setAttribute("listeArt", liste);
		rd.forward(request, response);
	}

}
