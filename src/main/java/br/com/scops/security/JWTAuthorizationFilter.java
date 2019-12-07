package br.com.scops.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import software.amazon.ion.IonException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private JWTUtil jwtutil;
	private UserDetailsService userDetailsService;

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil,
			UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.jwtutil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}

	// Metodo de autorização do tokens
	// Executar antes de executar
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IonException, ServletException {
		String header = request.getHeader("Authorization");
		if (header != null && header.startsWith("Bearer")) {
			UsernamePasswordAuthenticationToken auth = getAuthentication(request, header.substring(7));
			if (auth != null) {
				//token liberado 
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		try {
			chain.doFilter(request, response);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, String token) {
        if(jwtutil.tokenValido(token)) {
        	String username = jwtutil.getUsername(token);
        	UserDetails user = userDetailsService.loadUserByUsername(username);
        	return new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
        }
		return null;
	}

}
