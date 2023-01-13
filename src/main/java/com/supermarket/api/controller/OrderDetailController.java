package com.supermarket.api.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


import com.supermarket.api.entity.Order;
import com.supermarket.api.entity.OrderDetail;
import com.supermarket.api.service.OrderDetailService;
import com.supermarket.api.service.OrderService;







@RestController
@RequestMapping("/orderdetail")
public class OrderDetailController {

	@Autowired
	OrderDetailService orderdetailService;

	@PostMapping(value = "/create")
	public ResponseEntity<OrderDetail> add(@RequestBody OrderDetail orderdetail) {
		OrderDetail ordt =  orderdetailService.add(orderdetail);
		return new ResponseEntity<OrderDetail>(ordt, HttpStatus.CREATED);
	}

	
	@GetMapping("/list")
	public List<OrderDetail> getAll() {
		return orderdetailService.getAllOrderDetail();
	}

	@GetMapping("/byId/{id}")
	public OrderDetail get(@PathVariable(value = "id") Long id) {
		return orderdetailService.findOrderDetail(id);
	}
	
	

}
