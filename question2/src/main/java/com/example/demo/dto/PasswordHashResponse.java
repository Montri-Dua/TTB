package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PasswordHashResponse {
    public PasswordHashResponse(String hashedPassword2) {
		this.hashedPassword = hashedPassword2;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	private String hashedPassword;
}