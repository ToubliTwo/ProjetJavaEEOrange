package fr.eni.javaee.projetEE.ihm.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.javaee.projetEE.BusinessException;
import fr.eni.javaee.projetEE.bll.ArticleVenduManager;
import fr.eni.javaee.projetEE.bll.CategoryManager;
import fr.eni.javaee.projetEE.bll.EnchereManager;
import fr.eni.javaee.projetEE.bll.RetraitManager;
import fr.eni.javaee.projetEE.bll.UtilisateurManager;
import fr.eni.javaee.projetEE.bo.ArticleVendu;
import fr.eni.javaee.projetEE.bo.Category;
import fr.eni.javaee.projetEE.bo.Enchere;
import fr.eni.javaee.projetEE.bo.Retrait;
import fr.eni.javaee.projetEE.bo.Utilisateur;

/**
 * Servlet implementation class ServletAfficheEnchere
 */
@WebServlet("/afficheEnchere")
public class ServletAfficheEnchere extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
		List<Category> listeCategories = null;
		CategoryManager categoryManager = new CategoryManager();
		try {
			listeCategories = categoryManager.selectAll();
		} catch (BusinessException e) {
			throw new ServletException(BusinessException.BLL_EXCEPTION_CATEGORIES);
		}
		this.getServletContext().setAttribute("listeCategory", listeCategories);
		super.init();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idArticle = request.getParameter("idArticle");
		String vendeurString = request.getParameter("vendeur");

		if (idArticle != null && !idArticle.isEmpty()) {

			int id = Integer.parseInt(idArticle);
			ArticleVenduManager articleVenduManager = new ArticleVenduManager();
			RetraitManager retraitManager = new RetraitManager();
			EnchereManager enchereManager = new EnchereManager();
			UtilisateurManager utilisateurManager = new UtilisateurManager();

			ArticleVendu articleVendu;
			Retrait retrait;
			Enchere enchere;

			try {
				articleVendu = articleVenduManager.selectionner(id);
				retrait = retraitManager.selectionner(id);
				enchere = enchereManager.selectByArticle(id);
				if (enchere != null) {
					int enchereMontant = enchere.getMontantEnchere();
					int idUser = enchere.getIdUtilisateur();

					Utilisateur u = utilisateurManager.selectById(idUser);
					String userPseudo = u.getPseudo();

					request.setAttribute("enchereMontant", enchereMontant);
					request.setAttribute("userPseudo", userPseudo);
				}

				request.setAttribute("articleVendu", articleVendu);
				request.setAttribute("retrait", retrait);
				request.setAttribute("enchere", enchere);
				request.setAttribute("vendeur", vendeurString);

				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/JSP/detailVente.jsp");
				dispatcher.forward(request, response);
			} catch (BusinessException e) {
				e.printStackTrace();
			}

		} else {
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
			request.setAttribute("error", BusinessException.BLL_EXCEPTION_UTILISATEUR);
			getServletContext().getRequestDispatcher("/WEB-INF/JSP/index.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String curentUserId = request.getParameter("curentUserId");
		String encherPoposition = request.getParameter("encherPoposition");
		String prixPrecedent = request.getParameter("prixVente");
		String idArticle = request.getParameter("idArticle");
//		String articleVendu = request.getParameter("articleVendu");
		String curentUserCredit = request.getParameter("curentUserCredit");

		int curentUser = Integer.parseInt(curentUserId);
		int newPrice = Integer.parseInt(encherPoposition);
		int prixAncien = Integer.parseInt(prixPrecedent);
		int idArticleVendu = Integer.parseInt(idArticle);
		int curentUserScore = Integer.parseInt(curentUserCredit);
		int updateCurentUserScore = curentUserScore - newPrice;

		Date currentDate = new Date();

		ArticleVenduManager articleVenduManager = new ArticleVenduManager();
		UtilisateurManager utilisateurManager = new UtilisateurManager();
		EnchereManager enchereManager = new EnchereManager();

		try {

			Enchere enchere = new Enchere(curentUser, idArticleVendu, currentDate, newPrice);

			Enchere enchereExist = enchereManager.selectByArticle(idArticleVendu);

			if (enchereExist == null) {
				enchereManager.ajouter(enchere);
			} else {
				String idUserPrevious = request.getParameter("idUserPrevious");
				int userPrevious = Integer.parseInt(idUserPrevious);
				Utilisateur u = utilisateurManager.selectById(userPrevious);
				int oldUserScore = u.getCredit();
				enchereManager.update(enchere);

				int updatePrecedentUserScore = oldUserScore + prixAncien;
				utilisateurManager.updateCredit(userPrevious, updatePrecedentUserScore);
			}
			articleVenduManager.updateVente(idArticleVendu, newPrice);

			utilisateurManager.updateCredit(curentUser, updateCurentUserScore);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
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
		RequestDispatcher dispatcher = request.getRequestDispatcher("encheres");
		dispatcher.forward(request, response);

	}
}
