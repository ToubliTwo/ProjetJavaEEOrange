package fr.eni.javaee.projetEE.ihm.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.javaee.projetEE.BusinessException;
import fr.eni.javaee.projetEE.bll.ArticleVenduManager;
import fr.eni.javaee.projetEE.bll.CategoryManager;
import fr.eni.javaee.projetEE.bo.ArticleVendu;
import fr.eni.javaee.projetEE.bo.Category;
import fr.eni.javaee.projetEE.bo.Utilisateur;

/**
 * Servlet implementation class ServletAfficheMesArticles
 */
@WebServlet("/afficheMesArticles")
public class ServletAfficheMesArticles extends HttpServlet {
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
		Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("userConnected");
		int idUtilisateur = utilisateur.getIdUtilisateur();
		ArticleVenduManager articleVenduManager = new ArticleVenduManager();
		List<ArticleVendu> listeArticles = null;
		try {
			listeArticles = articleVenduManager.selectionnerParUtilisateur(idUtilisateur);
		} catch (BusinessException e) {
			throw new ServletException(BusinessException.DAL_EXCEPTION_SELECT_ARTICLE_BY_UTILISATEUR);
		}
		request.setAttribute("listeArticles", listeArticles);
		request.getRequestDispatcher("/WEB-INF/JSP/mesVentes.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Récuperer l'id de l'article
		String idArticleStr = request.getParameter("idArticle");
		ArticleVenduManager articleVenduManager = new ArticleVenduManager();
		// Récuperer l'idCatégorie de l'article
		ArticleVendu article = null;
		try {
			article = articleVenduManager.selectionner(Integer.parseInt(idArticleStr));
		} catch (BusinessException e) {
			throw new ServletException(BusinessException.DAL_EXCEPTION_SELECT_ARTICLE_BY_ID);
		}
		int idCategory = article.getIdCategory();
		// Récupérer la date de début d'enchère
		Date dateDebutEncheres = article.getDateDebutEncheres();
		Date dateActuelle = new Date();
		boolean dateBoutonSupprimer = dateDebutEncheres.after(dateActuelle);
		request.setAttribute("dateBoutonSupprimer", dateBoutonSupprimer);
		request.setAttribute("idCategory", idCategory);
		request.setAttribute("article", article);
		request.getRequestDispatcher("/WEB-INF/JSP/modifyArticle.jsp").forward(request, response);
	}

}
