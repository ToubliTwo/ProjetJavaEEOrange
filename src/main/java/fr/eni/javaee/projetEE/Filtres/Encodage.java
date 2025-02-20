package fr.eni.javaee.projetEE.Filtres;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
	
@WebFilter(dispatcherTypes = { DispatcherType.REQUEST }, urlPatterns = { "/*"})
public class Encodage implements Filter {
	
	 @Override
	 public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	 throws IOException, ServletException {
	 request.setCharacterEncoding("utf-8");
	
	 chain.doFilter(request, response);
	 }
}
