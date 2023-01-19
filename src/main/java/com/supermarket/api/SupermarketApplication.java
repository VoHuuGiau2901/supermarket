package com.supermarket.api;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.supermarket.api.dao.UserDAO;
import com.supermarket.api.entity.Role;
import com.supermarket.api.entity.User;
import com.supermarket.api.service.RoleService;
import com.supermarket.api.service.UserService;
import com.supermarket.api.service.GlobalService.Constant;

@SpringBootApplication
public class SupermarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(SupermarketApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	RoleService roleService;

	@Autowired
	UserDAO userDAO;

	@PostConstruct
	public void initializeRole() {
		Role roleUser = roleService.findRoleByName(Constant.USER_ROLE);
		if (roleUser == null) {
			System.out.println("Role user not found");
			roleService.addRole(Constant.USER_ROLE);
		}
		Role roleAdmin = roleService.findRoleByName(Constant.ADMIN_ROLE);
		if (roleAdmin == null) {
			System.out.println("Role admin not found");
			roleService.addRole(Constant.ADMIN_ROLE);
		}

		User firstAdmin = userDAO.findByEmail("admin@gmail.com");

		if (firstAdmin == null) {
			firstAdmin = new User();
			roleAdmin = roleService.findRoleByName(Constant.ADMIN_ROLE);

			firstAdmin.setRole(roleAdmin);

			firstAdmin.setEmail("admin@gmail.com");
			firstAdmin.setFullname("First Admin");
			firstAdmin.setPassword(new BCryptPasswordEncoder().encode("admin123456"));

			firstAdmin.setCreatedDate(Constant.getCurrentDateTime());
			firstAdmin.setModifiedDate(null);
			firstAdmin.setStatus(Constant.ACTIVE_STATUS);

			userDAO.save(firstAdmin);
		}
	}
}
