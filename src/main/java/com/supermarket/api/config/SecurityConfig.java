package com.supermarket.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.supermarket.api.security.SecurityFilter;
import com.supermarket.api.service.GlobalService.Constant;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	SecurityFilter securityFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable();

		// apis that need Admin Role to call
		http.authorizeHttpRequests().antMatchers("/user/list", "/product/create", "/product/update", "/product/delete",
				"/category/create", "/category/update", "/category/delete").hasAnyAuthority(Constant.ADMIN_ROLE);

		// api that need User or Admin role to call
		http.authorizeHttpRequests().antMatchers("/user/change-password", "/user/profile", "/user/update",
				"/user/resetPassword", "/cart/**")
				.hasAnyAuthority(Constant.USER_ROLE, Constant.ADMIN_ROLE);

		http.addFilterBefore(securityFilter, BasicAuthenticationFilter.class);

		return http.build();
	}
}
