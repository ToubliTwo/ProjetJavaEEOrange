package fr.eni.javaee.projetEE.dal;

import fr.eni.javaee.projetEE.dal.articleVendu.ArticleVenduDAO;
import fr.eni.javaee.projetEE.dal.articleVendu.ArticleVenduDAOJdbcImpl;
import fr.eni.javaee.projetEE.dal.categories.CategoryDAO;
import fr.eni.javaee.projetEE.dal.categories.CategoryDAOJdbcImpl;
import fr.eni.javaee.projetEE.dal.enchere.EnchereDAO;
import fr.eni.javaee.projetEE.dal.enchere.EnchereDAOJdbcImpl;
import fr.eni.javaee.projetEE.dal.retrait.RetraitDAO;
import fr.eni.javaee.projetEE.dal.retrait.RetraitDAOJdbcImpl;
import fr.eni.javaee.projetEE.dal.utilisateurs.UtilisateurDAO;
import fr.eni.javaee.projetEE.dal.utilisateurs.UtilisateurDAOJdbcImpl;

public class DAOFactory {
	
	public static UtilisateurDAO getUtilisateurDAO() {
		return new UtilisateurDAOJdbcImpl();
	}
	
	public static EnchereDAO getEnchereDAO() {
		return new EnchereDAOJdbcImpl();
	}
	
	public static ArticleVenduDAO getArticleVenduDAO() {
		return new ArticleVenduDAOJdbcImpl();
	}
	
	public static CategoryDAO getCategoryDAO() {
		return new CategoryDAOJdbcImpl();
	}
	
	public static RetraitDAO getRetraitDAO() {
		return new RetraitDAOJdbcImpl();
	}
}
