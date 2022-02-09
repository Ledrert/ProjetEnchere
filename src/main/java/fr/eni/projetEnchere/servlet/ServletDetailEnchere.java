package fr.eni.projetEnchere.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
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
import fr.eni.projetEnchere.bo.Enchere;
import fr.eni.projetEnchere.bo.Retrait;
import fr.eni.projetEnchere.bo.Utilisateur;
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
			Date today = java.sql.Date.valueOf(LocalDate.now());
			request.setAttribute("today", today);
		
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
			ArticleManager am = ArticleManager.getInstance();
			int id =  Integer.valueOf(request.getParameter("id"));
			Article article = am.getById(id);
			Enchere enc = am.getDernierEnchere(article);
			if(enc == null) {
				enc = new Enchere();
				enc.setMontantEnchere(article.getPrixInitial());
				enc.setNoEncherisseur(article.getUserVendeur());
			}
			request.setAttribute("enchere", enc);
			request.setAttribute("listMenu", menu);
			request.setAttribute("article", article);
			request.setAttribute("liensMenu", menu.keySet());
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/DetailEnchere.jsp");
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
		Timestamp dateEnchereSQL = new Timestamp(Calendar.getInstance().getTime().getTime());

		//recupérer session
		HttpSession session = request.getSession();
		//récupérer utilisateur inscrit dans la session -> récupérer id : utiliser.getNoUtilisateur
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");

		Enchere enc = new Enchere();
		enc.setNoEncherisseur(utilisateur);
		enc.setDateEnchere(dateEnchereSQL);		
		int montantEnchere = Integer.parseInt(request.getParameter("prix"));
		enc.setMontantEnchere(montantEnchere);
		int noArt = Integer.valueOf(request.getParameter("noArt"));
		

		try {
			ArticleManager am = ArticleManager.getInstance();
			enc.setArticleVendu(am.getById(noArt));
			am.ajouterEnchere(enc);
						
			
		} catch (DalException e) {
			e.printStackTrace();
			
		}
		response.sendRedirect("/ProjetEnchere/");
		
		
	}

}
