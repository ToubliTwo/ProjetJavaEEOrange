package fr.eni.javaee.projetEE.dal.utilisateurs;

import java.util.List;

import fr.eni.javaee.projetEE.BusinessException;
import fr.eni.javaee.projetEE.bo.Utilisateur;

public interface UtilisateurDAO {

  public void insert(Utilisateur utilisateur) throws BusinessException;

  public Utilisateur update(Utilisateur utilisateur) throws BusinessException;

  public void updatePwd(String email, String newPwd) throws BusinessException;

  public void delete(int idUtilisateur) throws BusinessException;

  public Utilisateur selectById(int idUtilisateur) throws BusinessException;

  public List<Utilisateur> selectAll() throws BusinessException;

  public Utilisateur selectByEmail(String email) throws BusinessException;

  public Utilisateur selectByPseudo(String pseudo) throws BusinessException;

  public Utilisateur login(String email, String password) throws BusinessException;

  public Utilisateur updateCredit(int idUtilisateur, int credit) throws BusinessException;
  
  public void ajoutCredit(int idUtilisateur, int credit) throws BusinessException;
  
  public int getCredit(int idUtilisateur) throws BusinessException;
}
