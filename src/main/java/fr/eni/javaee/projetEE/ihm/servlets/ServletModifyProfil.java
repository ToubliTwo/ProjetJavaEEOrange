package fr.eni.javaee.projetEE.ihm.servlets;

import java.io.IOException;

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
@WebServlet("/modify")
public class ServletModifyProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ServletModifyProfil() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/JSP/modifyProfil.jsp");
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
		String newPassword = request.getParameter("newPassword");
		String confirmNewPassword = request.getParameter("confirmNewPassword");

		UtilisateurManager utilisateurManager = new UtilisateurManager();
		Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("userConnected");
		String pswHash = UtilisateurManager.hashPwd(password);

		boolean isParamValid = FormValid.isName(pseudo) && FormValid.isName(prenom) && FormValid.isName(nom)
				&& FormValid.isEmail(email) && FormValid.isPhone(telephone) && FormValid.isStreet(rue)
				&& FormValid.isPostalCode(codePostal) && FormValid.isCity(ville) && FormValid.isPassword(password)
				&& (newPassword == null || FormValid.isPassword(newPassword))
				&& (newPassword == null || newPassword.equals(confirmNewPassword));
		if (!isParamValid) {
			request.setAttribute("error", BusinessException.BLL_EXCEPTION_MODIF_UTILISATEUR);
			request.setAttribute("pseudo", pseudo);
			request.setAttribute("prenom", prenom);
			request.setAttribute("nom", nom);
			request.setAttribute("email", email);
			request.setAttribute("telephone", telephone);
			request.setAttribute("rue", rue);
			request.setAttribute("ville", ville);
			request.setAttribute("codePostal", codePostal);
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/JSP/modifyProfil.jsp");
			rd.forward(request, response);
			return;
		}

		if (pswHash == null) {
			request.setAttribute("error", BusinessException.BLL_EXCEPTION_PWD);
			request.setAttribute("pseudo", pseudo);
			request.setAttribute("prenom", prenom);
			request.setAttribute("nom", nom);
			request.setAttribute("email", email);
			request.setAttribute("telephone", telephone);
			request.setAttribute("rue", rue);
			request.setAttribute("ville", ville);
			request.setAttribute("codePostal", codePostal);
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/JSP/modifyProfil.jsp");
			rd.forward(request, response);
			return;
		}
		if (pswHash.equals(utilisateur.getMotDePasse())) {

			if (pseudo == null) {
				pseudo = utilisateur.getPseudo();
			}
			if (prenom == null) {
				prenom = utilisateur.getPrenom();
			}
			if (nom == null) {
				nom = utilisateur.getNom();
			}
			if (email == null) {
				email = utilisateur.getEmail();
			}
			if (telephone == null) {
				telephone = utilisateur.getTelephone();
			}
			if (rue == null) {
				rue = utilisateur.getRue();
			}
			if (ville == null) {
				ville = utilisateur.getVille();
			}
			if (codePostal == null) {
				codePostal = utilisateur.getCodePostal();
			}
			if (!(newPassword == null)) {
				if (newPassword.equals(confirmNewPassword)) {
					pswHash = UtilisateurManager.hashPwd(newPassword);
				} else {
					request.setAttribute("error", BusinessException.BLL_EXCEPTION_PWD_DIFFERENT);
					request.setAttribute("pseudo", pseudo);
					request.setAttribute("prenom", prenom);
					request.setAttribute("nom", nom);
					request.setAttribute("email", email);
					request.setAttribute("telephone", telephone);
					request.setAttribute("rue", rue);
					request.setAttribute("ville", ville);
					request.setAttribute("codePostal", codePostal);
					RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/JSP/modifyProfil.jsp");
					rd.forward(request, response);
					return;
				}
			}

			Utilisateur u = new Utilisateur(utilisateur.getIdUtilisateur(), pseudo, nom, prenom, email, telephone, rue,
					codePostal, ville, pswHash, utilisateur.getCredit(), utilisateur.isAdmin());
			try {
				utilisateurManager.update(u);
				request.getSession().invalidate();
				request.getSession().setAttribute("userConnected", u);

				request.setAttribute("success", "Modification avec succès");

				RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/JSP/modifyProfil.jsp");

				rd.forward(request, response);

			} catch (BusinessException e) {
				throw new RuntimeException(e);
			}

		} else {

			request.setAttribute("error", BusinessException.BLL_EXCEPTION_PWD);
			request.setAttribute("pseudo", pseudo);
			request.setAttribute("prenom", prenom);
			request.setAttribute("nom", nom);
			request.setAttribute("email", email);
			request.setAttribute("telephone", telephone);
			request.setAttribute("rue", rue);
			request.setAttribute("ville", ville);
			request.setAttribute("codePostal", codePostal);
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/JSP/modifyProfil.jsp");
			rd.forward(request, response);

		}

	}
}
