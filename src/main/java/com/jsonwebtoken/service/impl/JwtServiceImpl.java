package com.jsonwebtoken.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsonwebtoken.pojo.TokenGenerationPOJO;

import com.jsonwebtoken.request.TokenGenerationRequest;
import com.jsonwebtoken.request.TokenRegenerateRequest;
import com.jsonwebtoken.request.TokenValidationRequest;
import com.jsonwebtoken.service.JwtService;
import com.jsonwebtoken.util.JwtUtil;

@Service
public class JwtServiceImpl implements JwtService {

	Logger LOGGER = LoggerFactory.getLogger(JwtServiceImpl.class);
	
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public TokenGenerationPOJO generateToken(TokenGenerationRequest tokenGenerationRequest) {
		LOGGER.info("Received request to generate the access token and refresh token");
		TokenGenerationPOJO tokenGenerationPOJO = new TokenGenerationPOJO();
		try {
			String accessToken = jwtUtil.generateAccessToken(tokenGenerationRequest);
			String refreshToken = jwtUtil.generateRefreshToken(tokenGenerationRequest);
			
			LOGGER.info("Get the access and refresh token");
			tokenGenerationPOJO.setUniqueId(tokenGenerationRequest.getUniqueId());
			tokenGenerationPOJO.setAccessToken(accessToken);
			tokenGenerationPOJO.setRefreshToken(refreshToken);
			
		} catch (Exception exception) {
			LOGGER.error("An error occur while generating the token");
			throw exception;
		}
		return tokenGenerationPOJO; 
	}
	
	@Override
	public boolean validateToken(TokenValidationRequest tokenValidationRequest) {
		LOGGER.info("Received request to validate the token");
		boolean isTokenExpired = false;
		try {
			LOGGER.info("Check token is expired or not");
			isTokenExpired = jwtUtil.isTokenExipred(tokenValidationRequest.getSecretKey(), tokenValidationRequest.getAccessToken());
		} catch (Exception exception) {
			LOGGER.error("An error occur while validate the token");
			throw exception;
		}
		return isTokenExpired;
	}

	@Override
	public TokenGenerationPOJO regenerateToken(TokenRegenerateRequest tokenRegenerateRequest) {
		LOGGER.info("Received request to regenerate the access token and refresh token");
		TokenGenerationPOJO refreshGenerationPOJO = new TokenGenerationPOJO();
		try {
			LOGGER.info("Check the refresh token is expired or not");
			boolean isRefreshTokenValid = jwtUtil.isTokenExipred(tokenRegenerateRequest.getSecretKey(), tokenRegenerateRequest.getRefreshToken());
			if (isRefreshTokenValid) {
				LOGGER.info("Refresh token is valid");
				TokenGenerationRequest tokenGenerationRequest = new TokenGenerationRequest();
				tokenGenerationRequest.setUniqueId(tokenRegenerateRequest.getUniqueId());
				tokenGenerationRequest.setSecretKey(tokenRegenerateRequest.getSecretKey());
				tokenGenerationRequest.setAccessTokenTime(tokenRegenerateRequest.getAccessTokenTime());
				tokenGenerationRequest.setRefreshTokenTime(tokenRegenerateRequest.getRefreshTokenTime());
				
				TokenGenerationPOJO tokenGenerationPOJO = generateToken(tokenGenerationRequest);
				refreshGenerationPOJO.setUniqueId(tokenGenerationRequest.getUniqueId());
				refreshGenerationPOJO.setAccessToken(tokenGenerationPOJO.getAccessToken());
				refreshGenerationPOJO.setRefreshToken(tokenGenerationPOJO.getRefreshToken());
			}else {
				LOGGER.error("Refresh token is expired");
			}
		} catch (Exception exception) {
			LOGGER.error("An error occur while regenerating the token");
			throw exception;
		}
		return refreshGenerationPOJO;
	}

}
