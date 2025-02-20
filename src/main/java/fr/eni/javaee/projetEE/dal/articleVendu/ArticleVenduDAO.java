package fr.eni.javaee.projetEE.dal.articleVendu;

import java.time.LocalDateTime;
import java.util.List;

import fr.eni.javaee.projetEE.BusinessException;
import fr.eni.javaee.projetEE.bo.ArticleVendu;
import fr.eni.javaee.projetEE.bo.Utilisateur;

public interface ArticleVenduDAO {

	public void insert(ArticleVendu article) throws BusinessException;

	public void update(ArticleVendu article) throws BusinessException;

	public void updateVente(int idArticle, int newPrice) throws BusinessException;

	public void updateCreditVente(int idUtilisateur, int credit) throws BusinessException;

	public void delete(int idArticle) throws BusinessException;

	public ArticleVendu selectById(int idArticle) throws BusinessException;

	public List<ArticleVendu> selectAll() throws BusinessException;

	public List<ArticleVendu> selectByCategorie(int idCategorie) throws BusinessException;

	public List<ArticleVendu> selectByNom(String nomArticle) throws BusinessException;

	public List<ArticleVendu> selectByUtilisateur(int idUtilisateur) throws BusinessException;

	public List<ArticleVendu> selectByFiltreEtCategorie(String filtre, int idCategorie) throws BusinessException;

	public List<ArticleVendu> selectByFiltre(String filtre) throws BusinessException;

	public List<ArticleVendu> selectByDateOpen() throws BusinessException;

	public List<ArticleVendu> selectByDateBeforeBegin() throws BusinessException;

	public List<ArticleVendu> selectByDateClose() throws BusinessException;

	public List<ArticleVendu> selectByDateUser(LocalDateTime now, int idUtilisateur) throws BusinessException;
	public boolean selectByIdAndUtilisateur(int idArticle, int idUtilisateur) throws BusinessException;
	public void deleteByIdArticle(int idArticle, Utilisateur utilisateur) throws BusinessException;
}
