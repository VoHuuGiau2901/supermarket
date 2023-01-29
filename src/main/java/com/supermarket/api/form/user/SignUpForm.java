package com.supermarket.api.form.user;

public class SignUpForm {
	private String fullname;

	private String password;

	private String address;

	private String dob;

	private String email;

	private String phone;

	public String getFullname() {
		return fullname;
	}

	@Override
	public String toString() {
		return "SignUpForm [fullname=" + fullname + ", password=" + password + ", address=" + address + ", dob=" + dob
				+ ", email=" + email + ", phone=" + phone + "]";
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

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
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
}