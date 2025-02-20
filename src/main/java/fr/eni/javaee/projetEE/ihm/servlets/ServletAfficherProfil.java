package fr.eni.javaee.projetEE.ihm.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.javaee.projetEE.BusinessException;
import fr.eni.javaee.projetEE.bll.UtilisateurManager;
import fr.eni.javaee.projetEE.bo.Utilisateur;

/**
 * Servlet implementation class ServletAfficherProfil
 */
@WebServlet("/profil")
public class ServletAfficherProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String idUtilisateurStr = request.getParameter("idUtilisateur");
		if (idUtilisateurStr != null && !idUtilisateurStr.isEmpty()) {
			int idUtilisateur = Integer.parseInt(idUtilisateurStr);

			UtilisateurManager utilisateurManager = new UtilisateurManager();
			Utilisateur utilisateur;

			try {
				utilisateur = utilisateurManager.selectById(idUtilisateur);

				// Передаем объект на JSP страницу
				request.setAttribute("utilisateur", utilisateur);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/JSP/afficherProfil.jsp");
				dispatcher.forward(request, response);
			} catch (BusinessException e) {
				request.setAttribute("error", BusinessException.BLL_EXCEPTION_UTILISATEUR);
				getServletContext().getRequestDispatcher("/WEB-INF/JSP/index.jsp").forward(request, response);
			}
		} else {
			request.setAttribute("error", BusinessException.BLL_EXCEPTION_UTILISATEUR);
			getServletContext().getRequestDispatcher("/WEB-INF/JSP/index.jsp").forward(request, response);
		}

	}
}
