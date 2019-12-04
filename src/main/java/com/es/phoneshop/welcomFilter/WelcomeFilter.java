package com.es.phoneshop.welcomFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class WelcomeFilter implements Filter {
	private FilterConfig filterConfig;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) request).getSession();
		if ((boolean)session.getAttribute("isFirst") == true) {
			session.setAttribute("isFirst", false);
			request.setAttribute("rootUrl", ((HttpServletRequest) request).getRequestURI());
			request.getRequestDispatcher("/WEB-INF/pages/welcomePage.jsp").forward(request, response);
		}
	}

	@Override
	public void destroy() {
	}
}
