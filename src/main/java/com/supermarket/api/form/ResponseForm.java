package com.supermarket.api.form;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

public class ResponseForm {
	public Map<String, String> getResponse() {
		return response;
	}

	public void setResponse(Map<String, String> response) {
		this.response = response;
	}

	public ResponseForm(String message,Boolean result) {
		super();
		setMessage(message);
		setResult(result);
	}
	
	public ResponseForm() {
		super();
	}

	private Map<String, String> response = new HashMap<>();

	public void setResult(Boolean result) {
		response.put("result", result.toString());
	}

	public void setMessage(String message) {
		response.put("message", message);
	}
}
