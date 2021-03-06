package fr.eni.projetEnchere.servlet;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import fr.eni.projetEnchere.bll.UtilisateurManager;
import fr.eni.projetEnchere.bo.Utilisateur;
import fr.eni.projetEnchere.dal.DalException;
import fr.eni.projetEnchere.helpers.HashPassword;
/**
 * Servlet implementation class ServletModifProfil
 */
@WebServlet(name="/ServletInscription", urlPatterns="/inscription")
public class ServletInscription extends HttpServlet {
    private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Inscription.jsp");
        rd.forward(request, response);
    }
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pseudo = request.getParameter("pseudo");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String tel = request.getParameter("phone");
        String rue = request.getParameter("adresse");
        String codePostal = request.getParameter("codePostal");
        String ville = request.getParameter("ville");
        String password = request.getParameter("password");
        String verif = request.getParameter("confirmPassword");
        //test tel pattern
        if(tel.contains("[0-9]+") && tel.length() == 10) {
            //on valide
        }
        Utilisateur utilisateur = null;
        UtilisateurManager um;
        try {
            um = UtilisateurManager.getInstance();
            if (um.verifPseudoExist(pseudo)) {
                response.sendRedirect("inscription?error=1");
                return;
            }
            if (um.verifEmailExist(email)) {
                response.sendRedirect("inscription?error=1");
                return;
            }
            if(um.verifPassword(password, verif)) {
                response.sendRedirect("inscription?error=2");
                return;
            }
            password = HashPassword.hashpassword(password);
            um.ajouterUtilisateur(pseudo, nom, prenom, email, tel, rue, codePostal, ville, password);
        } catch (DalException e) {
            e.printStackTrace();
        }
        response.sendRedirect("/ProjetEnchere/enchere");
    }
}