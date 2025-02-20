package fr.eni.javaee.projetEE.dal.articleVendu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import fr.eni.javaee.projetEE.BusinessException;
import fr.eni.javaee.projetEE.bo.ArticleVendu;
import fr.eni.javaee.projetEE.bo.Utilisateur;
import fr.eni.javaee.projetEE.dal.ConnectionProvider;

public class ArticleVenduDAOJdbcImpl implements ArticleVenduDAO {

    private static final String INSERT = "INSERT INTO ARTICLES_VENDUS(nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie, chemin_image) VALUES(?,?,?,?,?,?,?,?,?)";
    private static final String SELECT_BY_ID = "SELECT * FROM ARTICLES_VENDUS WHERE no_article=?";
    private static final String SELECT_ALL = "SELECT * FROM ARTICLES_VENDUS";
    private static final String SELECT_BY_NOM = "SELECT *,  FROM ARTICLES_VENDUS WHERE nom_article=?" ;
    private static final String SELECT_BY_NOM_FILTRE = "SELECT * FROM ARTICLES_VENDUS WHERE nom_article LIKE ?";
    private static final String SELECT_BY_CATEGORIES_FILTRE = "SELECT * FROM ARTICLES_VENDUS WHERE nom_article LIKE ? AND no_categorie=?";
    private static final String SELECT_BY_CATEGORIE = "SELECT * FROM ARTICLES_VENDUS WHERE no_categorie=?";
    private static final String SELECT_BY_UTILISATEUR = "SELECT * FROM ARTICLES_VENDUS WHERE no_utilisateur=?";
    private static final String UPDATE = "UPDATE ARTICLES_VENDUS SET nom_article=?, description=?, date_debut_encheres=?, date_fin_encheres=?, prix_initial=?, prix_vente=?, no_utilisateur=?, no_categorie=?, chemin_image=? WHERE no_article=?";
    private static final String UPDATE_VENTE = "UPDATE ARTICLES_VENDUS SET prix_vente=? WHERE no_article=?";
    private static final String UPDATE_CREDIT_VENTE = "UPDATE UTILISATEURS SET credit=? WHERE no_utilisateur=?";
    private static final String DELETE = "DELETE FROM ARTICLES_VENDUS WHERE no_article=?";
    private static final String SELECT_BY_DATE = "SELECT * FROM ARTICLES_VENDUS WHERE date_fin_encheres > GETDATE()";
    private static final String SELECT_BY_DATE_AND_USER = "SELECT * FROM ARTICLES_VENDUS WHERE date_fin_encheres > GETDATE() AND no_utilisateur=?";
    private static final String SELECT_BY_DATE_BEFORE_BEGIN = "SELECT * FROM ARTICLES_VENDUS WHERE date_debut_encheres > GETDATE()";
    private static final String SELECT_BY_DATE_CLOSE = "SELECT * FROM ARTICLES_VENDUS WHERE date_fin_encheres < GETDATE()";
    private static final String SELECT_BY_IDARTICLE_AND_IDUTILISATEUR = "SELECT * FROM ARTICLES_VENDUS WHERE no_article=? AND no_utilisateur=?";
    
	public ArticleVenduDAOJdbcImpl() {
	}

