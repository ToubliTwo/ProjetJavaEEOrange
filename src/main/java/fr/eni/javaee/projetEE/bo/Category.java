package fr.eni.javaee.projetEE.bo;

public class Category {

	private int idCategory;
	private String libelle;

	public Category() {

	}

	public Category(int idCategory, String libelle) {
		setIdCategory(idCategory);
		setLibelle(libelle);
	}

	public int getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	@Override
	public String toString() {
		return "Category [idCategory=" + idCategory + ", libelle=" + libelle + "]";
	}
}
