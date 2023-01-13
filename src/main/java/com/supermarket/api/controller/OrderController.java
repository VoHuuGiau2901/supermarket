package com.supermarket.api.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


import com.supermarket.api.entity.Order;

import com.supermarket.api.service.OrderService;







@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	OrderService orderService;

	@PostMapping(value = "/create")
	public ResponseEntity<Order> add(@RequestBody Order order) {
		Order or =  orderService.add(order);
		return new ResponseEntity<Order>(or, HttpStatus.CREATED);
	}

	
	@GetMapping("/list")
	public List<Order> getAll() {
		return orderService.getAllOrder();
	}

	@GetMapping("/byId/{id}")
	public Order get(@PathVariable(value = "id") Long id) {
		return orderService.findOrder(id);
	}
	
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable("id")Long id) {
		orderService.delete(id);
		return new ResponseEntity<String>("Order deleted successfully", HttpStatus.OK);
		
	}
	

}
