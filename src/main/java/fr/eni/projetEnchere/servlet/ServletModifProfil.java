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
import fr.eni.projetEnchere.helpers.HashPassword;

/**
 * Servlet implementation class ServletModifProfil
 */
@WebServlet(name="/ServletModifProfil", urlPatterns="/modifProfil")
public class ServletModifProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
		request.setAttribute("user", utilisateur);
		request.getRequestDispatcher("/WEB-INF/ModifProfil.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Utilisateur user = (Utilisateur) session.getAttribute("user");
		Utilisateur utilisateur = new Utilisateur();
		UtilisateurManager um;
		
		utilisateur.setNoUtilisateur(user.getNoUtilisateur());
		utilisateur.setPseudo(request.getParameter("pseudo"));
		utilisateur.setNom(request.getParameter("nom"));
		utilisateur.setPrenom(request.getParameter("prenom"));
		utilisateur.setEmail(request.getParameter("email"));
		utilisateur.setTelephone(request.getParameter("phone"));
		utilisateur.setRue(request.getParameter("adresse"));
		utilisateur.setCodePostal(request.getParameter("codePostal"));
		utilisateur.setVille(request.getParameter("ville"));
		String hashPassword = request.getParameter("password");
		utilisateur.setPassword(HashPassword.hashpassword(hashPassword));

		try {
			um = UtilisateurManager.getInstance();
			um.ModifierUtilisateur(utilisateur);
			session.setAttribute("user", utilisateur);
		} catch (DalException e) {
			System.out.println("erreur la méthode ModifierUtilisateur()");
			e.printStackTrace();
		}
		request.getRequestDispatcher("/profil").forward(request, response);
	}

}

