package com.supermarket.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supermarket.api.entity.Role;

public interface RoleDAO extends JpaRepository<Role, Long> {
	Role findFirstByName(String name);
}
