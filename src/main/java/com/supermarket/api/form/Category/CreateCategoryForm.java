package com.supermarket.api.form.Category;

public class CreateCategoryForm {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "CreateCategoryForm [name=" + name + "]";
	}
}