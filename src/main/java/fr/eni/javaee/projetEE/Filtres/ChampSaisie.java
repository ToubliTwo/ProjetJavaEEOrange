package fr.eni.javaee.projetEE.Filtres;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Servlet Filter implementation class ChampSaisie
 */
@WebFilter(filterName = "ChampSaisie", urlPatterns = { "/modify", "/nouvelleVente", "/register", "/login" })
public class ChampSaisie extends HttpFilter implements Filter {
	private static final long serialVersionUID = 1L;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		chain.doFilter(new XssRequestWrapper((HttpServletRequest) request), response);
	}

	@Override
	public void destroy() {
	}

	private static class XssRequestWrapper extends HttpServletRequestWrapper {

		private static final Pattern[] XSS_PATTERNS = { Pattern.compile("<.*?>"), Pattern.compile("&.*?;"),
				Pattern.compile("%[0-9a-fA-F]*") };

		public XssRequestWrapper(HttpServletRequest servletRequest) {
			super(servletRequest);
		}

		@Override
		public String[] getParameterValues(String parameterName) {

			String[] values = super.getParameterValues(parameterName);

			if (values == null)
				return null;

			int count = values.length;
			for (int i = 0; i < count; i++) {
				// On remplace chaque chaîne de caractères
				values[i] = removeTags(values[i]);
			}

			return values;
		}

		@Override
		public String getParameter(String parameter) {
			return removeTags(super.getParameter(parameter));
		}

		@Override
		public String getHeader(String name) {
			return removeTags(super.getHeader(name));
		}

		private String removeTags(String value) {
			if (value != null) {
				// On retire le code ASCII 0, au cas ou
				value = value.replaceAll("\0", "");

				// Supprime l'ensemble de tags et des entités existants
				for (Pattern pattern : XSS_PATTERNS) {
					value = pattern.matcher(value).replaceAll("");
				}

				// Au cas ou les caractères < et > ne seraient pas en nombres pairs
				value = value.replaceAll("<", "");
				value = value.replaceAll(">", "");
			}
			return value;
		}
	}

}