package fr.eni.javaee.projetEE.bll;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import fr.eni.javaee.projetEE.BusinessException;
import fr.eni.javaee.projetEE.bo.Utilisateur;
import fr.eni.javaee.projetEE.dal.DAOFactory;
import fr.eni.javaee.projetEE.dal.utilisateurs.UtilisateurDAO;

public class UtilisateurManager {

	private UtilisateurDAO utilisateurDAO;

	public UtilisateurManager() {
		this.utilisateurDAO = DAOFactory.getUtilisateurDAO();
	}

	public void ajouter(Utilisateur utilisateur) throws BusinessException {
		if (utilisateur == null) {
			throw new BusinessException(BusinessException.BLL_EXCEPTION_AJOUT_UTILISATEUR);
		}
		System.out.println("manager : " + utilisateur);
		this.utilisateurDAO.insert(utilisateur);

	}

	public Utilisateur selectById(int idUtilisateur) throws BusinessException {
		Utilisateur u = this.utilisateurDAO.selectById(idUtilisateur);
		if (u.getIdUtilisateur() == 0) {
			throw new BusinessException(BusinessException.BLL_EXCEPTION_SELECTION_UTILISATEUR);
		}
		return u;
	}

	public Utilisateur selectByEmail(String email) throws BusinessException {
		Utilisateur u = this.utilisateurDAO.selectByEmail(email);

		return u;
	}

	public Utilisateur selectByPseudo(String pseudo) throws BusinessException {
		Utilisateur u = this.utilisateurDAO.selectByPseudo(pseudo);

		return u;
	}

	public Utilisateur login(String email, String password) throws BusinessException {
		Utilisateur u = this.utilisateurDAO.login(email, password);
		if (u == null) {
			throw new BusinessException(BusinessException.BLL_EXCEPTION_LOGIN);
		}
		return u;
	}

	public void update(Utilisateur utilisateur) throws BusinessException {
		Utilisateur u = this.utilisateurDAO.update(utilisateur);
		if (u == null) {
			throw new BusinessException(BusinessException.BLL_EXCEPTION_MODIF_UTILISATEUR);
		}
	}

	public void delete(int idUtilisateur) throws BusinessException {
		this.utilisateurDAO.delete(idUtilisateur);
	}

	public void updateCredit(int idUtilisateur, int credit) throws BusinessException {
		@SuppressWarnings("unused")
		Utilisateur u = this.utilisateurDAO.updateCredit(idUtilisateur, credit);
	}
  
  public void ajoutCredit(int idUtilisateur, int credit) throws BusinessException {
	    this.utilisateurDAO.ajoutCredit(idUtilisateur, credit);
	  }
  public int getCredit(int idUtilisateur) throws BusinessException {
	    return this.utilisateurDAO.getCredit(idUtilisateur);
	  }

	public void updatePwd(String email, String newPwd) throws BusinessException {
		String hashedPassword = hashPwd(newPwd);
		this.utilisateurDAO.updatePwd(email, hashedPassword);
	}

	public static String hashPwd(String pwd) {
		MessageDigest md = null;
		StringBuffer sb = new StringBuffer();
		byte[] reponse;
		try {
			md = MessageDigest.getInstance("SHA-256");
			reponse = md.digest(pwd.getBytes());
			for (int i : reponse) {
				sb.append((Integer.toString((i & 0xff) + 0x100, 16).substring(1)));
			}

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
