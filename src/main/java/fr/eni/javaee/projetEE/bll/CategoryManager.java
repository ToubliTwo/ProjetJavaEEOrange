package fr.eni.javaee.projetEE.bll;

import java.util.List;

import fr.eni.javaee.projetEE.BusinessException;
import fr.eni.javaee.projetEE.bo.Category;
import fr.eni.javaee.projetEE.dal.DAOFactory;
import fr.eni.javaee.projetEE.dal.categories.CategoryDAO;

public class CategoryManager {
	
	CategoryDAO categoryDAO;
	
	public CategoryManager() {
		this.categoryDAO = DAOFactory.getCategoryDAO();
	}
	
	
	public List<Category> selectAll() throws BusinessException {
	if (categoryDAO == null) {
		throw new BusinessException(BusinessException.BLL_EXCEPTION_CATEGORIES);
	}
		List<Category> listeCategories = categoryDAO.selectAll();
		return listeCategories;
	}
	
	  public Category selectById(int idCategory) throws BusinessException {
		    Category c = this.categoryDAO.selectById(idCategory);
		    if (c.getIdCategory() == 0) {
		      throw new BusinessException(BusinessException.BLL_EXCEPTION_SELECTION_UTILISATEUR);
		    }
		    return c;
		  }

}
