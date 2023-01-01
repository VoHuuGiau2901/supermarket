package com.supermarket.api.form.product;

import org.springframework.web.multipart.MultipartFile;

public class CreateProductForm {
	@Override
	public String toString() {
		return "CreateProductForm [proName=" + proName + ", file=" + file.getName() + ", price=" + price + ", quantity="
				+ quantity + ", categoryId=" + categoryId + "]";
	}
	private String proName;
	private MultipartFile file;
	private Long price; 
	private Integer quantity;
	private Long categoryId;
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
}
