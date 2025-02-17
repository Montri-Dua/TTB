package com.bank.crm.dto;


import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String message;

	public ErrorResponse(String string) {
		// TODO Auto-generated constructor stub
		this.message = string;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}