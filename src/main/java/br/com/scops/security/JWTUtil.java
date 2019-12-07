package br.com.scops.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;

	public String generateToken(String username) {
		return Jwts.builder().setSubject(username).setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
	}

	public boolean tokenValido(String token) {
		Claims clains = getClass(token);
		if (clains != null) {
			String username = clains.getSubject();
			Date experitaionDate = clains.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			if (username != null && experitaionDate != null && now.before(experitaionDate)) {
				return true;
			}
		}
		return false;
	}

	public String getUsername(String token) {
		Claims clains = getClass(token);
		if (clains != null) {
			return clains.getSubject();
		}
		return null;
	}

	private Claims getClass(String token) {
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			return null;
		}
	}
}