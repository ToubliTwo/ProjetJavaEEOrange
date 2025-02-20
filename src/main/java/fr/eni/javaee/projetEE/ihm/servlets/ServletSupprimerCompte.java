package fr.eni.javaee.projetEE.ihm.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.javaee.projetEE.BusinessException;
import fr.eni.javaee.projetEE.bll.UtilisateurManager;
import fr.eni.javaee.projetEE.bo.Utilisateur;

/**
 * Servlet implementation class Supprimer
 */
@WebServlet("/deleteAccount")
public class ServletSupprimerCompte extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// FIX ME la suppression de compte ne fonctionne pas à 100% : si un utilisateur possède une enchère en cours, il ne peut pas supprimer son compte.
		HttpSession session = request.getSession();
		if (session != null) {
			Utilisateur user = (Utilisateur) session.getAttribute("userConnected");
			if (user != null) {
				String password = request.getParameter("password");
				String userIdStr = request.getParameter("userId");
				if (userIdStr != null && !userIdStr.isEmpty()) {
					System.out.println("user id" + userIdStr);
					int userId = Integer.parseInt(userIdStr);
					System.out.println("user id" + userId);
					if (user.getIdUtilisateur() == userId && user.getMotDePasse().equals(password))
						System.out.println("user id" + user.getIdUtilisateur());
					System.out.println("pass id" + user.getMotDePasse());
					{
						UtilisateurManager utilisateurManager = new UtilisateurManager();
						try {
							System.out.println("delete user" + userId);
							utilisateurManager.delete(userId);
							session.invalidate();
							response.sendRedirect(request.getContextPath() + "/theEnd");
							return;
						} catch (BusinessException e) {
							throw new ServletException(BusinessException.DAL_EXCEPTION_DELETE_UTILISATEUR);
						}
					}
				}
			}
		}
		response.sendRedirect(request.getContextPath() + "/profil");
	}
}
