package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	private String message;
    private String errorCode;
    private Long timestamp;

    public ErrorResponse(String message, String string, long l) {
        this.message = message;
        this.errorCode = string;
        this.timestamp = System.currentTimeMillis();
    }

	public ErrorResponse(String string) {
		this.message = string;
	}

}