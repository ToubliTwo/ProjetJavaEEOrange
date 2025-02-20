package fr.eni.javaee.projetEE.dal.retrait;

import fr.eni.javaee.projetEE.BusinessException;

import fr.eni.javaee.projetEE.bo.Retrait;

public interface RetraitDAO {

    public Retrait selectByArticle(int idArticle) throws BusinessException;
    public void insert(Retrait retrait) throws BusinessException;
    public void update(Retrait retrait) throws BusinessException;
}
