package com.telefonica.ssnn.qg.seguridad;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.AuthenticationManager;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.providers.anonymous.AnonymousAuthenticationToken;
import org.springframework.security.ui.AuthenticationDetailsSource;
import org.springframework.security.ui.AuthenticationEntryPoint;
import org.springframework.security.ui.FilterChainOrder;
import org.springframework.security.ui.SpringSecurityFilter;
import org.springframework.security.ui.WebAuthenticationDetailsSource;
import org.springframework.security.ui.rememberme.NullRememberMeServices;
import org.springframework.security.ui.rememberme.RememberMeServices;
import org.springframework.util.Assert;

public class CustomBasicProcessingFilter extends SpringSecurityFilter implements InitializingBean {
	private AuthenticationDetailsSource authenticationDetailsSource = new WebAuthenticationDetailsSource();
	private AuthenticationEntryPoint authenticationEntryPoint;
	private AuthenticationManager authenticationManager;
	private RememberMeServices rememberMeServices = new NullRememberMeServices();
	private boolean ignoreFailure = false;
	private String credentialsCharset = "UTF-8";

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.authenticationManager, "An AuthenticationManager is required");
		if (!this.isIgnoreFailure()) {
			Assert.notNull(this.authenticationEntryPoint, "An AuthenticationEntryPoint is required");
		}

	}

	public void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String header = request.getHeader("Authorization");
		if (super.logger.isDebugEnabled()) {
			super.logger.debug("Authorization header: " + header);
		}

		if (header != null && header.startsWith("Basic ")) {
			byte[] base64Token = header.substring(6).getBytes("UTF-8");
			//String token = new String(Base64.decodeBase64(base64Token), this.getCredentialsCharset(request));
			String username = "";
			String password = "";
			int delim = (new String(Base64.decodeBase64(base64Token), this.getCredentialsCharset(request)).indexOf(":"));
			if (delim != -1) {
				username = new String(Base64.decodeBase64(base64Token), this.getCredentialsCharset(request)).substring(0, delim);
				password = "password";
				//password = token.substring(delim + 1);
			}

			if (this.authenticationIsRequired(username)) {
				UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username,
						password);
				authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));

				Authentication authResult;
				try {
					authResult = this.authenticationManager.authenticate(authRequest);
				} catch (AuthenticationException var13) {
					if (super.logger.isDebugEnabled()) {
						super.logger
								.debug("Authentication request for user: " + username + " failed: " + var13.toString());
					}

					SecurityContextHolder.getContext().setAuthentication((Authentication) null);
					this.rememberMeServices.loginFail(request, response);
					this.onUnsuccessfulAuthentication(request, response, var13);
					if (this.ignoreFailure) {
						chain.doFilter(request, response);
					} else {
						this.authenticationEntryPoint.commence(request, response, var13);
					}

					return;
				}

				if (super.logger.isDebugEnabled()) {
					super.logger.debug("Authentication success: " + authResult.toString());
				}

				SecurityContextHolder.getContext().setAuthentication(authResult);
				this.rememberMeServices.loginSuccess(request, response, authResult);
				this.onSuccessfulAuthentication(request, response, authResult);
			}
		}

		chain.doFilter(request, response);
	}

	private boolean authenticationIsRequired(String username) {
		Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();
		if (existingAuth != null && existingAuth.isAuthenticated()) {
			if (existingAuth instanceof UsernamePasswordAuthenticationToken
					&& !existingAuth.getName().equals(username)) {
				return true;
			} else {
				return existingAuth instanceof AnonymousAuthenticationToken;
			}
		} else {
			return true;
		}
	}

	protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			Authentication authResult) throws IOException {
	}

	protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException {
	}

	protected AuthenticationEntryPoint getAuthenticationEntryPoint() {
		return this.authenticationEntryPoint;
	}

	public void setAuthenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

	protected AuthenticationManager getAuthenticationManager() {
		return this.authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	protected boolean isIgnoreFailure() {
		return this.ignoreFailure;
	}

	public void setIgnoreFailure(boolean ignoreFailure) {
		this.ignoreFailure = ignoreFailure;
	}

	public void setAuthenticationDetailsSource(AuthenticationDetailsSource authenticationDetailsSource) {
		Assert.notNull(authenticationDetailsSource, "AuthenticationDetailsSource required");
		this.authenticationDetailsSource = authenticationDetailsSource;
	}

	public void setRememberMeServices(RememberMeServices rememberMeServices) {
		Assert.notNull(rememberMeServices, "rememberMeServices cannot be null");
		this.rememberMeServices = rememberMeServices;
	}

	public void setCredentialsCharset(String credentialsCharset) {
		Assert.hasText(credentialsCharset, "credentialsCharset cannot be null or empty");
		this.credentialsCharset = credentialsCharset;
	}

	protected String getCredentialsCharset(HttpServletRequest httpRequest) {
		return this.credentialsCharset;
	}

	public int getOrder() {
		return FilterChainOrder.BASIC_PROCESSING_FILTER;
	}
}