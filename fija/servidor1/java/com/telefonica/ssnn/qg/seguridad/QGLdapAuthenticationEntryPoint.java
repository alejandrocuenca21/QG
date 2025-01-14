package com.telefonica.ssnn.qg.seguridad;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.AuthenticationException;
import org.springframework.security.ui.AuthenticationEntryPoint;

import sun.misc.BASE64Decoder;
import com.telefonica.ssnn.qg.utils.QGStringHelper;

/**
 * Punto de entrada para comenzar la autenticacion.
 * @author jacangas
 *
 */
public class QGLdapAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private static final String LOGIN_KEY = "login";
	
	private Logger logger = Logger.getLogger(QGLdapAuthenticationEntryPoint.class);
	
	private static final String BASIC_HEADER_TOKEN = "Basic ";
	
	public void commence(ServletRequest request, ServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
	
		if ((request instanceof HttpServletRequest) && (response instanceof HttpServletResponse)) {
			
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			
			
			if (QGStringHelper.isNotBlank(httpRequest.getHeader("authorization"))) {
				
				String authHeader = httpRequest.getHeader("authorization");
				
				int index = authHeader.indexOf(BASIC_HEADER_TOKEN);
				
				if (index != -1) {
					
					String usernamePassword = authHeader.substring(BASIC_HEADER_TOKEN.length());
					
					BASE64Decoder decoder = new BASE64Decoder();
					String decoded = new String(decoder.decodeBuffer(usernamePassword));
					
					logger.debug("DESCODIFICACION: " + decoded);
					
					//logger.info("usuario: " + decoded.split(":")[0]);
					
					httpResponse.sendRedirect(httpRequest.getContextPath() + "/login?j_username=" + decoded.split(":")[0] + "&j_password=");
					
					
				} else {
					httpRequest.getRequestDispatcher("/jsp/QGprohibido.jsp").forward(request, response);
				}
			
				
			} else {
				httpRequest.getRequestDispatcher("/jsp/QGprohibido.jsp").forward(request, response);
			}
			
				
		} else {
			throw new ServletException("Solo http.");
		}

	}
	
	private String getLogin(HttpServletRequest httpRequest) {
		String login = httpRequest.getParameter(LOGIN_KEY);
		
		if (QGStringHelper.isBlank(login)) {
			login = (String) httpRequest.getAttribute(LOGIN_KEY);
		}
		
		return login;
	}

}
