package fr.eni.javaee.projetEE.ihm.servlets;

import java.io.IOException;

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
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/JSP/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		boolean loginValid = FormValid.isEmail(email) && FormValid.isPassword(password);
		if (!loginValid) {
			request.setAttribute("error", BusinessException.BLL_EXCEPTION_LOGIN);
			getServletContext().getRequestDispatcher("/WEB-INF/JSP/login.jsp").forward(request, response);
			return;
		}

		String hashedPassword = UtilisateurManager.hashPwd(password);

		UtilisateurManager utilisateurManager = new UtilisateurManager();
		Utilisateur u;

		try {
			u = utilisateurManager.login(email, hashedPassword);
			if (u == null) {
				request.getRequestDispatcher("/WEB-INF/JSP/index.jsp").forward(request, response);
			} else {
				request.getSession().setAttribute("userConnected", u);
				response.sendRedirect("encheres");
			}
		} catch (Exception e) {
			request.setAttribute("error", BusinessException.BLL_EXCEPTION_PWD);
			getServletContext().getRequestDispatcher("/WEB-INF/JSP/login.jsp").forward(request, response);

		}
	}

}
