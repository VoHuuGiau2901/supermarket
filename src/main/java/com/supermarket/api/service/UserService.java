package com.supermarket.api.service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.supermarket.api.dao.RoleDAO;
import com.supermarket.api.dao.UserDAO;
import com.supermarket.api.entity.Role;
import com.supermarket.api.entity.User;
import com.supermarket.api.exception.AuthenticateException;
import com.supermarket.api.exception.DuplicateException;
import com.supermarket.api.form.LoginForm;
import com.supermarket.api.form.ResponseForm;
import com.supermarket.api.form.SignUpForm;
import com.supermarket.api.security.MyAuthentication;
import com.supermarket.api.security.SecurityUtils;
import com.supermarket.api.service.GlobalService.Constant;

@Service
public class UserService {
	@Autowired
	UserDAO userDAO;

	@Autowired
	RoleDAO roleDAO;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	SecurityUtils securityUtils;

	public ResponseEntity<?> authenticateUser(LoginForm loginForm) {
		User user = userDAO.findByEmail(loginForm.getEmail());
		if (user == null) {
			throw new AuthenticateException("Email " + loginForm.getEmail() + " is invalid");
		}
		boolean valid = passwordEncoder.matches(loginForm.getPassword(), user.getPassword());
		if (valid) {
			System.out.println(user);

			String token = securityUtils.GenerateJwt(user);

			MyAuthentication myAuthentication = new MyAuthentication(token);

			myAuthentication.setAuthenticated(true);

			SecurityContext securityContext = SecurityContextHolder.getContext();
			securityContext.setAuthentication(myAuthentication);

			Map<String, String> response = new HashMap<>();
			response.put("Token", token);
			response.put("Role", user.getRole().getName());

			return new ResponseEntity<>(response, HttpStatus.OK);
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

	public ResponseEntity<?> createUser(SignUpForm signUpForm) throws ParseException {
		this.checkDuplicate(signUpForm);

		User regUser = new User();

		Role roleUser = roleDAO.findFirstByName("USER");

		regUser.setRole(roleUser);

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

		return new ResponseEntity<>(new ResponseForm("Account Created", true), HttpStatus.OK);
	}

	public List<User> getAllUser() {
		return userDAO.findAll();
	}

}
