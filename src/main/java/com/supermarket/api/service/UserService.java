package com.supermarket.api.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.supermarket.api.dao.UserDAO;
import com.supermarket.api.entity.User;
import com.supermarket.api.exception.AuthenticateException;
import com.supermarket.api.exception.DuplicateException;
import com.supermarket.api.exception.NotFoundException;
import com.supermarket.api.form.LoginForm;
import com.supermarket.api.form.SignUpForm;
import com.supermarket.api.service.GlobalService.Constant;

@Service
public class UserService {
	@Autowired
	UserDAO userDAO;

	@Autowired
	PasswordEncoder passwordEncoder;

	public String authenticateUser(LoginForm loginForm) {
		User user = userDAO.findByEmail(loginForm.getEmail());
		if (user == null) {
			throw new AuthenticateException("Email " + loginForm.getEmail() + " is invalid");
		}
		boolean valid = passwordEncoder.matches(loginForm.getPassword(), user.getPassword());
		if (valid) {
			return "login success";
		} else {
			throw new AuthenticateException("password is not match for " + loginForm.getEmail());
		}
	}

	public Boolean checkDuplicate(SignUpForm signUpForm) {
		User regUser = userDAO.findByEmailOrPhone(signUpForm.getEmail(), signUpForm.getPhone());
		if (regUser != null) {
			if (regUser.getEmail().equalsIgnoreCase(signUpForm.getEmail())) {
				throw new DuplicateException("Email " + signUpForm.getEmail() + " has been taken");
			}
			if (regUser.getPhone().equalsIgnoreCase(signUpForm.getPhone())) {
				throw new DuplicateException("Phone " + signUpForm.getPhone() + " has been taken");
			}
		}
		return true;
	}

	public String createUser(SignUpForm signUpForm) throws ParseException {
		this.checkDuplicate(signUpForm);

		User regUser = new User();

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

		return "signUp successfully";
	}

	public List<User> getAllUser() {
		return userDAO.findAll();
	}

}
