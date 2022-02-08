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
import fr.eni.projetEnchere.dal.DAOFactory;
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
		String radioF, enchDebut, enchEC, enchEnd, venteEC, venteNC, venteFin;
		if(request.getParameter("radioFiltre") == null) {
			radioF = "no";
		} else {
			radioF = request.getParameter("radioFiltre");
		}
		if(request.getParameter("EncDebut") == null) {
			enchDebut = "no";
		} else {
			enchDebut = request.getParameter("EncDebut");
		}
		if(request.getParameter("MyEncEC") == null) {
			enchEC = "no";
		} else {
			enchEC = request.getParameter("MyEncEC");
		}
		if(request.getParameter("MyEncWin") == null) {
			enchEnd = "no";
		} else {
			enchEnd = request.getParameter("MyEncWin");
		}
		if(request.getParameter("MyVenteEC") == null) {
			venteEC = "no";
		} else {
			venteEC = request.getParameter("MyVenteEC");
		}
		if(request.getParameter("MyVenteNC") == null) {
			venteNC = "no";
		} else {
			venteNC = request.getParameter("MyVenteNC");
		}
		if(request.getParameter("MyVenteFin") == null) {
			venteFin = "no";
		} else {
			venteFin = request.getParameter("MyVenteFin");
		}
		
		try {
			artM = ArticleManager.getInstance();
			if(radioF.equals("no")) {
				listArtFiltre = artM.listerArticle();
			} else { 
				HttpSession session = request.getSession();
				Utilisateur user = (Utilisateur)session.getAttribute("user");
				if(radioF.equals("vente")) {
					listArtFiltre = filtrerVente(venteEC, venteNC, venteFin, user);
				} else {
					listArtFiltre = filtrerAchat(enchDebut, enchEC, enchEnd, user);
			} }
			listArtFiltre = filtrerListe(listArtFiltre, request.getParameter("catSelect"), request.getParameter("keyword").trim().toLowerCase());
			
		} catch (DalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		doRedirect(request, response, listArtFiltre);
		
	}
	
	private List<Article> filtrerListe(List<Article> liste ,String catSelect, String keywords){
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
	
	private List<Article> filtrerAchat(String begin, String ec, String win, Utilisateur user){
		ArticleManager am;
		List<Article> listArtFiltre = new ArrayList<Article>();
		try {
			am = ArticleManager.getInstance();
				if(begin.equals("on")) {
					listArtFiltre = am.encheresEnCours(listArtFiltre, user);
				}
				if(ec.equals("on")) {
					//A une enchère sur cette article dont la date d'enchère n'est pas terminé
					listArtFiltre = am.mesEncheresEnCours(listArtFiltre, user);
				}
				if(win.equals("on")) {
					//est l'acheteur de l'article
					listArtFiltre = am.mesEncheresGagnes(listArtFiltre, user);
				}
				if(begin.equals("no") & ec.equals("no") & win.equals("no")) {
					listArtFiltre = am.mesAchats(listArtFiltre, user);
				}
		} catch (DalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listArtFiltre;
	}
	
	private List<Article> filtrerVente(String ec, String prep, String end, Utilisateur user){
		ArticleManager am;
		List<Article> listArtFiltre = new ArrayList<Article>();
		try {
			am = ArticleManager.getInstance();
			if(ec.equals("on")) {
				//les articles de l'utilisateur qui ont débuté mais qui ne sont pas terminés
				listArtFiltre = am.mesVentesEnCours(listArtFiltre, user);
			}
			if(prep.equals("on")) {
				//les articles de l'utilisateur qui n'ont pas commencés
				listArtFiltre = am.mesVentesNonDebutees(listArtFiltre, user);
			}
			if(end.equals("on")) {
				//les articles de l'utilisateur dont l'enchère a terminé
				listArtFiltre = am.mesVentesTerminees(listArtFiltre, user);
			}
			if(prep.equals("no") & ec.equals("no") & end.equals("no")) {
				listArtFiltre = am.mesVentes(listArtFiltre, user);
			}
		} catch (DalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
