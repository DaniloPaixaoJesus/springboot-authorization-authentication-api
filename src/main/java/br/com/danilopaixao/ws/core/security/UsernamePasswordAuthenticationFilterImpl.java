package br.com.danilopaixao.ws.core.security;
import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UsernamePasswordAuthenticationFilterImpl extends UsernamePasswordAuthenticationFilter   {
	
	// We use auth manager to validate the user credentials
	private AuthenticationManager authManager;
	
	private final SecurityConfig securityConfig;
    
	public UsernamePasswordAuthenticationFilterImpl(AuthenticationManager authManager, SecurityConfig securityConfig) {
		this.authManager = authManager;
		this.securityConfig = securityConfig;
		
		// By default, UsernamePasswordAuthenticationFilter listens to "/login" path. 
		// In our case, we use "/auth". So, we need to override the defaults.
		this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(securityConfig.getUri(), "POST"));
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		try {
			
			// 1. Get credentials from request
			UserCredentials creds = new ObjectMapper().readValue(request.getInputStream(), UserCredentials.class);
			
			// 2. Create auth object (contains credentials) which will be used by auth manager
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
					creds.getLogin(), creds.getPassword(), Collections.emptyList());
			
			// 3. Authentication manager authenticate the user, and use UserDetialsServiceImpl::loadUserByUsername() method to load the user.
			return authManager.authenticate(authToken);
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	// Upon successful authentication, generate a token.
	// The 'auth' passed to successfulAuthentication() is the current authenticated user.
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		
		Long now = System.currentTimeMillis();
		String token = new JwtUtils(auth, now, securityConfig).getToken();
		
		// Add token to header
		response.addHeader(securityConfig.getHeader(), securityConfig.getPrefix() + token);

		response.setContentType("application/json");
		response.getWriter().write("{\"" + securityConfig.getHeader() + "\": \"" + securityConfig.getPrefix() + token + "\"}");
		response.getWriter().flush();
		response.getWriter().close();
		response.setStatus(HttpServletResponse.SC_OK);
	}

	// A (temporary) class just to represent the user credentials
	private static class UserCredentials {
	    private String login, password;

		public String getLogin() {
			return login;
		}

		public void setLogin(String login) {
			this.login = login;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}

}