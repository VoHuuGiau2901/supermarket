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
@Table(name = Constant.TABLE_PREFIX + "order")
public class Order extends EntityBase {
	@Id
	@Column(name = "order_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Order_orderId_sequence")
	@SequenceGenerator(name = "Order_orderId_sequence", sequenceName = "Order_orderId_sequence", allocationSize = 1, initialValue = 1)

	private Long id;

	@Column(name = "orderdate")
	private String orderdate;

	@Column(name = "total")
	private String total;

	@Column(name = "user_id")
	private Long user_id;

	@Column(name = "status")
	private Integer status;
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnoreProperties(value = "orders")
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	

	




}
