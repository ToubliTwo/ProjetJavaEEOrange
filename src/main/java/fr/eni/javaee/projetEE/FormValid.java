package fr.eni.javaee.projetEE;

import javax.servlet.http.Part;

public class FormValid {

	public static boolean isString(String string) {
		return string != null && !string.isEmpty();
	}

	public static boolean isName(String name) {
		return name != null && name.matches("^[A-Za-z0-9_ -]{1,30}$");
	}

	public static boolean isFiltre(String filtre) {
		return filtre == null || filtre.matches("^[A-Za-z0-9_ -]{0,30}$");
	}
	
	public static boolean isEmail(String email) {
		return email != null
				&& email.matches("^[A-Za-z0-9._%\\-+!#$&/=?^|~]{1,20}@[A-Za-z0-9.-]{1,15}[.][A-Za-z]{1,3}$");
	}

	public static boolean isPassword(String password) {
		return password != null && password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).{8,30}$");
	}

	public static boolean isPhone(String phone) {
		return phone != null && phone.matches("^\\+?[0-9\\s-]{7,20}$");
	}

	public static boolean isPostalCode(String postalCode) {
		return postalCode != null && postalCode.matches("^[a-zA-Z0-9_-]{1,10}$");
	}

	public static boolean isStreet(String street) {
		return street != null && street.matches("^[a-zA-Z0-9\\s-]{1,30}$");
	}

	public static boolean isCity(String city) {
		return city != null && city.matches("^[a-zA-Z\\s-]{1,30}$");
	}

	public static boolean isDescription(String description) {
		return description != null && description.matches("^[a-zA-Z0-9\\s-]{1,300}$");
	}

	public static boolean isInt(String number) {
		return number != null && number.matches("^[0-9]{1,10}$");
	}

	public static boolean isImage(Part filePart) {
		long maxFileSize = 8 * 1024 * 1024; // 8 Mo
		if (filePart == null) {
			return false; // Aucun fichier n'a été téléchargé
		}

		// Vérifier l'extension du fichier
		String fileName = filePart.getSubmittedFileName();
		if (!fileName.matches(".+\\.(jpg|jpeg|png|gif|bmp|svg)$")) {
			return false; // Extension de fichier non valide
		}

		// Vérifier le type MIME du fichier
		String contentType = filePart.getContentType();
		if (!contentType.startsWith("image/")) {
			return false; // Type MIME non valide
		}

		// Vérifier la taille du fichier
		long fileSize = filePart.getSize();
		if (fileSize > maxFileSize) {
			return false; // Poids du fichier dépassé
		}

		// Autres vérifications possibles : dimensions de l'image, etc.

		return true; // Le fichier est une image valide
	}

}
