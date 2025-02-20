package fr.eni.javaee.projetEE.dal.utilisateurs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.javaee.projetEE.BusinessException;
import fr.eni.javaee.projetEE.bo.Utilisateur;
import fr.eni.javaee.projetEE.dal.ConnectionProvider;

public class UtilisateurDAOJdbcImpl implements UtilisateurDAO {

  private static final String SELECT_ALL = "SELECT * FROM UTILISATEURS";
  private static final String SELECT_BY_ID = "SELECT * FROM UTILISATEURS WHERE no_utilisateur=?";
  private static final String SELECT_BY_MAIL = "SELECT * FROM UTILISATEURS WHERE email=?";
  private static final String SELECT_BY_PSEUDO = "SELECT * FROM UTILISATEURS WHERE pseudo=?";
  private static final String INSERT = "INSERT INTO UTILISATEURS(pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
  private static final String UPDATE = "UPDATE UTILISATEURS SET pseudo=?,nom=?,prenom=?,email=?,telephone=?,rue=?,code_postal=?,ville=?,mot_de_passe=?,credit=?,administrateur=? WHERE no_utilisateur=?";
  private static final String UPDATE_PWD = "UPDATE UTILISATEURS SET mot_de_passe=? WHERE email=?";
  private static final String DELETE = "DELETE FROM UTILISATEURS WHERE no_utilisateur=?";
  private static final String LOGIN = "SELECT * FROM UTILISATEURS WHERE email=? AND mot_de_passe=?";

  // Constructeur à visiblité package pour que seule la fabrique puisse créer des
  // instances
  public UtilisateurDAOJdbcImpl() {

  }

