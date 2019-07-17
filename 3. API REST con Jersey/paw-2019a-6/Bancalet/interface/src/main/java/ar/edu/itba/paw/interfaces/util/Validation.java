package ar.edu.itba.paw.interfaces.util;

import org.springframework.http.HttpStatus;

public enum Validation {

	// General
	OK("OK", HttpStatus.OK), DATABASE_ERROR("Database error", HttpStatus.SERVICE_UNAVAILABLE),

	// Email
	EMAIL_ERROR("Email couldn't be sent", HttpStatus.SERVICE_UNAVAILABLE);

	private String message;
	private HttpStatus httpStatus;

	Validation(String message, HttpStatus status) {
		this.message = message;
		this.httpStatus = status;
	}

	public String getMessage() {
		return message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public Validation withMessage(String msg) {
		this.message = this.message + ": " + msg;
		return this;
	}

	public boolean isOk() {
		return this == Validation.OK;
	}

	public boolean isError() {
		return !this.isOk();
	}
}
