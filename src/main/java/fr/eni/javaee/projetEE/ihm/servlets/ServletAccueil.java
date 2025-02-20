package fr.eni.javaee.projetEE.ihm.servlets;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.javaee.projetEE.BusinessException;
import fr.eni.javaee.projetEE.FormValid;
import fr.eni.javaee.projetEE.bll.ArticleVenduManager;
import fr.eni.javaee.projetEE.bll.CategoryManager;
import fr.eni.javaee.projetEE.bll.UtilisateurManager;
import fr.eni.javaee.projetEE.bo.ArticleVendu;
import fr.eni.javaee.projetEE.bo.Category;
import fr.eni.javaee.projetEE.bo.Utilisateur;

/**
 * Servlet implementation class ServletAccueil
 */
@WebServlet("/encheres")
public class ServletAccueil extends HttpServlet {
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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getSession().getAttribute("userConnected") != null) {
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
		}

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/index.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Récupérer les filtres de recherche pour utilisateur non connecté
		String categorie = request.getParameter("noCategorie");
		String filtre = request.getParameter("filtre");
		// Vérification de la validité du filtre si non null
		if (filtre != null) {
			boolean isFiltreValid = FormValid.isFiltre(filtre);
			if (!isFiltreValid) {
				request.setAttribute("error", BusinessException.BLL_EXCEPTION_FILTER);

			}
		}

		// Récupérer les filtres de recherche pour utilisateur connecté
		Utilisateur userConnected = (Utilisateur) request.getSession().getAttribute("userConnected");
		String achatEnCours = request.getParameter("enchereOuvertes");
		String AchatArticle = request.getParameter("mesEncheres");
		String AchatRemporte = request.getParameter("encheresRemportees");

		String VenteEnCours = request.getParameter("mesVentes");
		String VenteNonDebute = request.getParameter("ventesNonDebutees");
		String VenteTermine = request.getParameter("ventesTerminees");

		List<ArticleVendu> resultatsRecherche = new ArrayList<>();
		List<ArticleVendu> resultatsFiltre = new ArrayList<>();
		List<ArticleVendu> resultatsCheckbox = new ArrayList<>();
		ArticleVenduManager ArticleVenduManager = new ArticleVenduManager();

		try {
			// Récupération des résultats des filtres de recherche
			resultatsFiltre = Stream.of(
					(categorie == null || categorie.isEmpty()) && (filtre == null || filtre.isEmpty())
							? ArticleVenduManager.selectionnerTous()
							: null,
					(categorie == null || categorie.isEmpty()) && (filtre != null && !filtre.isEmpty())
							? ArticleVenduManager.selectionnerParFiltre(filtre)
							: null,
					(filtre == null || filtre.isEmpty()) && (categorie != null && !categorie.isEmpty())
							? ArticleVenduManager.selectionnerParCategorie(Integer.parseInt(categorie))
							: null,
					(filtre != null && !filtre.isEmpty()) && (categorie != null && !categorie.isEmpty())
							? ArticleVenduManager.selectionnerParFiltreEtCategorie(filtre, Integer.parseInt(categorie))
							: null)
					.filter(Objects::nonNull).flatMap(List::stream).collect(Collectors.toList());

		} catch (BusinessException e) {
			// Gérer les exceptions BusinessException
			String errorMessage;
			if (e.getMessage().equals(BusinessException.BLL_EXCEPTION_SELECTION_ARTICLE)) {
				errorMessage = BusinessException.BLL_EXCEPTION_SELECTION_ARTICLE;
			} else if (e.getMessage().equals(BusinessException.BLL_EXCEPTION_SELECTION_ARTICLE_FILTER)) {
				errorMessage = BusinessException.BLL_EXCEPTION_SELECTION_ARTICLE_FILTER;
			} else {
				errorMessage = BusinessException.BLL_EXCEPTION_SELECTION_ARTICLE_CATEGORY;
			}
			request.setAttribute("error", errorMessage);
			getServletContext().getRequestDispatcher("/WEB-INF/JSP/index.jsp").forward(request, response);
		}

		try {
			// Récupération des résultats des checkboxes de recherche
			Set<ArticleVendu> resultSet = Stream
					.of((achatEnCours != null) ? ArticleVenduManager.selectionnerParEncheresOuvertes() : null,
							(AchatArticle != null)
									? ArticleVenduManager.selectionnerParMesEncheres(userConnected.getIdUtilisateur())
									: null,
							(AchatRemporte != null) ? ArticleVenduManager.selectionnerParEncheresRemportees(
									LocalDateTime.now(), userConnected.getIdUtilisateur()) : null,
							(VenteEnCours != null)
									? ArticleVenduManager.selectionnerParMesVentes(userConnected.getIdUtilisateur())
									: null,
							(VenteNonDebute != null) ? ArticleVenduManager.selectionnerParVentesNonDebutees() : null,
							(VenteTermine != null) ? ArticleVenduManager.selectionnerParVentesTerminees() : null)
					.filter(Objects::nonNull).flatMap(List::stream).collect(Collectors.toSet()); // Utilisation de
																									// Collectors.toSet()
																									// pour obtenir un
																									// ensemble sans
																									// doublons

			resultatsCheckbox = new ArrayList<>(resultSet);
		} catch (BusinessException e) {
			throw new ServletException(BusinessException.BLL_EXCEPTION_SELECTION_ARTICLE);
		}

		if (resultatsCheckbox.isEmpty()) {
			// Si aucune checkbox n'est cochée, utilisez les résultats de resultatsFiltre
			resultatsRecherche = new ArrayList<>(resultatsFiltre);
		} else {
			// Sinon, filtrez les résultats de resultatsCheckbox pour ne conserver que ceux
			// présents dans resultatsFiltre
			resultatsRecherche = resultatsCheckbox.stream().filter(resultatsFiltre::contains)
					.collect(Collectors.toList());
		}

		// Récupérer le pseudo de l'utilisateur à qui appartient l'article
		UtilisateurManager utilisateurManager = new UtilisateurManager();
		for (ArticleVendu article : resultatsRecherche) {
			try {
				Utilisateur utilisateur = utilisateurManager.selectById(article.getIdUtilisateur());
				if (utilisateur != null) {
					article.setUtilisateur(utilisateur);
				}
			} catch (BusinessException e) {
				request.setAttribute("error", BusinessException.BLL_EXCEPTION_SELECTION_UTILISATEUR);
				getServletContext().getRequestDispatcher("/WEB-INF/JSP/index.jsp").forward(request, response);
				return;
			}
		}

		request.setAttribute("resultatsRecherche", resultatsRecherche);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/JSP/index.jsp");
		dispatcher.forward(request, response);
	}

}
