package com.supermarket.api;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.supermarket.api.entity.Role;
import com.supermarket.api.service.RoleService;

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

	@PostConstruct
	public void initializeRole() {
		Role role = roleService.findRoleByName("USER");
		if (role == null) {
			System.out.println("Role user not found");
			roleService.addRole("USER");
		}
	}
}
