package com.supermarket.api.exception;

@SuppressWarnings("serial")
public class AuthenticateException extends RuntimeException {
	public AuthenticateException(String message) {
		super(message);
	}
}