  @Override
  public void insert(Utilisateur utilisateur) throws BusinessException {
    if (utilisateur == null) {
      throw new BusinessException(BusinessException.INSERT_UTILISATEUR_NULL);
    }
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try (Connection cnx = ConnectionProvider.getConnection()) {
      try {
        cnx.setAutoCommit(false);
        pstmt = cnx.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, utilisateur.getPseudo());
        pstmt.setString(2, utilisateur.getNom());
        pstmt.setString(3, utilisateur.getPrenom());
        pstmt.setString(4, utilisateur.getEmail());
        pstmt.setString(5, utilisateur.getTelephone());
        pstmt.setString(6, utilisateur.getRue());
        pstmt.setString(7, utilisateur.getCodePostal());
        pstmt.setString(8, utilisateur.getVille());
        pstmt.setString(9, utilisateur.getMotDePasse());
        pstmt.setInt(10, utilisateur.getCredit());
        pstmt.setBoolean(11, utilisateur.isAdmin());
        pstmt.executeUpdate();
        rs = pstmt.getGeneratedKeys();
        if (rs.next()) {
          utilisateur.setIdUtilisateur(rs.getInt(1));
        }
        cnx.commit();
        cnx.close();
        pstmt.close();
        rs.close();
      } catch (SQLException e) {
        cnx.rollback();
        throw new BusinessException(BusinessException.DAL_EXCEPTION_INSERT_UTILISATEUR);
      }
    } catch (SQLException e) {
      throw new BusinessException(BusinessException.DAL_EXCEPTION_CLASS_NOT_FOUND);
    }
  }

  @Override
  public Utilisateur update(Utilisateur utilisateur) throws BusinessException {
    if (utilisateur == null) {
      throw new BusinessException(BusinessException.DAL_EXCEPTION_UPDATE_UTILISATEUR);
    }
    try (Connection cnx = ConnectionProvider.getConnection()) {
      try {
        PreparedStatement pstmt = cnx.prepareStatement(UPDATE);
        pstmt.setString(1, utilisateur.getPseudo());
        pstmt.setString(2, utilisateur.getNom());
        pstmt.setString(3, utilisateur.getPrenom());
        pstmt.setString(4, utilisateur.getEmail());
        pstmt.setString(5, utilisateur.getTelephone());
        pstmt.setString(6, utilisateur.getRue());
        pstmt.setString(7, utilisateur.getCodePostal());
        pstmt.setString(8, utilisateur.getVille());
        pstmt.setString(9, utilisateur.getMotDePasse());
        pstmt.setInt(10, utilisateur.getCredit());
        pstmt.setBoolean(11, utilisateur.isAdmin());
        pstmt.setInt(12, utilisateur.getIdUtilisateur());

        pstmt.executeUpdate();
        cnx.close();
        pstmt.close();
      } catch (SQLException e) {
        throw new BusinessException(BusinessException.DAL_EXCEPTION_UPDATE_UTILISATEUR);
      }
    } catch (SQLException e) {
      throw new BusinessException(BusinessException.DAL_EXCEPTION_CLASS_NOT_FOUND);
    }
    return utilisateur;
  }

  @Override
  public void updatePwd(String email, String newPwd) throws BusinessException {
    try (Connection cnx = ConnectionProvider.getConnection()) {
      try {
        PreparedStatement pstmt = cnx.prepareStatement(UPDATE_PWD);
        pstmt.setString(1, newPwd);
        pstmt.setString(2, email);
        pstmt.executeUpdate();
        cnx.close();
        pstmt.close();
      } catch (SQLException e) {
        throw new BusinessException(BusinessException.DAL_EXCEPTION_UPDATE_PWD);
      }
    } catch (SQLException e) {
      throw new BusinessException(BusinessException.DAL_EXCEPTION_CLASS_NOT_FOUND);
    }
  }

  @Override
  public void delete(int id) throws BusinessException {
    try (Connection cnx = ConnectionProvider.getConnection()) {
      try {
        PreparedStatement pstmt = cnx.prepareStatement(DELETE);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
        cnx.close();
        pstmt.close();
      } catch (SQLException e) {
        throw new BusinessException(BusinessException.DAL_EXCEPTION_DELETE_UTILISATEUR);
      }
    } catch (SQLException e) {
      throw new BusinessException(BusinessException.DAL_EXCEPTION_CLASS_NOT_FOUND);
    }
  }

  @Override
  public Utilisateur selectById(int id) throws BusinessException {
    Utilisateur utilisateur = null;
    try (Connection cnx = ConnectionProvider.getConnection()) {
      try {
        PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_ID);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
          utilisateur = new Utilisateur(rs.getInt("no_utilisateur"), rs.getString("pseudo"),
              rs.getString("nom"), rs.getString("prenom"), rs.getString("email"),
              rs.getString("telephone"), rs.getString("rue"), rs.getString("code_postal"),
              rs.getString("ville"), rs.getString("mot_de_passe"), rs.getInt("credit"),
              rs.getBoolean("administrateur"));
        }
        cnx.close();
        pstmt.close();
        rs.close();
      } catch (SQLException e) {
        throw new BusinessException(BusinessException.DAL_EXCEPTION_SELECT_UTILISATEUR);
      }
    } catch (SQLException e) {
      throw new BusinessException(BusinessException.DAL_EXCEPTION_CLASS_NOT_FOUND);
    }
    return utilisateur;
  }

  @Override
  public List<Utilisateur> selectAll() throws BusinessException {
    List<Utilisateur> utilisateurs = new ArrayList<>();
    try (Connection cnx = ConnectionProvider.getConnection()) {
      try {
        PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
          utilisateurs.add(new Utilisateur(rs.getInt("no_utilisateur"), rs.getString("pseudo"),
              rs.getString("nom"), rs.getString("prenom"), rs.getString("email"),
              rs.getString("telephone"), rs.getString("rue"), rs.getString("code_postal"),
              rs.getString("ville"), rs.getString("mot_de_passe"), rs.getInt("credit"),
              rs.getBoolean("administrateur")));
        }
        cnx.close();
        pstmt.close();
        rs.close();
      } catch (SQLException e) {
        throw new BusinessException(BusinessException.DAL_EXCEPTION_SELECT_UTILISATEUR);
      }
    } catch (SQLException e) {
      throw new BusinessException(BusinessException.DAL_EXCEPTION_CLASS_NOT_FOUND);
    }
    return utilisateurs;

  }

  @Override
  public Utilisateur selectByEmail(String email) throws BusinessException {
    Utilisateur utilisateur = null;
    try (Connection cnx = ConnectionProvider.getConnection()) {
      try {
        PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_MAIL);
        pstmt.setString(1, email);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
          utilisateur = new Utilisateur(rs.getInt("no_utilisateur"), rs.getString("pseudo"),
              rs.getString("nom"), rs.getString("prenom"), rs.getString("email"),
              rs.getString("telephone"), rs.getString("rue"), rs.getString("code_postal"),
              rs.getString("ville"), rs.getString("mot_de_passe"), rs.getInt("credit"),
              rs.getBoolean("administrateur"));
        }
      } catch (SQLException e) {
        throw new BusinessException(BusinessException.DAL_EXCEPTION_SELECT_UTILISATEUR);
      }
    } catch (SQLException e) {
      throw new BusinessException(BusinessException.DAL_EXCEPTION_CLASS_NOT_FOUND);
    }
    return utilisateur;
  }

  @Override
  public Utilisateur selectByPseudo(String pseudo) throws BusinessException {
    Utilisateur utilisateur = null;
    try (Connection cnx = ConnectionProvider.getConnection()) {
      try {
        PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_PSEUDO);
        pstmt.setString(1, pseudo);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
          utilisateur = new Utilisateur(rs.getInt("no_utilisateur"), rs.getString("pseudo"),
              rs.getString("nom"), rs.getString("prenom"), rs.getString("email"),
              rs.getString("telephone"), rs.getString("rue"), rs.getString("code_postal"),
              rs.getString("ville"), rs.getString("mot_de_passe"), rs.getInt("credit"),
              rs.getBoolean("administrateur"));
        }
      } catch (SQLException e) {
        throw new BusinessException(BusinessException.DAL_EXCEPTION_SELECT_UTILISATEUR);
      }
    } catch (SQLException e) {
      throw new BusinessException(BusinessException.DAL_EXCEPTION_CLASS_NOT_FOUND);
    }
    return utilisateur;
  }

  @Override
  public Utilisateur login(String email, String motDePasse) throws BusinessException {
    Utilisateur utilisateur = null;
    try (Connection cnx = ConnectionProvider.getConnection()) {
      try (PreparedStatement pstmt = cnx.prepareStatement(LOGIN)) {
        pstmt.setString(1, email);
        pstmt.setString(2, motDePasse);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
          utilisateur = new Utilisateur(rs.getInt("no_utilisateur"), rs.getString("pseudo"),
              rs.getString("nom"), rs.getString("prenom"), rs.getString("email"),
              rs.getString("telephone"), rs.getString("rue"), rs.getString("code_postal"),
              rs.getString("ville"), rs.getString("mot_de_passe"), rs.getInt("credit"),
              rs.getBoolean("administrateur"));
        }
      } catch (SQLException e) {
        throw new BusinessException(BusinessException.DAL_EXCEPTION_SELECT_UTILISATEUR);
      }
    } catch (SQLException e) {
      throw new BusinessException(BusinessException.DAL_EXCEPTION_CLASS_NOT_FOUND);
    }
    return utilisateur;
  }

  @Override
  public Utilisateur updateCredit(int idUtilisateur, int credit) throws BusinessException {
    try (Connection cnx = ConnectionProvider.getConnection()) {
      try (PreparedStatement pstmt = cnx
          .prepareStatement("UPDATE UTILISATEURS SET credit=? WHERE no_utilisateur=?")) {
        pstmt.setInt(1, credit);
        pstmt.setInt(2, idUtilisateur);
        System.out.println("test" + pstmt);
        pstmt.executeUpdate();
      } catch (SQLException e) {
        throw new BusinessException(BusinessException.DAL_EXCEPTION_UPDATE_CREDIT);
      }
    } catch (SQLException e) {
      throw new BusinessException(BusinessException.DAL_EXCEPTION_CLASS_NOT_FOUND);
    }
	return null;
  }
  
  @Override
  public void ajoutCredit(int idUtilisateur, int credit) throws BusinessException {
    try (Connection cnx = ConnectionProvider.getConnection()) {
      try (PreparedStatement pstmt = cnx
          .prepareStatement("UPDATE UTILISATEURS SET credit=? WHERE no_utilisateur=?")) {
        pstmt.setInt(1, credit);
        pstmt.setInt(2, idUtilisateur);
        pstmt.executeUpdate();
      } catch (SQLException e) {
        throw new BusinessException(BusinessException.DAL_EXCEPTION_UPDATE_CREDIT);
      }
    } catch (SQLException e) {
      throw new BusinessException(BusinessException.DAL_EXCEPTION_CLASS_NOT_FOUND);
    }
  }
  
  @Override
  public int getCredit(int idUtilisateur) throws BusinessException{
	  int credit =0;
	  try (Connection cnx = ConnectionProvider.getConnection();
		         PreparedStatement pstmt = cnx.prepareStatement("SELECT credit FROM UTILISATEURS WHERE no_utilisateur=?")) {

		        pstmt.setInt(1, idUtilisateur);

		        try (ResultSet rs = pstmt.executeQuery()) {
		            if (rs.next()) {
		                // Récupérer la valeur de la colonne "credit"
		                credit = rs.getInt("credit");
		            }
		        } catch (SQLException e) {
		            throw new BusinessException("Erreur lors de la récupération du crédit de l'utilisateur.", e);
		        }

		    } catch (SQLException e) {
		        throw new BusinessException("Erreur lors de l'accès à la base de données.", e);
		    }
	  return credit ;
  }

}
