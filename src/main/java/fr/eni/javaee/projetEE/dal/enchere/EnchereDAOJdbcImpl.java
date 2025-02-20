package fr.eni.javaee.projetEE.dal.enchere;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import fr.eni.javaee.projetEE.BusinessException;
import fr.eni.javaee.projetEE.bo.Enchere;
import fr.eni.javaee.projetEE.dal.ConnectionProvider;

public class EnchereDAOJdbcImpl implements EnchereDAO {

	private static final String SELECT_ALL = "SELECT * FROM ENCHERES";
	private static final String SELECT_BY_UTILISATEURS = "SELECT * FROM ENCHERES WHERE no_utilisateur = ?";
	private static final String SELECT_BY_ARTICLES = "SELECT * FROM ENCHERES WHERE no_article = ?";
	private static final String SELECT_BY_MONTANT = "SELECT * FROM ENCHERES WHERE montant_enchere = ?";
	private static final String INSERT = "INSERT INTO ENCHERES (date_enchere, montant_enchere, no_utilisateur, no_article) VALUES (?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE ENCHERES SET date_enchere = ?, montant_enchere = ?, no_utilisateur = ? WHERE no_article = ?";
	private static final String DELETE = "DELETE FROM ENCHERES WHERE no_enchere = ?";

	// Constructeur à visibilité package pour que seule la factory puisse créer une
	// instance
	public EnchereDAOJdbcImpl() {
	}

	@Override
	public List<Enchere> selectAll() throws BusinessException {
		List<Enchere> encheres = new ArrayList<>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			try {
				PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					encheres.add(new Enchere(rs.getInt("no_utilisateur"), rs.getInt("no_article"),
							rs.getDate("date_enchere"), rs.getInt("montant_enchere")));
				}
				cnx.close();
				pstmt.close();
			} catch (SQLException e) {
				throw new BusinessException(BusinessException.DAL_EXCEPTION_SELECT_ENCHERE);
			}
		} catch (SQLException e1) {
			throw new BusinessException(BusinessException.DAL_EXCEPTION_CLASS_NOT_FOUND);
		}
		return encheres;
	}

	@Override
	public Enchere selectByUtilisateur(int idUtilisateur) throws BusinessException {
		Enchere enchere = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			try {
				PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_UTILISATEURS);
				pstmt.setInt(1, idUtilisateur);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					enchere = new Enchere(rs.getInt("no_utilisateur"), rs.getInt("no_article"),
							rs.getDate("date_enchere"), rs.getInt("montant_enchere"));
				}
				cnx.close();
				pstmt.close();
			} catch (SQLException e) {
				throw new BusinessException(BusinessException.DAL_EXCEPTION_SELECT_ENCHERE);
			}
		} catch (SQLException e1) {
			throw new BusinessException(BusinessException.DAL_EXCEPTION_CLASS_NOT_FOUND);
		}
		return enchere;
	}

	@Override
	public Enchere selectByArticle(int idArticle) throws BusinessException {
		Enchere enchere = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			try {
				PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_ARTICLES);
				pstmt.setInt(1, idArticle);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					enchere = new Enchere(rs.getInt("no_utilisateur"), rs.getInt("no_article"),
							rs.getDate("date_enchere"), rs.getInt("montant_enchere"));
				}
				cnx.close();
				pstmt.close();
			} catch (SQLException e) {
				throw new BusinessException(BusinessException.DAL_EXCEPTION_SELECT_ENCHERE);
			}
		} catch (SQLException e1) {
			throw new BusinessException(BusinessException.DAL_EXCEPTION_CLASS_NOT_FOUND);
		}
		return enchere;
	}

	@Override
	public Enchere selectByMontant(int montantEnchere) throws BusinessException {
		Enchere enchere = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			try {
				PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_MONTANT);
				pstmt.setInt(1, montantEnchere);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					enchere = new Enchere(rs.getInt("no_utilisateur"), rs.getInt("no_article"),
							rs.getDate("date_enchere"), rs.getInt("montant_enchere"));
				}
				cnx.close();
				pstmt.close();
			} catch (SQLException e) {
				throw new BusinessException(BusinessException.DAL_EXCEPTION_SELECT_ENCHERE);
			}
		} catch (SQLException e1) {
			throw new BusinessException(BusinessException.DAL_EXCEPTION_CLASS_NOT_FOUND);
		}
		return enchere;
	}

	@Override
	public void insert(Enchere enchere) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			try {
				cnx.setAutoCommit(false);
				PreparedStatement pstmt = cnx.prepareStatement(INSERT);
				pstmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
				pstmt.setInt(2, enchere.getMontantEnchere());
				pstmt.setInt(3, enchere.getIdUtilisateur());
				pstmt.setInt(4, enchere.getIdArticle());
				pstmt.executeUpdate();
				cnx.commit();
				cnx.close();
				pstmt.close();
			} catch (SQLException e) {
				cnx.rollback();
				throw new BusinessException(BusinessException.DAL_EXCEPTION_INSERT_ENCHERE);
			}
		} catch (SQLException e1) {
			throw new BusinessException(BusinessException.DAL_EXCEPTION_CLASS_NOT_FOUND);
		}
	}

	@Override
	public void update(Enchere enchere) throws BusinessException {
		System.out.println("encher jdbcmli:" + enchere);
		try (Connection cnx = ConnectionProvider.getConnection()) {

			try {
				PreparedStatement pstmt = cnx.prepareStatement(UPDATE);
				pstmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
				pstmt.setInt(2, enchere.getMontantEnchere());
				pstmt.setInt(3, enchere.getIdUtilisateur());
				pstmt.setInt(4, enchere.getIdArticle());
				pstmt.executeUpdate();
				cnx.close();
				pstmt.close();
			} catch (SQLException e) {
				throw new BusinessException(BusinessException.DAL_EXCEPTION_UPDATE_ENCHERE);
			}
		} catch (SQLException e1) {
			throw new BusinessException(BusinessException.DAL_EXCEPTION_CLASS_NOT_FOUND);
		}
	}

	@Override
	public void delete(int idArticle) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			try {
				PreparedStatement pstmt = cnx.prepareStatement(DELETE);
				pstmt.setInt(1, idArticle);
				pstmt.executeUpdate();
				cnx.close();
				pstmt.close();
			} catch (SQLException e) {
				throw new BusinessException(BusinessException.DAL_EXCEPTION_DELETE_ENCHERE);
			}
		} catch (SQLException e1) {
			throw new BusinessException(BusinessException.DAL_EXCEPTION_CLASS_NOT_FOUND);
		}
	}
}
