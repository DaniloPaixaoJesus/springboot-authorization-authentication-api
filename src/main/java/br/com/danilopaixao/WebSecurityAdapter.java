package br.com.danilopaixao;

import javax.servlet.http.HttpServletResponse;

import br.com.danilopaixao.security.OncePerRequestFilterImpl;
import br.com.danilopaixao.security.SecurityConfig;
import br.com.danilopaixao.security.UsernamePasswordAuthenticationFilterImpl;
import br.com.danilopaixao.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityAdapter extends WebSecurityConfigurerAdapter {

	/**
	 *  Test authenticate:
	 *  POST localhost:8080/auth/
	 *  body: { "login":"user", "password":"123456" }
	 */


	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private SecurityConfig securityConfig;

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("OPTIONS");
		config.addAllowedMethod("GET");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("DELETE");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http //
			.cors()
			.and()
			// disable csrf spring verification on form
			.csrf().disable()
			// make sure we use stateless session; session won't be used to store user's state.
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) 
			.and()
			// handle an authorized attempts 
			.exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
			.and()
				// Add a filter to validate the tokens with every request
				.addFilter(new UsernamePasswordAuthenticationFilterImpl(authenticationManager(), securityConfig))
				.addFilterAfter(new OncePerRequestFilterImpl(securityConfig), UsernamePasswordAuthenticationFilter.class)
			// authorization requests config
			.authorizeRequests()
				.antMatchers("/api/v1/**").authenticated()
			//.antMatchers("/api/v1/**").hasRole("ADMIN")/*.authenticated()*/
			.antMatchers(HttpMethod.GET, securityConfig.getSwaggerUI()).permitAll()
				// allow all who are accessing "auth" service
				.antMatchers(HttpMethod.POST, securityConfig.getUri()).permitAll()
				.antMatchers(HttpMethod.POST, "/**").permitAll()
				.antMatchers(HttpMethod.GET, "/**").permitAll()
				// must be an admin if trying to access admin area (authentication is also required here)
				// Any other request must be authenticated
				.anyRequest().authenticated(); 
	}
	
	// Spring has UserDetailsService interface, which can be overriden to provide our implementation for fetching user from database (or any other source).
	// The UserDetailsService object is used by the auth manager to load the user from database.
	// In addition, we need to define the password encoder also. So, auth manager can compare and verify passwords.
	//@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
  	public SecurityConfig jwtConfig() {
    	   return new SecurityConfig();
  	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}

}
