package fr.eni.javaee.projetEE.dal.categories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.javaee.projetEE.BusinessException;
import fr.eni.javaee.projetEE.bo.Category;
import fr.eni.javaee.projetEE.dal.ConnectionProvider;

public class CategoryDAOJdbcImpl implements CategoryDAO {

	private static final String SELECT_ALL = "SELECT * FROM categories";
	private static final String SELECT_BY_ID = "SELECT * FROM categories WHERE no_categorie = ?";
	
	@Override
	public List<Category> selectAll() throws BusinessException {
		List<Category> listeCategories = new ArrayList<Category>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				listeCategories.add(new Category(rs.getInt("no_categorie"), rs.getString("libelle")));
			}
		} catch (SQLException e) {
			throw new BusinessException(BusinessException.DAL_EXCEPTION_CATEGORIES);
		}
		return listeCategories;
	}
	
	@Override
	public Category selectById(int idCategory) throws BusinessException {
		Category category = null;
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_ID);
            pstmt.setInt(1, idCategory);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                category = new Category(rs.getInt("no_categorie"), rs.getString("libelle"));
            }
            cnx.close();
            pstmt.close();
            } catch (SQLException e) {
            	                throw new BusinessException(BusinessException.DAL_EXCEPTION_CATEGORIES);
            }
        return category;
	}
}
