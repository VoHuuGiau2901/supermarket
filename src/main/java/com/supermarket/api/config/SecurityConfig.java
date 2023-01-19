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
		http.cors().and().csrf().disable().cors();

		// apis that need Admin Role to call
		http.authorizeHttpRequests().antMatchers("/product/create", "/product/update", "/product/delete",
				"/category/create", "/category/update", "/category/delete").hasAnyAuthority(Constant.ADMIN_ROLE);

//		http.authorizeHttpRequests().antMatchers("/user/**").hasAnyAuthority(Constant.USER_ROLE);

		http.addFilterBefore(securityFilter, BasicAuthenticationFilter.class);

		return http.build();
	}
}
