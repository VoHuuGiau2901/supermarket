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


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.supermarket.api.service.GlobalService.Constant;

@Entity
@Table(name = Constant.TABLE_PREFIX + "orderdetail")
public class OrderDetail extends EntityBase {
	@Id
	@Column(name = "detail_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Detail_detailId_sequence")
	@SequenceGenerator(name = "Detail_detailId_sequence", sequenceName = "Detail_detailId_sequence", allocationSize = 1, initialValue = 1)

	private Long id;

	@Column(name = "price")
	private Long price;

	@Column(name = "quantity")
	private Long quantity;

	@Column(name = "order_id")
	private Long order_id;

	@Column(name = "product_id")
	private Long product_id;

	@Column(name = "status")
	private Integer status;
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	@JoinColumn(name = "product_id")
	@JsonIgnoreProperties(value = "orderdetails")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}

	public Long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	


	
	




}
