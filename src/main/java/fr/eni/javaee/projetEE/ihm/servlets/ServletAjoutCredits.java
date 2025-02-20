package fr.eni.javaee.projetEE.ihm.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.javaee.projetEE.BusinessException;
import fr.eni.javaee.projetEE.bll.UtilisateurManager;
import fr.eni.javaee.projetEE.bo.Utilisateur;

/**
 * Servlet implementation class ServletAjoutCredits
 */
@WebServlet("/ajoutCredits")
public class ServletAjoutCredits extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("userConnected");
		UtilisateurManager utilisateurM = new UtilisateurManager();
		int id = utilisateur.getIdUtilisateur();
		System.out.println("id :" + id);
		int montant = 0;
		try {
			montant = utilisateurM.getCredit(id);
			System.out.println("montant :" + montant);
			request.getSession().setAttribute("credit", montant);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.getRequestDispatcher("/WEB-INF/JSP/ajoutCredits.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Récupération du montant à ajouter
		String montantStr = request.getParameter("montant");
		int montant = Integer.parseInt(montantStr);
		// Récupération du montant déjà crédité de l'utilisateur pour garder le credit
		// actuel afin d'y ajouter un montant
		UtilisateurManager utilisateurM = new UtilisateurManager();
		Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("userConnected");
		int id = utilisateur.getIdUtilisateur();
		int montantExistant = 0;
		try {
			montantExistant = utilisateurM.getCredit(id);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ajout du montant pour atteindre le nouveau montant et le set dans la bdd
		montant += montantExistant;
		try {
			utilisateurM.ajoutCredit(id, montant);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// redirection vers la JSP pour rester sur la page d'ajout de crédit après en
		// avoir ajouté
		// request.getRequestDispatcher("/WEB-INF/JSP/ajoutCredits.jsp").forward(request,
		// response);
		doGet(request, response);
	}
}
