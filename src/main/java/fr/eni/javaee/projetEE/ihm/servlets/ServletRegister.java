package fr.eni.javaee.projetEE.ihm.servlets;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.javaee.projetEE.BusinessException;
import fr.eni.javaee.projetEE.FormValid;
import fr.eni.javaee.projetEE.bll.UtilisateurManager;
import fr.eni.javaee.projetEE.bo.Utilisateur;

/**
 * Servlet implementation class ServletHelloWorld
 */
@WebServlet("/register")
public class ServletRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ServletRegister() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/JSP/register.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pseudo = request.getParameter("pseudo");
		String prenom = request.getParameter("prenom");
		String nom = request.getParameter("nom");
		String email = request.getParameter("email");
		String telephone = request.getParameter("telephone");
		String rue = request.getParameter("rue");
		String ville = request.getParameter("ville");
		String codePostal = request.getParameter("codePostal");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");

		boolean isParamValid = FormValid.isName(pseudo) && FormValid.isName(prenom) && FormValid.isName(nom)
				&& FormValid.isEmail(email) && FormValid.isPhone(telephone) && FormValid.isStreet(rue)
				&& FormValid.isPostalCode(codePostal) && FormValid.isCity(ville) && FormValid.isPassword(password)
				&& FormValid.isPassword(confirmPassword);
		if (!isParamValid) {
			request.setAttribute("error", BusinessException.BLL_EXCEPTION_TITRE);
			request.setAttribute("pseudo", pseudo);
			request.setAttribute("prenom", prenom);
			request.setAttribute("nom", nom);
			request.setAttribute("email", email);
			request.setAttribute("telephone", telephone);
			request.setAttribute("rue", rue);
			request.setAttribute("ville", ville);
			request.setAttribute("codePostal", codePostal);
			getServletContext().getRequestDispatcher("/WEB-INF/JSP/register.jsp").forward(request, response);
			return;
		}

		if (Objects.equals(password, confirmPassword)) {

			UtilisateurManager utilisateurManager = new UtilisateurManager();

			try {

				if (utilisateurManager.selectByEmail(email) == null) {
					if (utilisateurManager.selectByPseudo(pseudo) == null) {
						password = UtilisateurManager.hashPwd(password);
						Utilisateur utilisateur = new Utilisateur(pseudo, nom, prenom, email, telephone, rue,
								codePostal, ville, password, 0, false);

						utilisateurManager.ajouter(utilisateur);

						request.getSession().setAttribute("userConnected", utilisateur);
						response.sendRedirect("/ProjetJavaEEOrange/encheres"); // Redirection sur le profil
					} else {
						// Pseudo déjà pris
						request.setAttribute("error", BusinessException.BLL_EXCEPTION_PSEUDO);
						getServletContext().getRequestDispatcher("/WEB-INF/JSP/register.jsp").forward(request,
								response);

					}
				} else {
					// Email déjà pris
					request.setAttribute("error", BusinessException.BLL_EXCEPTION_EMAIL);
					getServletContext().getRequestDispatcher("/WEB-INF/JSP/register.jsp").forward(request, response);
				}
			} catch (BusinessException e) {
				throw new RuntimeException(e);
			}

		} else {
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/JSP/register.jsp");
			rd.forward(request, response);

		}

	}
}
