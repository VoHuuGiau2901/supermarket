package com.supermarket.api.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.supermarket.api.service.GlobalService.Constant;

@Entity
@Table(name = Constant.TABLE_PREFIX + "user")
public class User extends EntityBase {
	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userId_sequence")
	@SequenceGenerator(name = "userId_sequence", sequenceName = "userId_sequence", allocationSize = 1, initialValue = 1)
	private Long id;

	@Column(name = "fullname")
	private String fullname;

	@Column(name = "password")
	private String password;

	@Column(name = "address")
	private String address;

	@Column(name = "dob")
	private Date dob;

	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private String phone;
	
	@Column(name = "code")
	private String code;

	@ManyToOne
	@JsonIgnoreProperties({ "users" })
	private Role role;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties({ "user" })
	private List<CartItem> cartItems;

	public User() {
		super();
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", fullname=" + fullname + ", password=" + password + ", address=" + address
				+ ", dob=" + dob + ", email=" + email + ", phone=" + phone + ", role=" + role + ", cartItems="
				+ cartItems + "]";
	}

	public User(Long id, String fullname, String password, String address, Date dob, String email, String phone,
			Integer status) {
		super();
		this.id = id;
		this.fullname = fullname;
		this.password = password;
		this.address = address;
		this.dob = dob;
		this.email = email;
		this.phone = phone;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}
}
