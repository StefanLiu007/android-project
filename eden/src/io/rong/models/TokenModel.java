package io.rong.models;

public class TokenModel {
	private String code;
	private String userId;
	private String token;
	public TokenModel(String code, String userId, String token) {
		super();
		this.code = code;
		this.userId = userId;
		this.token = token;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

}
