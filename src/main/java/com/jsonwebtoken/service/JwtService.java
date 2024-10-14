package com.jsonwebtoken.service;

import com.jsonwebtoken.pojo.TokenGenerationPOJO;
import com.jsonwebtoken.request.TokenGenerationRequest;
import com.jsonwebtoken.request.TokenRegenerateRequest;
import com.jsonwebtoken.request.TokenValidationRequest;

public interface JwtService {
	
	public TokenGenerationPOJO generateToken(TokenGenerationRequest tokenGenerationRequest);
	
	public boolean validateToken(TokenValidationRequest tokenValidationRequest);
	
	public TokenGenerationPOJO regenerateToken(TokenRegenerateRequest tokenRegenerateRequest);
}
