package com.supermarket.api.controller;

import java.util.Objects;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.supermarket.api.security.MyAuthentication;
import com.supermarket.api.service.GlobalService.Constant;

public class BaseController {
	public Long getCurrentUserId() {
		try {
			SecurityContext securityContext = SecurityContextHolder.getContext();
			MyAuthentication authentication = (MyAuthentication) securityContext.getAuthentication();
			return (Long) authentication.getCredentials();
		} catch (Exception e) {
			return -1L;
		}
	}

	public boolean isAdmin() {
		try {
			SecurityContext securityContext = SecurityContextHolder.getContext();
			MyAuthentication authentication = (MyAuthentication) securityContext.getAuthentication();
			return Objects.equals(authentication.getDetails(), Constant.ADMIN_ROLE);
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isUser() {
		try {
			SecurityContext securityContext = SecurityContextHolder.getContext();
			MyAuthentication authentication = (MyAuthentication) securityContext.getAuthentication();
			return Objects.equals(authentication.getDetails(), Constant.USER_ROLE);
		} catch (Exception e) {
			return false;
		}
	}
}
