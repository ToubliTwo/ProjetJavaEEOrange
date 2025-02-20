package fr.eni.javaee.projetEE;

public class BusinessException extends Exception {
	private static final long serialVersionUID = 1L;

	
	public static String DAL_EXCEPTION_CLASS_NOT_FOUND = "Erreur de connexion à la base de données. Revenez dans quelque temps.";
	
	public static final String INSERT_UTILISATEUR_NULL = "Veuillez vérifier les informations saisies";
	public static final String DAL_EXCEPTION_INSERT_UTILISATEUR = "Erreur traitement sur la base. Echec lors de l'insertion d'un utilisateur";
	public static final String DAL_EXCEPTION_UPDATE_UTILISATEUR = "Erreur traitement sur la base. Echec lors de la mise à jour d'un utilisateur";
	public static final String DAL_EXCEPTION_DELETE_UTILISATEUR = "Erreur traitement sur la base. Echec lors de la suppression d'un utilisateur";
	public static final String DAL_EXCEPTION_SELECT_UTILISATEUR = "Erreur traitement sur la base. Aucun utilisateur trouvé";
	public static final String DAL_EXCEPTION_UPDATE_CREDIT = "Erreur traitement sur la base. Echec lors de la mise à jour du crédit d'un utilisateur";
	public static final String DAL_EXCEPTION_UPDATE_PWD = "Erreur traitement sur la base. Echec lors de la mise à jour du mot de passe d'un utilisateur";
	
	public static final String INSERT_ARTICLE_NULL = "Veuillez vérifier les informations saisies";
	public static final String DAL_EXCEPTION_INSERT_ARTICLE = "Erreur traitement sur la base. Echec lors de l'insertion d'un article";
	public static final String DAL_EXCEPTION_UPDATE_ARTICLE = "Erreur traitement sur la base. Echec lors de la mise à jour d'un article";
	public static final String DAL_EXCEPTION_UPDATE_VENTE = "Erreur traitement sur la base. Echec lors de la mise à jour du prix de vente d'un article";
	public static final String DAL_EXCEPTION_UPDATE_CREDIT_VENTE = "Erreur traitement sur la base. Echec lors de la mise à jour du crédit de l'utilisateur";
	public static final String DAL_EXCEPTION_DELETE_ARTICLE = "Erreur traitement sur la base. Echec lors de la suppression d'un article";
	public static final String DAL_EXCEPTION_UPDATE_IMAGE = "Erreur traitement sur la base. Echec lors de la mise à jour de l'image d'un article";
	public static final String DAL_EXCEPTION_SELECT_ARTICLE = "Erreur traitement sur la base. Aucun article trouvé";
	public static final String DAL_EXCEPTION_SELECT_ARTICLE_BY_CATEGORIE = "Erreur traitement sur la base. Aucun article trouvé pour cette catégorie";
	public static final String DAL_EXCEPTION_SELECT_ARTICLE_BY_NOM = "Erreur traitement sur la base. Aucun article trouvé pour ce nom";
	public static final String DAL_EXCEPTION_SELECT_ARTICLE_BY_UTILISATEUR = "Erreur traitement sur la base. Aucun article trouvé pour cet utilisateur";
	public static final String DAL_EXCEPTION_SELECT_ARTICLE_BY_ID = "Erreur traitement sur la base. Aucun article trouvé pour cet identifiant";
	public static final String DAL_EXCEPTION_SELECT_ARTICLE_BY_CATEGORIE_AND_FILTRE = "Erreur traitement sur la base. Aucun article trouvé pour ce filtre et cette catégorie";
	public static final String DAL_EXCEPTION_SELECT_ARTICLE_BY_FILTRE = "Erreur traitement sur la base. Aucun article trouvé pour ce filtre";
	public static final String DAL_EXCEPTION_SELECT_ARTICLE_BY_DATE = "Erreur traitement sur la base. Aucun article trouvé pour cette date";
	public static final String DAL_EXCEPTION_SELECT_ARTICLE_BY_DATE_AND_USER = "Erreur traitement sur la base. Aucun article trouvé pour cette date et cet utilisateur";
	public static final String DAL_EXCEPTION_SELECT_ARTICLE_BY_DATE_BEFORE_BEGIN = "Erreur traitement sur la base. Aucun article trouvé pour cette date";
	public static final String DAL_EXCEPTION_SELECT_ARTICLE_BY_DATE_CLOSE = "Erreur traitement sur la base. Aucun article trouvé pour cette date";
	
	public static final String INSERT_ENCHERE_NULL = "Veuillez vérifier les informations saisies";
	public static final String DAL_EXCEPTION_INSERT_ENCHERE = "Erreur traitement sur la base. Echec lors de l'insertion d'une enchère";
	public static final String DAL_EXCEPTION_UPDATE_ENCHERE = "Erreur traitement sur la base. Echec lors de la mise à jour d'une enchère";
	public static final String DAL_EXCEPTION_DELETE_ENCHERE = "Erreur traitement sur la base. Echec lors de la suppression d'une enchère";
	public static final String DAL_EXCEPTION_SELECT_ENCHERE = "Erreur traitement sur la base. Aucune enchère trouvée";

	public static final String DAL_EXCEPTION_INSERT_RETRAIT = "Erreur traitement sur la base. Echec lors de l'insertion d'un retrait";
	public static final String DAL_EXCEPTION_SELECT_RETRAIT = "Erreur traitement sur la base. Aucun retrait trouvée";
	
	public static final String DAL_EXCEPTION_CATEGORIES = "Erreur traitement sur la base. Aucune catégorie trouvée";
	
