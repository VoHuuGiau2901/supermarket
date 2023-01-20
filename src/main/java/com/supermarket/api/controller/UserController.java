package com.supermarket.api.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supermarket.api.entity.User;
import com.supermarket.api.form.LoginForm;
import com.supermarket.api.form.SignUpForm;
import com.supermarket.api.service.UserService;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/list")
	public List<User> getAll() {
		return userService.getAllUser();
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginForm loginForm) {
		return userService.authenticateUser(loginForm);
	}

	@PostMapping("/signUp")
	public ResponseEntity<?> add(@RequestBody SignUpForm signUpForm) throws ParseException {
		return userService.createUser(signUpForm);
	}
}
