package fr.eni.javaee.projetEE.ihm.servlets;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.javaee.projetEE.BusinessException;
import fr.eni.javaee.projetEE.FormValid;
import fr.eni.javaee.projetEE.bll.UtilisateurManager;

/**
 * Servlet implementation class ServeltMotDePassePerdu
 */
@WebServlet("/initialiserMotDePasse")
public class ServletMotDePassePerdu extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// FIX ME envoie de mail même si mail non présent dans BDD !!

	// renvoie vers la page initPwd.jsp qui sert à réinitialiser le mot de passe
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/initPwd.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Récupérer l'adresse e-mail saisie par l'utilisateur
		String email = request.getParameter("email");

		// Vérifier que l'émail est valide
		boolean emailValid = FormValid.isEmail(email);
		if (!emailValid) {
			request.setAttribute("error", BusinessException.BLL_EXCEPTION_EMAIL);
			request.getRequestDispatcher("/WEB-INF/JSP/initPwd.jsp").forward(request, response);
			getServletContext().getRequestDispatcher("/WEB-INF/JSP/initPwd.jsp").forward(request, response);
			return;
		}

		// Générer un nouveau mot de passe aléatoire
		String newPassword = "!aA" + generateRandomPassword();

		UtilisateurManager utilisateurManager = new UtilisateurManager();
		try {
			utilisateurManager.updatePwd(email, newPassword);
		} catch (BusinessException e) {
			request.setAttribute("error", BusinessException.BLL_EXCEPTION_UPDATE_PWD);
			getServletContext().getRequestDispatcher("/WEB-INF/JSP/succesInitPwd.jsp").forward(request, response);
		}

		// Envoi d'un e-mail à l'utilisateur avec le nouveau mot de passe
		sendEmail(email, newPassword);

		// Rediriger vers une page de confirmation
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/succesInitPwd.jsp");
		rd.forward(request, response);
	}

	private void sendEmail(String toEmail, String newPassword) {
		// Configuration pour l'envoi d'e-mail
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.office365.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.auth", "true");

		// Authentification SMTP
		Authenticator authenticator = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("projeteniorangeinitpwd@outlook.com", "ProjetJavaEEOrange2023!");
			}
		};

		// Envoyer l'e-mail
		try {
			// Créer une session de courrier
			Session session = Session.getInstance(properties, authenticator);

			// Créer un message e-mail
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("ProjetEniOrangeInitPwd@outlook.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			message.setSubject("Réinitialisation de mot de passe");
			message.setText("Votre nouveau mot de passe est : " + newPassword);

			// Envoyer le message
			Transport.send(message);
			System.out.println("E-mail envoyé avec succès !");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	private String generateRandomPassword() {
		// Générer un nouveau mot de passe aléatoire (exemple)
		Random random = new Random();
		return Integer.toString(random.nextInt(1000000));
	}
}
