package fr.eni.javaee.projetEE.bll;

import java.time.LocalDateTime;
import java.util.List;

import fr.eni.javaee.projetEE.BusinessException;
import fr.eni.javaee.projetEE.bo.ArticleVendu;
import fr.eni.javaee.projetEE.bo.Utilisateur;
import fr.eni.javaee.projetEE.dal.DAOFactory;
import fr.eni.javaee.projetEE.dal.articleVendu.ArticleVenduDAO;

public class ArticleVenduManager {

	private ArticleVenduDAO articleVenduDAO;

	public ArticleVenduManager() {
		this.articleVenduDAO = DAOFactory.getArticleVenduDAO();
	}

	public void ajouter(ArticleVendu articleVendu) throws BusinessException {
		if (articleVendu == null) {
			throw new BusinessException(BusinessException.BLL_EXCEPTION_AJOUT_ARTICLE);
		}
		this.articleVenduDAO.insert(articleVendu);
	}

	public void modifier(ArticleVendu articleVendu) throws BusinessException {
		if (articleVendu == null) {
			throw new BusinessException(BusinessException.BLL_EXCEPTION_MODIF_ARTICLE);
		}
		this.articleVenduDAO.update(articleVendu);
	}

	public void supprimer(int idArticle) throws BusinessException {
		if (idArticle == 0) {
			throw new BusinessException(BusinessException.BLL_EXCEPTION_SUP_ARTICLE);
		}
		this.articleVenduDAO.delete(idArticle);
	}

	public ArticleVendu selectionner(int idArticle) throws BusinessException {
		if (idArticle == 0) {
			throw new BusinessException(BusinessException.BLL_EXCEPTION_SELECTION_ARTICLE);
		}
		return this.articleVenduDAO.selectById(idArticle);
	}

	public List<ArticleVendu> selectionnerTous() throws BusinessException {
		if (this.articleVenduDAO.selectAll().isEmpty()) {
			throw new BusinessException(BusinessException.BLL_EXCEPTION_SELECTION_ARTICLE);
		}
		List<ArticleVendu> listeAllArticles = this.articleVenduDAO.selectAll();
		return listeAllArticles;
	}

	public List<ArticleVendu> selectionnerParCategorie(int idCategorie) throws BusinessException {
		if (idCategorie == 0) {
			throw new BusinessException(BusinessException.BLL_EXCEPTION_SELECTION_ARTICLE);
		}
		List<ArticleVendu> listeArticlesCategorie = this.articleVenduDAO.selectByCategorie(idCategorie);
		return listeArticlesCategorie;
	}

	public List<ArticleVendu> selectionnerParNom(String nomArticle) throws BusinessException {
		if (nomArticle == null) {
			throw new BusinessException(BusinessException.BLL_EXCEPTION_SELECTION_ARTICLE);
		}
		List<ArticleVendu> listeArticlesNom = this.articleVenduDAO.selectByNom(nomArticle);
		return listeArticlesNom;
	}

	public List<ArticleVendu> selectionnerParUtilisateur(int idUtilisateur) throws BusinessException {
		if (idUtilisateur == 0) {
			throw new BusinessException(BusinessException.BLL_EXCEPTION_SELECTION_ARTICLE);
		}
		List<ArticleVendu> listeArticlesUtilisateur = this.articleVenduDAO.selectByUtilisateur(idUtilisateur);
		return listeArticlesUtilisateur;
	}

	public List<ArticleVendu> selectionnerParFiltreEtCategorie(String filtre, int idCategorie)
			throws BusinessException {
		if (filtre == null || idCategorie == 0) {
			throw new BusinessException(BusinessException.BLL_EXCEPTION_SELECTION_ARTICLE);
		}
		List<ArticleVendu> listeArticlesFiltreCategorie = this.articleVenduDAO.selectByFiltreEtCategorie(filtre,
				idCategorie);
		return listeArticlesFiltreCategorie;
	}

