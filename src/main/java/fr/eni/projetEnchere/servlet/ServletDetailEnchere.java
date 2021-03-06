package fr.eni.projetEnchere.servlet;
import java.io.IOException;
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
import fr.eni.projetEnchere.bll.UtilisateurManager;
import fr.eni.projetEnchere.bo.Article;
import fr.eni.projetEnchere.bo.Enchere;
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
		
		if(session != null) { //S'il y a une session (donc : un login a ?t? fait)
			Utilisateur user = (Utilisateur)session.getAttribute("user"); //r?cup?ration des informations de l'utilisateur connect?
			if(user == null) {
				menu.put("/connexion", "Se connecter");
				menu.put("/inscription", "S'inscrire");
			} else {
				menu.put("/vendre", "Vendre un article");
				menu.put("/profil", "Mon profil");
				menu.put("/deconnexion", "D?connexion");
			}
		} else {
			menu.put("/connexion", "Se connecter");
			menu.put("/inscription", "S'inscrire");
		}
		
		try {
			ArticleManager am = ArticleManager.getInstance();
			int id, error;
            if(request.getParameter("id") == null) {
                error = 1;
                request.setAttribute("error", error);
                id = (int)session.getAttribute("article");
            } else {
            id =  Integer.valueOf(request.getParameter("id"));
            }
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

		//recup?rer session
		HttpSession session = request.getSession();
		//r?cup?rer utilisateur inscrit dans la session -> r?cup?rer id : utiliser.getNoUtilisateur
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");

		int oldEnchere = Integer.valueOf(request.getParameter("oldEnc"));
		int noOldU = Integer.valueOf(request.getParameter("oldEncU"));
		
		Enchere enc = new Enchere();
		enc.setNoEncherisseur(utilisateur);
		enc.setDateEnchere(dateEnchereSQL);		
		int montantEnchere = Integer.parseInt(request.getParameter("prix"));
		enc.setMontantEnchere(montantEnchere);
		int noArt = Integer.valueOf(request.getParameter("noArt"));
		int ancienneEnchere = Integer.parseInt(request.getParameter("mtnArt"));
        if (montantEnchere < ancienneEnchere) {
            session.setAttribute("article", noArt);
            response.sendRedirect("detailEnchere?error=1");
        } else { if (utilisateur.getCredit() < montantEnchere) {
        	session.setAttribute("article", noArt);
            response.sendRedirect("detailEnchere?error=2");
	        } else {
		
				try {
					ArticleManager am = ArticleManager.getInstance();
					UtilisateurManager um = UtilisateurManager.getInstance();
					Utilisateur old = um.getById(noOldU);
					enc.setArticleVendu(am.getById(noArt));
					am.ajouterEnchere(enc);
					um.paiementEnchere(old, utilisateur, oldEnchere, montantEnchere);
					
				} catch (DalException e) {
					e.printStackTrace();
					
				}
				response.sendRedirect("/ProjetEnchere/");
	        }
        }
	}
}