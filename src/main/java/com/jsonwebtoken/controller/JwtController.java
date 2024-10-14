package com.jsonwebtoken.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jsonwebtoken.pojo.TokenGenerationPOJO;
import com.jsonwebtoken.request.TokenGenerationRequest;
import com.jsonwebtoken.request.TokenRegenerateRequest;
import com.jsonwebtoken.request.TokenValidationRequest;
import com.jsonwebtoken.response.JwtResponsePOJO;
import com.jsonwebtoken.service.JwtService;

@RestController
public class JwtController {

	Logger logger = LoggerFactory.getLogger(JwtController.class);
	
	@Autowired
	private JwtService jwtService;

	@PostMapping("/generateToken")
	public JwtResponsePOJO generateToken(@RequestBody TokenGenerationRequest tokenGenerationRequest) {
		logger.info("Received request to generate the token's");
		JwtResponsePOJO jwtResponsePOJO = new JwtResponsePOJO();
		try {
			TokenGenerationPOJO tokenGenerationPOJO = jwtService.generateToken(tokenGenerationRequest);
			if (StringUtils.hasText(tokenGenerationPOJO.getAccessToken())
					&& StringUtils.hasText(tokenGenerationPOJO.getRefreshToken())) {
				logger.info("Token are successfully generated");
				jwtResponsePOJO.response("Token are", tokenGenerationPOJO, true);
			} else {
				logger.error("Unable to generate the token's");
				jwtResponsePOJO.errorResponse("Unable to generate the token");
			}
		} catch (Exception exception) {
			logger.error("An error occured while generate the token's");
			jwtResponsePOJO.errorResponse("An error occur while generating the token's!....");
		}
		return jwtResponsePOJO;
	}

	@PostMapping("/validateToken")
	public JwtResponsePOJO validateToken(@RequestBody TokenValidationRequest tokenValidationRequest) {
		logger.info("Received the request to validate the token");
		JwtResponsePOJO jwtResponsePOJO = new JwtResponsePOJO();
		try {
			if (jwtService.validateToken(tokenValidationRequest)) {
				logger.info("Token is Valid");
				jwtResponsePOJO.response("Token is Valid", null, true);
			} else {
				logger.error("Token is expired");
				jwtResponsePOJO.errorResponse("Invalid Token");
			}
		} catch (Exception exception) {
			logger.error("An error occur while validating the token");
			jwtResponsePOJO.errorResponse("An error occur while validating the token");
		}
		return jwtResponsePOJO;
	}

	@PostMapping("/regenerateToken")
	public JwtResponsePOJO regenerateToken(@RequestBody TokenRegenerateRequest tokenRegenerateRequest) {
		logger.info("Received new access and refresh token generation request");
		JwtResponsePOJO jwtResponsePOJO = new JwtResponsePOJO();
		try {
			TokenGenerationPOJO regenerateTokenGenerationPOJO = jwtService.regenerateToken(tokenRegenerateRequest);
			if (StringUtils.hasText(regenerateTokenGenerationPOJO.getAccessToken())
					&& StringUtils.hasText(regenerateTokenGenerationPOJO.getRefreshToken())) {
				logger.info("Token's are regenerated successfully");
				jwtResponsePOJO.response("Token are", regenerateTokenGenerationPOJO, true);
			} else {
				logger.error("Unable to get the regenerate token response");
				jwtResponsePOJO.errorResponse("Unable to regenerate the token");
			}
		} catch (Exception exception) {
			logger.error("An error occured while regenerate the token");
			jwtResponsePOJO.errorResponse("An error occur while regenerating the token!....");
		}
		return jwtResponsePOJO;
	}

}
