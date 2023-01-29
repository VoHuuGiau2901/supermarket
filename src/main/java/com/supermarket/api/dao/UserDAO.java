package com.supermarket.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.supermarket.api.entity.User;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {
	User findByEmail(String email);

	List<User> findAllByEmailOrPhone(String email, String phone);

	List<User> findAllByRoleName(String roleName);
}
