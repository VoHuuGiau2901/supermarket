package com.supermarket.api.exception;

@SuppressWarnings("serial")
public class DuplicateException extends RuntimeException {
	public DuplicateException(String message) {
		super(message);
	}
}