	public static final String BLL_EXCEPTION_LOGIN = "Erreur de connexion. Vérifiez votre email et mot de passe.";
	public static final String BLL_EXCEPTION_UTILISATEUR = "Aucun utilisateur trouvé.";
	public static final Object BLL_EXCEPTION_PSEUDO = "Le pseudo est déjà pris. Veuillez en choisir un autre.";
	public static final String BLL_EXCEPTION_TITRE = "Erreur. Veuillez vérifier vos informations.";
	public static final String BLL_EXCEPTION_EMAIL = "Votre email n'est pas valide";
	public static final String BLL_EXCEPTION_PWD = "Votre mot de passe n'est pas valide";
	public static final String BLL_EXCEPTION_PWD_DIFFERENT = "Votre mot de passe est different";
	public static final String BLL_EXCEPTION_AJOUT_UTILISATEUR = "Veuillez vérifier les informations saisies. Votre compte n'a pas pu être créé.";
	public static final String BLL_EXCEPTION_MODIF_UTILISATEUR = "Erreur lors de la modification de votre compte. Veuillez vérifier les informations saisies.";
	public static final String BLL_EXCEPTION_SUP_UTILISATEUR = "Erreur lors de la suppression de votre compte. Veuillez réessayer ultérieurement.";
	public static final String BLL_EXCEPTION_CREDIT = "Erreur lors de la mise à jour de votre crédit. Veuillez réessayer ultérieurement.";
	public static final String BLL_EXCEPTION_SELECTION_UTILISATEUR = "Aucun utilisateur trouvé. Veuillez rééssayer ultérieurement.";
	public static final String BLL_EXCEPTION_UPDATE_PWD = "Echec lors de la mise à jour du votre mot de passe. Veuillez rééssayer ultérieurement.";
	public static final Object BLL_EXCEPTION_FILTER = "Ce filtre n'est pas permis. Veuillez rééssayer.";
	
	
	public static final String BLL_EXCEPTION_ARTICLE = "Aucun article trouvé. Veuillez rééssayer ultérieurement.";
	public static final String BLL_EXCEPTION_SUCCESS_AJOUT_ARTICLE = "Votre article a bien été ajouté.";
	public static final String BLL_EXCEPTION_AJOUT_ARTICLE = "Veuillez vérifier les informations saisies. Votre article n'a pas pu être ajouté.";
	public static final Object BLL_EXCEPTION_AJOUT_IMAGE = "L'image n'a pas pu être ajoutée. Veuillez rééssayer avec un autre fichier (taille maximale 8mo)";
	public static final String BLL_EXCEPTION_MODIF_ARTICLE = "Erreur lors de la modification de l'article. Veuillez vérifier les informations saisies.";
	public static final String BLL_EXCEPTION_SUP_ARTICLE = "Erreur lors de la suppression de l'article sélectionné. Veuillez réessayer ultérieurement.";
	public static final String BLL_EXCEPTION_SELECTION_ARTICLE_CATEGORY = "Aucun article trouvé avec cette catégorie. Veuillez rééssayer avec une autre catégorie.";
	public static final String BLL_EXCEPTION_SELECTION_ARTICLE_FILTER = "Aucun article trouvé. Veuillez rééssayer avec un autre mot clé.";
	public static final String BLL_EXCEPTION_SELECTION_ARTICLE = "Aucun article trouvé. N'hésitez pas à être précurseur, mettez un article en vente !";
	public static final String BLL_EXCEPTION_SELECTION_ARTICLE_BY_UTILISATEUR = "Aucun article trouvé pour cet utilisateur. Veuillez rééssayer ultérieurement.";
	public static final String BLL_EXCEPTION_UPDATE_VENTE = "Erreur lors de la mise à jour du prix de vente de l'article sélectionné. Veuillez réessayer ultérieurement.";
	public static final String BLL_EXCEPTION_UPDATE_IMAGE = "Erreur lors de la mise à jour de l'image de l'article sélectionné. Veuillez réessayer ultérieurement.";
	
	public static final String BLL_EXCEPTION_ENCHERE = "Aucune enchère trouvée. Veuillez rééssayer ultérieurement.";
	public static final String BLL_EXCEPTION_AJOUT_ENCHERE = "Veuillez vérifier les informations saisies. Votre enchère n'a pas pu être ajoutée.";
	public static final String BLL_EXCEPTION_MODIF_ENCHERE = "Erreur lors de la modification de l'enchère sélectionnée. Veuillez vérifier les informations saisies.";
	public static final String BLL_EXCEPTION_SUP_ENCHERE = "Erreur lors de la suppression de l'enchère sélectionnée. Veuillez réessayer ultérieurement.";

	public static final String BLL_EXCEPTION_DATE = "La date de fin d'enchère doit être supérieure à la date du jour.";
	public static final String BLL_EXCEPTION_NUMBER = "Veuillez vérifier les informations saisies. Les champs numériques ne sont pas valides.";

	public static final String BLL_EXCEPTION_AJOUT_RETRAIT = "Veuillez vérifier les informations saisies. L'adresse du retrait n'a pas pu être ajouté.";
	public static final String BLL_EXCEPTION_SELECTION_RETRAIT = "Aucune adresse de retrait trouvée. Veuillez rééssayer ultérieurement.";
	
	public static final String BLL_EXCEPTION_CATEGORIES = "Aucune catégorie trouvée. Veuillez rééssayer ultérieurement.";


	public static final Object BLL_EXCEPTION_DELETE_ARTICLE = "Erreur lors de la suppression de l'article sélectionné. Veuillez réessayer ultérieurement.";


	public static final String BLL_EXCEPTION_MODIFICATION_RETRAIT = "Erreur lors de la modification de l'adresse de retrait. Veuillez vérifier les informations saisies.";









	public BusinessException() {
		super();
	}

	public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}
	
}
