package com.supermarket.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.supermarket.api.service.GlobalService.Constant;

@Entity
@Table(name = Constant.TABLE_PREFIX + "cartItem")
public class CartItem {
	@Id
	@Column(name = "cartItem_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cartItemId_sequence")
	@SequenceGenerator(name = "cartItemId_sequence", sequenceName = "cartItemId_sequence", allocationSize = 1, initialValue = 1)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "product_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Product product;

	private Long quantity;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
//	@JsonIgnoreProperties({ "cartItems" })
	@JsonIgnore
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