	@Override
	public void insert(ArticleVendu article) throws BusinessException {
		if (article == null) {
			throw new BusinessException(BusinessException.INSERT_ARTICLE_NULL);
		}
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			try {
				cnx.setAutoCommit(false);
				pstmt = cnx.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, article.getNomArticle());
				pstmt.setString(2, article.getDescription());
				pstmt.setTimestamp(3, new Timestamp(article.getDateDebutEncheres().getTime()));
				pstmt.setTimestamp(4, new Timestamp(article.getDateFinEncheres().getTime()));
				pstmt.setInt(5, article.getMiseAPrix());
				pstmt.setInt(6, article.getPrixVente());
				pstmt.setInt(7, article.getIdUtilisateur());
				pstmt.setInt(8, article.getIdCategory());
				pstmt.setString(9, article.getImage());
				pstmt.executeUpdate();
				rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					article.setIdArticle(rs.getInt(1));
				}
				cnx.commit();
				cnx.close();
				pstmt.close();
				rs.close();
			} catch (SQLException e) {
				cnx.rollback();
				throw new BusinessException(BusinessException.DAL_EXCEPTION_INSERT_ARTICLE);
			}
		} catch (SQLException e) {
			throw new BusinessException(BusinessException.DAL_EXCEPTION_CLASS_NOT_FOUND);
		}
	}

	@Override
	public void update(ArticleVendu article) throws BusinessException {
			if (article == null) {
				throw new BusinessException(BusinessException.DAL_EXCEPTION_UPDATE_ARTICLE);
			}
			try (Connection cnx = ConnectionProvider.getConnection()) {
				PreparedStatement pstmt = cnx.prepareStatement(UPDATE);
				pstmt.setInt(1, article.getIdArticle());
					pstmt.setString(2, article.getNomArticle());
					pstmt.setString(3, article.getDescription());
					pstmt.setTimestamp(4, new Timestamp(article.getDateDebutEncheres().getTime()));
          pstmt.setTimestamp(5, new Timestamp(article.getDateFinEncheres().getTime()));
					pstmt.setInt(6, article.getMiseAPrix());
					pstmt.setInt(7, article.getPrixVente());
					pstmt.setInt(8, article.getIdUtilisateur());
					pstmt.setInt(9, article.getIdCategory());
					pstmt.setString(10, article.getImage());
					pstmt.executeUpdate();
				} catch (SQLException e) {
					throw new BusinessException(BusinessException.DAL_EXCEPTION_UPDATE_ARTICLE);
				}
		}		

	@Override
	public void updateVente(int idArticle, int newPrice) throws BusinessException {
		if (idArticle == 0 || newPrice == 0) {
			throw new BusinessException(BusinessException.DAL_EXCEPTION_UPDATE_ENCHERE);
		}
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE_VENTE);
			pstmt.setInt(1, newPrice);
			pstmt.setInt(2, idArticle);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new BusinessException(BusinessException.DAL_EXCEPTION_UPDATE_ENCHERE);
		}
	}

	@Override
	public void updateCreditVente(int idUtilisateur, int credit) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE_CREDIT_VENTE);
			pstmt.setInt(1, credit);
			pstmt.setInt(2, idUtilisateur);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new BusinessException(BusinessException.DAL_EXCEPTION_UPDATE_CREDIT_VENTE);
		}
	}

	@Override
	public void delete(int idArticle) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(DELETE);
			pstmt.setInt(1, idArticle);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new BusinessException(BusinessException.DAL_EXCEPTION_DELETE_ARTICLE);
		}
	}
	
	@Override
	public void deleteByIdArticle(int idArticle, Utilisateur utilisateur) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(DELETE);
			pstmt.setInt(1, idArticle);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new BusinessException(BusinessException.DAL_EXCEPTION_DELETE_ARTICLE);
		}
	}
	
	@Override
	public ArticleVendu selectById(int idArticle) throws BusinessException {
		ArticleVendu article = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_ID);
			pstmt.setInt(1, idArticle);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				article = new ArticleVendu(rs.getInt("no_article"), rs.getString("nom_article"),
						rs.getString("description"), rs.getTimestamp("date_debut_encheres"),
						rs.getTimestamp("date_fin_encheres"), rs.getInt("prix_initial"), rs.getInt("prix_vente"),
						rs.getInt("no_utilisateur"), rs.getInt("no_categorie"), rs.getString("chemin_image"));
			}
		} catch (SQLException e) {
			throw new BusinessException(BusinessException.DAL_EXCEPTION_SELECT_ARTICLE_BY_ID);
		}
		return article;
	}

	@Override
	public List<ArticleVendu> selectAll() throws BusinessException {
		List<ArticleVendu> liste = new ArrayList<ArticleVendu>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
//							File imageFile = new File(rs.getString("chemin_image")); "méthode pour tronquer le nom si le chemin est complet
//							String imageName = imageFile.getName(); "méthode pour tronquer le nom si le chemin est complet
				liste.add(new ArticleVendu(rs.getInt("no_article"), rs.getString("nom_article"),
						rs.getString("description"), rs.getTimestamp("date_debut_encheres"),
						rs.getTimestamp("date_fin_encheres"), rs.getInt("prix_initial"), rs.getInt("prix_vente"),
						rs.getInt("no_utilisateur"), rs.getInt("no_categorie"), rs.getString("chemin_image")));
			}
		} catch (SQLException e) {
			throw new BusinessException(BusinessException.DAL_EXCEPTION_SELECT_ARTICLE);
		}
		return liste;
	}

	@Override
	public List<ArticleVendu> selectByCategorie(int idCategorie) throws BusinessException {
		List<ArticleVendu> liste = new ArrayList<ArticleVendu>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_CATEGORIE);
			pstmt.setInt(1, idCategorie);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				liste.add(new ArticleVendu(rs.getInt("no_article"), rs.getString("nom_article"),
						rs.getString("description"), rs.getTimestamp("date_debut_encheres"),
						rs.getTimestamp("date_fin_encheres"), rs.getInt("prix_initial"), rs.getInt("prix_vente"),
						rs.getInt("no_utilisateur"), rs.getInt("no_categorie"), rs.getString("chemin_image")));
			}
		} catch (Exception e) {
			throw new BusinessException(BusinessException.BLL_EXCEPTION_SELECTION_ARTICLE_CATEGORY);
		}
		return liste;
	}

	@Override
	public List<ArticleVendu> selectByNom(String nomArticle) throws BusinessException {
		List<ArticleVendu> liste = new ArrayList<ArticleVendu>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_NOM);
			pstmt.setString(1, nomArticle);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				liste.add(new ArticleVendu(rs.getInt("no_article"), rs.getString("nom_article"),
						rs.getString("description"), rs.getTimestamp("date_debut_encheres"),
						rs.getTimestamp("date_fin_encheres"), rs.getInt("prix_initial"), rs.getInt("prix_vente"),
						rs.getInt("no_utilisateur"), rs.getInt("no_categorie"), rs.getString("chemin_image")));
			}
		} catch (SQLException e) {
			throw new BusinessException(BusinessException.DAL_EXCEPTION_SELECT_ARTICLE_BY_NOM);
		}
		return liste;
	}

	@Override
	public List<ArticleVendu> selectByUtilisateur(int idUtilisateur) throws BusinessException {
		List<ArticleVendu> liste = new ArrayList<ArticleVendu>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_UTILISATEUR);
			pstmt.setInt(1, idUtilisateur);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				liste.add(new ArticleVendu(rs.getInt("no_article"), rs.getString("nom_article"),
						rs.getString("description"), rs.getTimestamp("date_debut_encheres"),
						rs.getTimestamp("date_fin_encheres"), rs.getInt("prix_initial"), rs.getInt("prix_vente"),
						rs.getInt("no_utilisateur"), rs.getInt("no_categorie"), rs.getString("chemin_image")));
			}
		} catch (SQLException e) {
			throw new BusinessException(BusinessException.DAL_EXCEPTION_SELECT_ARTICLE_BY_UTILISATEUR);
		}
		return liste;
	}

	@Override
	public List<ArticleVendu> selectByFiltreEtCategorie(String filtre, int idCategorie) throws BusinessException {
		List<ArticleVendu> liste = new ArrayList<ArticleVendu>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_CATEGORIES_FILTRE);
			pstmt.setString(1, "%" + filtre + "%");
			pstmt.setInt(2, idCategorie);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				liste.add(new ArticleVendu(rs.getInt("no_article"), rs.getString("nom_article"),
						rs.getString("description"), rs.getTimestamp("date_debut_encheres"),
						rs.getTimestamp("date_fin_encheres"), rs.getInt("prix_initial"), rs.getInt("prix_vente"),
						rs.getInt("no_utilisateur"), rs.getInt("no_categorie"), rs.getString("chemin_image")));
			}
		} catch (SQLException e) {
			throw new BusinessException(BusinessException.DAL_EXCEPTION_SELECT_ARTICLE_BY_CATEGORIE_AND_FILTRE);
		}
		return liste;
	}

	@Override
	public List<ArticleVendu> selectByFiltre(String filtre) throws BusinessException {
			List<ArticleVendu> liste = new ArrayList<ArticleVendu>();
			try (Connection cnx = ConnectionProvider.getConnection()) {
				PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_NOM_FILTRE);
					pstmt.setString(1, "%" + filtre + "%");
					ResultSet rs = pstmt.executeQuery();
						while (rs.next()) {
							liste.add(new ArticleVendu(rs.getInt("no_article"), rs.getString("nom_article"),
									rs.getString("description"), rs.getTimestamp("date_debut_encheres"),
									rs.getTimestamp("date_fin_encheres"), rs.getInt("prix_initial"), rs.getInt("prix_vente"),
									rs.getInt("no_utilisateur"), rs.getInt("no_categorie"),
									rs.getString("chemin_image")));
						}
			} catch (SQLException e) {
				throw new BusinessException(BusinessException.DAL_EXCEPTION_SELECT_ARTICLE_BY_FILTRE);
			}
		return liste;
	}

	@Override
	public List<ArticleVendu> selectByDateOpen() throws BusinessException {
		List<ArticleVendu> liste = new ArrayList<>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_DATE);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				liste.add(new ArticleVendu(rs.getInt("no_article"), rs.getString("nom_article"),
						rs.getString("description"), rs.getTimestamp("date_debut_encheres"),
						rs.getTimestamp("date_fin_encheres"), rs.getInt("prix_initial"), rs.getInt("prix_vente"),
						rs.getInt("no_utilisateur"), rs.getInt("no_categorie"), rs.getString("chemin_image")));
			}
		} catch (SQLException e) {
			throw new BusinessException(BusinessException.DAL_EXCEPTION_SELECT_ARTICLE_BY_DATE);
		}
		return liste;
	}

	@Override
	public List<ArticleVendu> selectByDateUser(LocalDateTime now, int idUtilisateur) throws BusinessException {
		List<ArticleVendu> liste = new ArrayList<ArticleVendu>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_DATE_AND_USER);
			pstmt.setInt(1, idUtilisateur);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				liste.add(new ArticleVendu(rs.getInt("no_article"), rs.getString("nom_article"),
						rs.getString("description"), rs.getTimestamp("date_debut_encheres"),
						rs.getTimestamp("date_fin_encheres"), rs.getInt("prix_initial"), rs.getInt("prix_vente"),
						rs.getInt("no_utilisateur"), rs.getInt("no_categorie"), rs.getString("chemin_image")));
			}
		} catch (SQLException e) {
			throw new BusinessException(BusinessException.DAL_EXCEPTION_SELECT_ARTICLE_BY_DATE_AND_USER);
		}
		return liste;
	}

	@Override
	public List<ArticleVendu> selectByDateBeforeBegin() throws BusinessException {
		List<ArticleVendu> liste = new ArrayList<ArticleVendu>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_DATE_BEFORE_BEGIN);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				liste.add(new ArticleVendu(rs.getInt("no_article"), rs.getString("nom_article"),
						rs.getString("description"), rs.getTimestamp("date_debut_encheres"),
						rs.getTimestamp("date_fin_encheres"), rs.getInt("prix_initial"), rs.getInt("prix_vente"),
						rs.getInt("no_utilisateur"), rs.getInt("no_categorie"), rs.getString("chemin_image")));
			}
		} catch (SQLException e) {
			throw new BusinessException(BusinessException.DAL_EXCEPTION_SELECT_ARTICLE_BY_DATE_BEFORE_BEGIN);
		}
		return liste;
	}

	@Override
	public List<ArticleVendu> selectByDateClose() throws BusinessException {
		List<ArticleVendu> liste = new ArrayList<ArticleVendu>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_DATE_CLOSE);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				liste.add(new ArticleVendu(rs.getInt("no_article"), rs.getString("nom_article"),
						rs.getString("description"), rs.getTimestamp("date_debut_encheres"),
						rs.getTimestamp("date_fin_encheres"), rs.getInt("prix_initial"), rs.getInt("prix_vente"),
						rs.getInt("no_utilisateur"), rs.getInt("no_categorie"), rs.getString("chemin_image")));
			}
		} catch (SQLException e) {
			throw new BusinessException(BusinessException.DAL_EXCEPTION_SELECT_ARTICLE_BY_DATE_CLOSE);
		}
		return liste;
	}
	@Override
	public boolean selectByIdAndUtilisateur(int idArticle, int idUtilisateur) {
		boolean articleDeUtilisateur = false;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_IDARTICLE_AND_IDUTILISATEUR);
			pstmt.setInt(1, idArticle);
			pstmt.setInt(2, idUtilisateur);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				articleDeUtilisateur = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return articleDeUtilisateur;
	}
}
