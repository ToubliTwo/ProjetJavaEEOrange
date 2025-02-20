package fr.eni.javaee.projetEE.dal.categories;

import java.util.List;

import fr.eni.javaee.projetEE.BusinessException;
import fr.eni.javaee.projetEE.bo.Category;

public interface CategoryDAO {

	public List<Category> selectAll() throws BusinessException;
	public Category selectById(int idCategory) throws BusinessException;
}
