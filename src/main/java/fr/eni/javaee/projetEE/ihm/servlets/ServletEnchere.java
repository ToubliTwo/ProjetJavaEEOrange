package fr.eni.javaee.projetEE.ihm.servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import fr.eni.javaee.projetEE.BusinessException;
import fr.eni.javaee.projetEE.FormValid;
import fr.eni.javaee.projetEE.bll.ArticleVenduManager;
import fr.eni.javaee.projetEE.bll.CategoryManager;
import fr.eni.javaee.projetEE.bll.RetraitManager;
import fr.eni.javaee.projetEE.bo.ArticleVendu;
import fr.eni.javaee.projetEE.bo.Category;
import fr.eni.javaee.projetEE.bo.Retrait;
import fr.eni.javaee.projetEE.bo.Utilisateur;

/**
 * Servlet implementation class ServletEnchere
 */
@WebServlet("/afficheDetailEnchere")
public class ServletEnchere extends HttpServlet {
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
			ArticleVendu articleVendu;
			Retrait retrait;

			try {
				articleVendu = articleVenduManager.selectionner(id);
				retrait = retraitManager.selectionner(id);

				request.setAttribute("articleVendu", articleVendu);
				request.setAttribute("retrait", retrait);
				request.setAttribute("vendeur", vendeurString);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/JSP/detailVente.jsp");
				dispatcher.forward(request, response);
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			request.setAttribute("error", BusinessException.BLL_EXCEPTION_UTILISATEUR);
			getServletContext().getRequestDispatcher("/WEB-INF/JSP/index.jsp").forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nomArticle = request.getParameter("nomArticle");
		String description = request.getParameter("description");
		String dateDebutEncheres = request.getParameter("dateDebutEncheres");
		String dateFinEncheres = request.getParameter("dateFinEncheres");
		String miseAPrix = request.getParameter("miseAPrix");
		String prixVente = miseAPrix;
		String idCategorie = request.getParameter("noCategorie");
		Part filePart = request.getPart("photo2");
		String cheminPhoto = sauvegarderPhoto(filePart);
		String idArticle = request.getParameter("idArticle");

		// Vérifier que les champs obligatoires sont bien remplis
		boolean isParamValid = FormValid.isName(nomArticle) && FormValid.isDescription(description)
				&& FormValid.isInt(miseAPrix);
		if (!isParamValid) {
			request.setAttribute("error", BusinessException.BLL_EXCEPTION_AJOUT_ARTICLE);
			request.setAttribute("nomArticle", nomArticle);
			request.setAttribute("description", description);
			request.setAttribute("dateDebutEncheres", dateDebutEncheres);
			request.setAttribute("dateFinEncheres", dateFinEncheres);
			request.setAttribute("miseAPrix", miseAPrix);
			getServletContext().getRequestDispatcher("/WEB-INF/JSP/article.jsp").forward(request, response);
			return;
		}

		// Vérifier l'image importée
		boolean isImageValid = FormValid.isImage(filePart);
		if (!isImageValid) {
			request.setAttribute("error", BusinessException.BLL_EXCEPTION_AJOUT_IMAGE);
			request.setAttribute("nomArticle", nomArticle);
			request.setAttribute("description", description);
			request.setAttribute("dateDebutEncheres", dateDebutEncheres);
			request.setAttribute("dateFinEncheres", dateFinEncheres);
			request.setAttribute("miseAPrix", miseAPrix);
			getServletContext().getRequestDispatcher("/WEB-INF/JSP/article.jsp").forward(request, response);
			return;
		}

		// Récupérer l'id de l'utilisateur connecté
		HttpSession session = request.getSession();
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("userConnected");
		int idUtilisateur = utilisateur.getIdUtilisateur();

		// Convertir la date en Date
		SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

		Date dateDebut = null;
		Date dateFin = null;

		try {
			dateDebut = dd.parse(dateDebutEncheres);
			dateFin = df.parse(dateFinEncheres);
		} catch (Exception e) {
			request.setAttribute("error", BusinessException.BLL_EXCEPTION_DATE);
			request.setAttribute("nomArticle", nomArticle);
			request.setAttribute("description", description);
			request.setAttribute("dateDebutEncheres", dateDebutEncheres);
			request.setAttribute("dateFinEncheres", dateFinEncheres);
			request.setAttribute("miseAPrix", miseAPrix);
			getServletContext().getRequestDispatcher("/WEB-INF/JSP/article.jsp").forward(request, response);
		}

		// Convertir les informations chiffrées en int
		int Prix = 0;
		int prixDeVente = 0;
		int idCat = 0;

		try {
			Prix = Integer.parseInt(miseAPrix);
			prixDeVente = Integer.parseInt(prixVente);
			idCat = Integer.parseInt(idCategorie);
		} catch (Exception e) {
			request.setAttribute("error", BusinessException.BLL_EXCEPTION_NUMBER);
			request.setAttribute("nomArticle", nomArticle);
			request.setAttribute("description", description);
			request.setAttribute("dateDebutEncheres", dateDebutEncheres);
			request.setAttribute("dateFinEncheres", dateFinEncheres);
			request.setAttribute("miseAPrix", miseAPrix);
			getServletContext().getRequestDispatcher("/WEB-INF/JSP/article.jsp").forward(request, response);
		}
		// Convertir l'idArticle en int
		int idArticleInt = 0;
		try {
			idArticleInt = Integer.parseInt(idArticle);
		} catch (Exception e) {
			request.setAttribute("error", BusinessException.BLL_EXCEPTION_NUMBER);
			request.setAttribute("nomArticle", nomArticle);
			request.setAttribute("description", description);
			request.setAttribute("dateDebutEncheres", dateDebutEncheres);
			request.setAttribute("dateFinEncheres", dateFinEncheres);
			request.setAttribute("miseAPrix", miseAPrix);
			getServletContext().getRequestDispatcher("/WEB-INF/JSP/article.jsp").forward(request, response);
		}

		// Ajouter l'article après avoir vérifié les informations de l'adresse de
		// retrait
		ArticleVendu article = new ArticleVendu(idArticleInt, nomArticle, description, dateDebut, dateFin, Prix,
				prixDeVente, idUtilisateur, idCat, cheminPhoto);
		try {
			ArticleVenduManager articleVenduManager = new ArticleVenduManager();
			articleVenduManager.modifier(article);

			// Ajouter l'adresse de retrait
			String rue = request.getParameter("rue");
			String codePostal = request.getParameter("codePostal");
			String ville = request.getParameter("ville");
			if (rue != null && !rue.isEmpty() && codePostal != null && !codePostal.isEmpty() && ville != null
					&& !ville.isEmpty()) {
				boolean isAdressValid = FormValid.isStreet(rue) && FormValid.isPostalCode(codePostal)
						&& FormValid.isCity(ville);
				if (isAdressValid) {
					Retrait retrait = new Retrait(article.getIdArticle(), rue, codePostal, ville);
					RetraitManager retraitManager = new RetraitManager();
					retraitManager.modifier(retrait);
				} else {
					request.setAttribute("error", BusinessException.BLL_EXCEPTION_AJOUT_RETRAIT);
					request.setAttribute("nomArticle", nomArticle);
					request.setAttribute("description", description);
					request.setAttribute("dateDebutEncheres", dateDebutEncheres);
					request.setAttribute("dateFinEncheres", dateFinEncheres);
					request.setAttribute("miseAPrix", miseAPrix);
					request.setAttribute("rue", rue);
					request.setAttribute("codePostal", codePostal);
					request.setAttribute("ville", ville);
					getServletContext().getRequestDispatcher("/WEB-INF/JSP/article.jsp").forward(request, response);
				}
			} else {
				Retrait retrait = new Retrait(article.getIdArticle(), utilisateur.getRue(), utilisateur.getCodePostal(),
						utilisateur.getVille());
				RetraitManager retraitManager = new RetraitManager();
				retraitManager.modifier(retrait);
			}
			request.setAttribute("success", BusinessException.BLL_EXCEPTION_SUCCESS_AJOUT_ARTICLE);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/article.jsp");
			rd.forward(request, response);
		} catch (BusinessException e) {
			request.setAttribute("error", BusinessException.BLL_EXCEPTION_AJOUT_ARTICLE);
			request.setAttribute("nomArticle", nomArticle);
			request.setAttribute("description", description);
			request.setAttribute("dateDebutEncheres", dateDebutEncheres);
			request.setAttribute("dateFinEncheres", dateFinEncheres);
			request.setAttribute("miseAPrix", miseAPrix);
			getServletContext().getRequestDispatcher("/WEB-INF/JSP/article.jsp").forward(request, response);
		}
	}

	// Générer un nom unique pour la photo et la sauvegarder en local
	private String sauvegarderPhoto(Part filePart) throws IOException {
		if (filePart == null || filePart.getSize() == 0) {
			return "";
		}

		String fileName = filePart.getSubmittedFileName();
		String extension = fileName.substring(fileName.lastIndexOf("."));
		String uniqueFileName = UUID.randomUUID().toString() + extension;
		String cheminSauvegarde = getServletContext().getRealPath("/resources/photos") + File.separator
				+ uniqueFileName;
		try (InputStream inputStream = filePart.getInputStream();
				OutputStream outputStream = new FileOutputStream(cheminSauvegarde)) {
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
		}
		return uniqueFileName;
	}

}
