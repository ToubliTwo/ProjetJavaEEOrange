package fr.eni.javaee.projetEE.ihm.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.javaee.projetEE.BusinessException;
import fr.eni.javaee.projetEE.bll.ArticleVenduManager;

/**
 * Servlet implementation class ServletDeleteArticle
 */
@WebServlet("/deleteArticle")
public class ServletDeleteArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Récuperer l'id de l'article
		String idArticleStr = request.getParameter("idArticle");
		ArticleVenduManager articleVenduManager = new ArticleVenduManager();
		int idArticle = Integer.parseInt(idArticleStr);
		try {
			articleVenduManager.supprimer(idArticle);
		} catch (BusinessException e) {
			throw new ServletException(BusinessException.DAL_EXCEPTION_DELETE_ARTICLE);
		}
		response.sendRedirect(request.getContextPath() + "/afficheMesArticles");
	}
}