	public List<ArticleVendu> selectionnerParFiltre(String filtre) throws BusinessException {
		if (filtre == null) {
			throw new BusinessException(BusinessException.BLL_EXCEPTION_SELECTION_ARTICLE);
		}
		List<ArticleVendu> listeArticlesFiltre = this.articleVenduDAO.selectByFiltre(filtre);
		return listeArticlesFiltre;
	}

	public void updateVente(int idArticle, int newPrice) throws BusinessException {
		if (idArticle == 0 || newPrice == 0) {
			throw new BusinessException(BusinessException.BLL_EXCEPTION_MODIF_ARTICLE);
		}
		this.articleVenduDAO.updateVente(idArticle, newPrice);
	}

	public void updateCreditVente(int idUtilisateur, int credit) throws BusinessException {
		if (idUtilisateur == 0) {
			throw new BusinessException(BusinessException.BLL_EXCEPTION_CREDIT);
		}
		this.articleVenduDAO.updateCreditVente(idUtilisateur, credit);
	}

	public List<ArticleVendu> selectionnerParEncheresOuvertes() throws BusinessException {
		List<ArticleVendu> listeArticlesEncheresOuvertes = this.articleVenduDAO.selectByDateOpen();
		return listeArticlesEncheresOuvertes;
	}

	public List<ArticleVendu> selectionnerParMesEncheres(int idUtilisateur) throws BusinessException {
		if (idUtilisateur == 0) {
			throw new BusinessException(BusinessException.BLL_EXCEPTION_SELECTION_ARTICLE);
		}
		List<ArticleVendu> listeArticlesMesEncheres = this.articleVenduDAO.selectByUtilisateur(idUtilisateur);
		return listeArticlesMesEncheres;
	}

	public List<ArticleVendu> selectionnerParEncheresRemportees(LocalDateTime now, int idUtilisateur)
			throws BusinessException {
		if (now == null || idUtilisateur == 0) {
			throw new BusinessException(BusinessException.BLL_EXCEPTION_SELECTION_ARTICLE);
		}
		List<ArticleVendu> listeArticlesEncheresRemportees = this.articleVenduDAO.selectByDateUser(now, idUtilisateur);
		return listeArticlesEncheresRemportees;
	}

	public List<ArticleVendu> selectionnerParMesVentes(int idUtilisateur) throws BusinessException {
		if (idUtilisateur == 0) {
			throw new BusinessException(BusinessException.BLL_EXCEPTION_SELECTION_ARTICLE);
		}
		List<ArticleVendu> listeArticlesMesVentes = this.articleVenduDAO.selectByUtilisateur(idUtilisateur);
		return listeArticlesMesVentes;
	}

	public List<ArticleVendu> selectionnerParVentesNonDebutees() throws BusinessException {
		List<ArticleVendu> listeArticlesVentesNonDebutees = this.articleVenduDAO.selectByDateBeforeBegin();
		return listeArticlesVentesNonDebutees;
	}

	public List<ArticleVendu> selectionnerParVentesTerminees() throws BusinessException {
		List<ArticleVendu> listeArticlesVentesTerminees = this.articleVenduDAO.selectByDateClose();
		return listeArticlesVentesTerminees;
	}
	
	public boolean articleDeUtilisateur(int idArticle, int idUtilisateur) throws BusinessException {
		if (idArticle == 0 || idUtilisateur == 0) {
			throw new BusinessException(BusinessException.BLL_EXCEPTION_SELECTION_ARTICLE);
		}
        return this.articleVenduDAO.selectByIdAndUtilisateur(idArticle, idUtilisateur);	}

	public void delete(int idArticle, Utilisateur utilisateur) throws BusinessException {
		if (idArticle == 0) {
			throw new BusinessException(BusinessException.BLL_EXCEPTION_SUP_ARTICLE);
		}
		this.articleVenduDAO.deleteByIdArticle(idArticle, utilisateur);
	}

}