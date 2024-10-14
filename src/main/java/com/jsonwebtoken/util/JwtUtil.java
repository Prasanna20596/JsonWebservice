package com.jsonwebtoken.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.jsonwebtoken.request.TokenGenerationRequest;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	public String generateAccessToken(TokenGenerationRequest tokenGenerationRequest) {
		try {
			Map<String, Object> claims = new HashMap<>();

			return Jwts.builder().setClaims(claims).setSubject(tokenGenerationRequest.getUniqueId())
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + tokenGenerationRequest.getAccessTokenTime()))
					.signWith(SignatureAlgorithm.HS512, tokenGenerationRequest.getSecretKey()).compact();

		} catch (Exception exception) {
			throw exception;
		}
	}

	public String generateRefreshToken(TokenGenerationRequest tokenGenerationRequest) {
		try {
			Map<String, Object> claims = new HashMap<>();

			return Jwts.builder().setClaims(claims).setSubject(tokenGenerationRequest.getUniqueId())
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + tokenGenerationRequest.getRefreshTokenTime()))
					.signWith(SignatureAlgorithm.HS512, tokenGenerationRequest.getSecretKey()).compact();

		} catch (Exception exception) {
			throw exception;
		}
	}

	public boolean isTokenExipred(String secretKey, String accessToken) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken).getBody();
			return true;
		} catch (ExpiredJwtException expiredJwtException) {
			return false;
		} catch (Exception exception) {
			throw exception;
		}
	}

}
