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

import com.supermarket.api.controller.BaseController;
import com.supermarket.api.dao.RoleDAO;
import com.supermarket.api.dao.UserDAO;
import com.supermarket.api.entity.Role;
import com.supermarket.api.entity.User;
import com.supermarket.api.exception.AuthenticateException;
import com.supermarket.api.exception.DuplicateException;
import com.supermarket.api.exception.NotFoundException;
import com.supermarket.api.form.ResponseForm;
import com.supermarket.api.form.user.LoginForm;
import com.supermarket.api.form.user.RetypePasswordForm;
import com.supermarket.api.form.user.SignUpForm;
import com.supermarket.api.form.user.UpdateUserForm;
import com.supermarket.api.security.MyAuthentication;
import com.supermarket.api.security.SecurityUtils;
import com.supermarket.api.service.GlobalService.Constant;
import com.supermarket.api.service.GlobalService.EmailService;

@Service
public class UserService extends BaseController {
	@Autowired
	UserDAO userDAO;

	@Autowired
	RoleDAO roleDAO;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	EmailService emailService;

	private boolean verify(String raw, String hashed) {
		return passwordEncoder.matches(raw, hashed);
	}

	private String generateHash(String raw) {
		return passwordEncoder.encode(raw);
	}

	public ResponseEntity<?> authenticateUser(LoginForm loginForm) {
		User user = this.getUserByEmail(loginForm.getEmail());

		boolean valid = verify(loginForm.getPassword(), user.getPassword());

		if (valid) {
			String token = securityUtils.GenerateJwt(user);

			MyAuthentication myAuthentication = new MyAuthentication(token);

			myAuthentication.setAuthenticated(true);

			SecurityContext securityContext = SecurityContextHolder.getContext();
			securityContext.setAuthentication(myAuthentication);

			Map<String, String> response = new HashMap<>();
			response.put("Token", token);
			response.put("userName", user.getFullname());
			response.put("Role", user.getRole().getName());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			throw new AuthenticateException("password is not match for " + loginForm.getEmail());
		}
	}

	public Boolean checkDuplicate(String phone, String email) {
		List<User> checkUsers = userDAO.findAllByEmailOrPhone(email, phone);

		for (User user : checkUsers) {
			if (user.getEmail().equalsIgnoreCase(email) && user.getId() != getCurrentUserId()) {
				throw new DuplicateException("Email " + email + " has been taken");
			}
			if (user.getPhone().equalsIgnoreCase(phone) && user.getId() != getCurrentUserId()) {
				throw new DuplicateException("Phone " + phone + " has been taken");
			}
		}

		return true;
	}

	public ResponseEntity<?> createUser(SignUpForm signUpForm) throws ParseException {
		this.checkDuplicate(signUpForm.getPhone(), signUpForm.getEmail());

		User regUser = new User();

		Role roleUser = roleDAO.findFirstByName("USER");

		regUser.setRole(roleUser);

		regUser.setFullname(signUpForm.getFullname());
		regUser.setPassword(this.generateHash(signUpForm.getPassword()));
		regUser.setAddress(signUpForm.getAddress());
		regUser.setDob(Constant.DATE_FORMAT.parse(signUpForm.getDob()));
		regUser.setEmail(signUpForm.getEmail());
		regUser.setPhone(signUpForm.getPhone());

		regUser.setCreatedDate(Constant.getCurrentDateTime());
		regUser.setModifiedDate(null);
		regUser.setStatus(Constant.ACTIVE_STATUS);

		userDAO.save(regUser);

		return new ResponseEntity<>(new ResponseForm<>("Account Created", true), HttpStatus.OK);
	}

	public ResponseEntity<?> updateUser(UpdateUserForm updateUserForm) throws ParseException {
		this.checkDuplicate(updateUserForm.getPhone(), updateUserForm.getEmail());

		User updateUser = this.getUserById(getCurrentUserId());

		updateUser.setFullname(updateUserForm.getFullname());
		updateUser.setAddress(updateUserForm.getAddress());
		updateUser.setDob(Constant.DATE_FORMAT.parse(updateUserForm.getDob()));
		updateUser.setEmail(updateUserForm.getEmail());
		updateUser.setPhone(updateUserForm.getPhone());

		updateUser.setModifiedDate(Constant.getCurrentDateTime());
		updateUser.setStatus(Constant.ACTIVE_STATUS);

		userDAO.save(updateUser);

		return new ResponseEntity<>(new ResponseForm<>("Account Updated", true), HttpStatus.OK);
	}

	public ResponseEntity<?> changePassword(String oldPassword, String newPassword) {
		User updatePasswordUser = this.getUserById(getCurrentUserId());

		boolean valid = this.verify(oldPassword, updatePasswordUser.getPassword());
		if (!valid) {
			throw new AuthenticateException("old password is not match");
		}
		updatePasswordUser.setPassword(this.generateHash(newPassword));

		userDAO.save(updatePasswordUser);

		return new ResponseEntity<>(new ResponseForm<>("Password Updated", true), HttpStatus.OK);
	}

	public List<User> getAllUser() {
		return userDAO.findAllByRoleName(Constant.USER_ROLE);
	}

	public User getUserById(Long id) {
		User user = userDAO.findById(id).orElse(null);
		if (user == null) {
			throw new NotFoundException("no user with id = " + id);
		}
		return user;
	}

	public User getUserByEmail(String email) {
		User user = userDAO.findByEmail(email);
		if (user == null) {
			throw new AuthenticateException("Email " + email + " is invalid");
		}
		return user;
	}

	public ResponseEntity<?> resetPassword(Long id) {
		User user = this.getUserById(id);

		String defaultPass = "123456";

		String encryp = this.generateHash(defaultPass);

		user.setPassword(encryp);

		userDAO.save(user);

		return new ResponseEntity<>(new ResponseForm<>("reset password success", true), HttpStatus.OK);
	}

	public ResponseEntity<?> anaylyzeForgetPassword(String email) {
		User user = this.getUserByEmail(email);

		String code = emailService.getOTP();

		user.setCode(code);

		userDAO.save(user);

		emailService.sendEmail(user);

		return new ResponseEntity<>(new ResponseForm<>("check your email", true), HttpStatus.OK);
	}

	public ResponseEntity<?> anaylyzeRetypePassword(RetypePasswordForm retypePasswordForm) {
		System.out.println(retypePasswordForm);

		User user = this.getUserByEmail(retypePasswordForm.getEmail());

		if (!retypePasswordForm.getCode().equalsIgnoreCase(user.getCode())) {
			throw new AuthenticateException("invalid code");
		}

		user.setCode(null);

		user.setPassword(this.generateHash(retypePasswordForm.getPassword()));

		userDAO.save(user);

		return new ResponseEntity<>(new ResponseForm<>("change password successfully", true), HttpStatus.OK);
	}
}
