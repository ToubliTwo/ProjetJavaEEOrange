package fr.eni.javaee.projetEE.bll;

import fr.eni.javaee.projetEE.BusinessException;
import fr.eni.javaee.projetEE.bo.Retrait;
import fr.eni.javaee.projetEE.dal.DAOFactory;
import fr.eni.javaee.projetEE.dal.retrait.RetraitDAO;

public class RetraitManager {
	
	private RetraitDAO retraitDAO;
	
	public RetraitManager() {
		this.retraitDAO = DAOFactory.getRetraitDAO();
	}
	
	public void ajouter(Retrait retrait) throws BusinessException {
		if (retrait == null) {
			throw new BusinessException(BusinessException.BLL_EXCEPTION_AJOUT_RETRAIT);
		}
		this.retraitDAO.insert(retrait);
	}
	
	public Retrait selectionner(int idArticle) throws BusinessException {
		if (idArticle == 0) {
			throw new BusinessException(BusinessException.BLL_EXCEPTION_SELECTION_RETRAIT);
		}
		return this.retraitDAO.selectByArticle(idArticle);
	}
	
	public void modifier(Retrait retrait) throws BusinessException {
		if (retrait == null) {
			throw new BusinessException(BusinessException.BLL_EXCEPTION_MODIFICATION_RETRAIT);
		}
		this.retraitDAO.update(retrait);
	}
}
