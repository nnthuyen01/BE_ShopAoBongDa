package com.website.aobongda.security.jwt;

import java.util.Date;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.website.aobongda.security.userprincipal.UserPrincipal;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtProvider {
	private static Logger logger = LoggerFactory.getLogger(JwtProvider.class);
	@Value("${websach.app.jwtSecret}")
	private String jwtSecret;
	
	@Value("${websach.app.jwtExpiration}")
	private int jwtExpiration = 1296000;
	
	public String createToken(Authentication authentication, String rolePrefix) {
		UserPrincipal userPrinciple = (UserPrincipal) authentication.getPrincipal();
		return Jwts.builder().setSubject(rolePrefix + userPrinciple.getUsername()).setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime()+jwtExpiration))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature -> Message: {}", e);
		} catch (MalformedJwtException e) {
			logger.error("The token invalid format -> Message: {}", e);
		} catch (UnsupportedJwtException e) {
			logger.error("Unsupported JWT token -> Message{}",e);
		} catch (ExpiredJwtException e) {
			logger.error("Expired JWT token -> Message{}",e);
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty -> Message{}",e);
		}
		return false;
	}
	
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
	}
	
	private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
}
