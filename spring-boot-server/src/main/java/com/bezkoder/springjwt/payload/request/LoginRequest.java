package com.bezkoder.springjwt.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

// swagger 用的
@Schema(name="LoginRequest", description="LoginRequest description")
public class LoginRequest {

	// swagger 用的
	@Schema(description="就是登入者的帳號")
	@NotBlank
	private String username;

	// swagger 用的
	@Schema(description="就是登入者的密碼")
	@NotBlank
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
