package fr.eni.javaee.projetEE.Filtres;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(dispatcherTypes = { DispatcherType.REQUEST }, urlPatterns = { "/modify", "/nouvelleVente", "/deleteAccount", "/confirmDeleteAccount", "/ajoutCredits" ,"/afficheEnchere","/deleteArticle"})
public class Login implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        if(httpServletRequest.getSession().getAttribute("userConnected")!=null)
        {
            filterChain.doFilter(servletRequest, servletResponse);
        }else {
            //Pas de user en session
            ((HttpServletResponse)servletResponse).sendRedirect("/ProjetJavaEEOrange/login");
        }
    }
}
