package fr.eni.javaee.projetEE.bll;

import fr.eni.javaee.projetEE.BusinessException;
import fr.eni.javaee.projetEE.bo.Enchere;
import fr.eni.javaee.projetEE.dal.DAOFactory;
import fr.eni.javaee.projetEE.dal.enchere.EnchereDAO;

public class EnchereManager {

	private EnchereDAO enchereDAO;

	public EnchereManager() {
		enchereDAO = DAOFactory.getEnchereDAO();
	}

	public void ajouter(Enchere enchere) throws BusinessException {
		this.enchereDAO.insert(enchere);
	}

	public Enchere selectByUtilisateur(int idUtilisateur) throws BusinessException {
		Enchere e = new Enchere();
		this.enchereDAO.selectByUtilisateur(idUtilisateur);
		return e;
	}

	public Enchere selectByArticle(int idArticle) throws BusinessException {
		Enchere e = new Enchere();
		e = this.enchereDAO.selectByArticle(idArticle);
		return e;
	}

	@SuppressWarnings("unused") // a enlever une fois utilisé
	public Enchere selectByMontant(int montantEnchere) throws BusinessException {
		Enchere e = new Enchere();
		if (e == null) {
			throw new BusinessException(BusinessException.BLL_EXCEPTION_ENCHERE);
		}
		this.enchereDAO.selectByMontant(montantEnchere);
		return e;
	}

	public void update(Enchere enchere) throws BusinessException {
		this.enchereDAO.update(enchere);
	}

	public void delete(int idArticle) throws BusinessException {
		if (idArticle == 0) {
			throw new BusinessException(BusinessException.BLL_EXCEPTION_SUP_ENCHERE);
		}
		this.enchereDAO.delete(idArticle);
	}
}
