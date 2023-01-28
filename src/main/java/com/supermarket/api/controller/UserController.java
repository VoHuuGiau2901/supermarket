package com.supermarket.api.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.supermarket.api.entity.Product;
import com.supermarket.api.entity.User;
import com.supermarket.api.form.LoginForm;
import com.supermarket.api.form.ResponseForm;
import com.supermarket.api.form.RetypePasswordForm;
import com.supermarket.api.form.SignUpForm;
import com.supermarket.api.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/list")
	public ResponseEntity<?> getAll() {
		List<User> allUser = userService.getAllUser();
		ResponseForm<List<User>> responseForm = new ResponseForm<>();
		responseForm.setData(allUser);
		responseForm.setMessage("get Users successfully");
		responseForm.setResult(true);

		return new ResponseEntity<>(responseForm, HttpStatus.OK);
	}

	@PostMapping("/resetPassword/{id}")
	public ResponseEntity<?> resetPassword(@PathVariable("id") Long id) {

		return userService.resetPassword(id);
	}

	@PostMapping("/forget-password")
	public ResponseEntity<?> forgetPassword(@RequestBody String email) {
		return userService.anaylyzeForgetPassword(email);
	}

	@PostMapping("/retype-password")
	public ResponseEntity<?> retypePassword(@RequestBody RetypePasswordForm retypePasswordForm) {
		return userService.anaylyzeRetypePassword(retypePasswordForm);
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
