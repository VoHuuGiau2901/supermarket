package com.supermarket.api.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.supermarket.api.service.GlobalService.Constant;

@Entity
@Table(name = Constant.TABLE_PREFIX + "role")
public class Role {
	@Id
	@Column(name = "role_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roleId_sequence")
	@SequenceGenerator(name = "roleId_sequence", sequenceName = "roleId_sequence", allocationSize = 1, initialValue = 1)
	private Long id;

	@Column(name = "name")
	private String name;

	@OneToMany(mappedBy = "role")
	@JsonIgnoreProperties({ "role" })
	private List<User> users;

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
