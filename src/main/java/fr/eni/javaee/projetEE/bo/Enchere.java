package fr.eni.javaee.projetEE.bo;

import java.util.Date;

public class Enchere {

	int idUtilisateur;
	int idArticle;
	Date dateEnchere;
	int montantEnchere;

	public Enchere() {

	}

	public Enchere(int idUtilisateur, int idArticle, Date currentDate, int montantEnchere) {
		this(idUtilisateur, currentDate, montantEnchere);
		setIdArticle(idArticle);
	}

	public Enchere(int idUtilisateur, Date dateEnchere, int montantEnchere) {
		setIdUtilisateur(idUtilisateur);
		setDateEnchere(dateEnchere);
		setMontantEnchere(montantEnchere);
	}

	public int getIdUtilisateur() {
		return idUtilisateur;
	}

	public void setIdUtilisateur(int idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}

	public int getIdArticle() {
		return idArticle;
	}

	public void setIdArticle(int idArticle) {
		this.idArticle = idArticle;
	}

	public Date getDateEnchere() {
		return dateEnchere;
	}

	public void setDateEnchere(Date dateEnchere) {
		this.dateEnchere = dateEnchere;
	}

	public int getMontantEnchere() {
		return montantEnchere;
	}

	public void setMontantEnchere(int montantEnchere) {
		this.montantEnchere = montantEnchere;
	}

	@Override
	public String toString() {
		return "Enchere [idUtilisateur=" + idUtilisateur + ", idArticle=" + idArticle + ", dateEnchere=" + dateEnchere
				+ ", montantEnchere=" + montantEnchere + "]";
	}

}