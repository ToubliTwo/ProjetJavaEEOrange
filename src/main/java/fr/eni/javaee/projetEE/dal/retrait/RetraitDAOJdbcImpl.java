package fr.eni.javaee.projetEE.dal.retrait;

import fr.eni.javaee.projetEE.BusinessException;
import fr.eni.javaee.projetEE.bo.Retrait;
import fr.eni.javaee.projetEE.dal.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RetraitDAOJdbcImpl implements RetraitDAO {

    private static final String SELECT_BY_ARTICLE = "SELECT * FROM RETRAITS WHERE no_article = ?";
    private static final String INSERT = "INSERT INTO RETRAITS (no_article, rue,code_postal ,ville) VALUES (?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE RETRAITS SET rue = ?, code_postal = ?, ville = ? WHERE no_article = ?";

    public RetraitDAOJdbcImpl() {

    }

    @Override
    public Retrait selectByArticle(int idArticle) throws BusinessException {
        Retrait retrait = null;
        try (Connection cnx = ConnectionProvider.getConnection()) {
            try {
                PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_ARTICLE);
                pstmt.setInt(1, idArticle);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    retrait = new Retrait(rs.getInt("no_article"), rs.getString("rue"),
                            rs.getString("code_postal"), rs.getString("ville"));
                }
                cnx.close();
                pstmt.close();
            } catch (SQLException e) {
                throw new BusinessException(BusinessException.DAL_EXCEPTION_SELECT_RETRAIT);
            }
        } catch (SQLException e1) {
            throw new BusinessException(BusinessException.DAL_EXCEPTION_CLASS_NOT_FOUND);
        }
        return retrait;
    }

    @Override
    public void insert(Retrait retrait) throws BusinessException {
        try (Connection cnx = ConnectionProvider.getConnection()) {
            try {
                cnx.setAutoCommit(false);
                PreparedStatement pstmt = cnx.prepareStatement(INSERT);
                pstmt.setInt(1, retrait.getIdArticle());
                pstmt.setString(2, retrait.getRue());
                pstmt.setString(3, retrait.getCodePostal());
                pstmt.setString(4, retrait.getVille());
                pstmt.executeUpdate();
                cnx.commit();
                cnx.close();
                pstmt.close();
            } catch (SQLException e) {
                cnx.rollback();
                throw new BusinessException(BusinessException.DAL_EXCEPTION_INSERT_RETRAIT);
            }
        } catch (SQLException e1) {
            throw new BusinessException(BusinessException.DAL_EXCEPTION_CLASS_NOT_FOUND);
        }
    }
    
    @Override
	public void update(Retrait retrait) throws BusinessException {
    	 try (Connection cnx = ConnectionProvider.getConnection()) {
             try {
                 cnx.setAutoCommit(false);
                 PreparedStatement pstmt = cnx.prepareStatement(UPDATE);
                 pstmt.setInt(1, retrait.getIdArticle());
                 pstmt.setString(2, retrait.getRue());
                 pstmt.setString(3, retrait.getCodePostal());
                 pstmt.setString(4, retrait.getVille());
                 pstmt.executeUpdate();
                 cnx.commit();
                 cnx.close();
                 pstmt.close();
             } catch (SQLException e) {
                 cnx.rollback();
                 throw new BusinessException(BusinessException.DAL_EXCEPTION_INSERT_RETRAIT);
             }
         } catch (SQLException e1) {
             throw new BusinessException(BusinessException.DAL_EXCEPTION_CLASS_NOT_FOUND);
         }
	}

}
