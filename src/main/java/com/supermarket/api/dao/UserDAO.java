package com.supermarket.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.supermarket.api.entity.User;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {
	User findByEmail(String email);

	User findByEmailOrPhone(String email, String phone);
}