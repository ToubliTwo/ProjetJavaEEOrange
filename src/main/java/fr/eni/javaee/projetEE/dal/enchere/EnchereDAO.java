package fr.eni.javaee.projetEE.dal.enchere;

import java.util.List;

import fr.eni.javaee.projetEE.BusinessException;
import fr.eni.javaee.projetEE.bo.Enchere;

public interface EnchereDAO {

	public List<Enchere> selectAll() throws BusinessException;

	public Enchere selectByUtilisateur(int idUtilisateur) throws BusinessException;

	public Enchere selectByArticle(int idArticle) throws BusinessException;

	public Enchere selectByMontant(int montantEnchere) throws BusinessException;

	public void insert(Enchere enchere) throws BusinessException;

	public void update(Enchere enchere) throws BusinessException;

	public void delete(int idArticle) throws BusinessException;
}
