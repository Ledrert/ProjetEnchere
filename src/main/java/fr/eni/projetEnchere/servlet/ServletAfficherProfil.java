package fr.eni.projetEnchere.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletAfficherProfil
 */
@WebServlet(name="/ServletAfficherProfil", urlPatterns = "/profil")
public class ServletAfficherProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ServletAfficherProfil() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	IOException {
		Map<String, String> menu = new HashMap<>();
		menu.put("inscription", "Se connecter - S'inscrire");
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/AfficherProfil.jsp");
		request.setAttribute("listMenu", menu);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
