package com.jsonwebtoken.request;

public class TokenGenerationRequest {

	private String uniqueId;
	private String applicationName;
	private String secretKey;
	private int accessTokenTime;
	private int refreshTokenTime;

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId.trim();
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName.trim();
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey.trim();
	}

	public int getAccessTokenTime() {
		return accessTokenTime;
	}

	public void setAccessTokenTime(int accessTokenTime) {
		this.accessTokenTime = accessTokenTime;
	}

	public int getRefreshTokenTime() {
		return refreshTokenTime;
	}

	public void setRefreshTokenTime(int refreshTokenTime) {
		this.refreshTokenTime = refreshTokenTime;
	}

}
