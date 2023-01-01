package com.supermarket.api.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supermarket.api.dao.UserDAO;
import com.supermarket.api.entity.User;
import com.supermarket.api.form.LoginForm;
import com.supermarket.api.form.SignUpForm;
import com.supermarket.api.service.Constant;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserDAO userDAO;

	@Autowired
	PasswordEncoder passwordEncoder;

	@GetMapping("/list")
	public List<User> getAll() {
		return userDAO.findAll();
	}

	@PostMapping("/login")
	public String login(@RequestBody LoginForm loginForm) {
		User user = userDAO.findByEmail(loginForm.getEmail());
		if (user == null) {
			return "Login Failed, Wrong email";
		}
		boolean valid = passwordEncoder.matches(loginForm.getPassword(), user.getPassword());
		if (valid) {
			return "Login Success";
		} else {
			return "Login Failed, Wrong password";
		}
	}

	@PostMapping("/signUp")
	public String add(@RequestBody SignUpForm signUpForm) throws ParseException {

		System.out.println(signUpForm.toString());

		User regUser = userDAO.findByEmailOrPhone(signUpForm.getEmail(), signUpForm.getPhone());
		if (regUser != null) {
			if (regUser.getEmail().equalsIgnoreCase(signUpForm.getEmail())) {
				return "email taken";
			}
			if (regUser.getPhone().equalsIgnoreCase(signUpForm.getPhone())) {
				return "phone taken";
			}
		}

		regUser = new User();

		regUser.setFullname(signUpForm.getFullname());
		regUser.setPassword(passwordEncoder.encode(signUpForm.getPassword()));
		regUser.setAddress(signUpForm.getAddress());
		regUser.setDob(Constant.DATE_FORMAT.parse(signUpForm.getDob()));
		regUser.setEmail(signUpForm.getEmail());
		regUser.setPhone(signUpForm.getPhone());

		regUser.setCreatedDate(Constant.getCurrentDateTime());
		regUser.setModifiedDate(null);
		regUser.setStatus(Constant.ACTIVE_STATUS);

		userDAO.save(regUser);

		return "SignUp successfully";
	}
}
