package com.supermarket.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supermarket.api.dao.RoleDAO;
import com.supermarket.api.entity.Role;

@Service
public class RoleService {
	@Autowired
	RoleDAO roleDAO;

	public void addRole(String name) {
		Role newRole = new Role();
		newRole.setName(name);

		roleDAO.save(newRole);
	}

	public Role findRoleByName(String name) {
		return roleDAO.findFirstByName(name);
	}
}
