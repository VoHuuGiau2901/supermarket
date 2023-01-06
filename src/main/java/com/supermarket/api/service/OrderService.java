package com.supermarket.api.service;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.supermarket.api.dao.OrderDAO;
import com.supermarket.api.entity.Category;
import com.supermarket.api.entity.Order;

import com.supermarket.api.exception.NotFoundException;
import com.supermarket.api.service.GlobalService.Constant;

import com.supermarket.api.exception.ResourceNotFoundException;

import com.supermarket.api.exception.NullObjectException;


 

@Service
public class OrderService {
	@Autowired
	OrderDAO orderDAO;

	public Order findOrder(Long id) {
		Order order = orderDAO.findById(id).orElse(null);
		if ( order == null) {
			throw new NotFoundException("There is no Order with id = " + id);
		}
		return order;
	}




	public List<Order> getAllOrder() {
		return orderDAO.findAll();
	}
	
	public Order add(Order order) {
		if(order == null )
		{
			throw new NullObjectException("order");
		}
		order.setCreatedDate(Constant.getCurrentDateTime());
	
		return orderDAO.save(order);
	}
	
	public void delete(Long id) {
		Optional<Order> opt =  orderDAO.findById(id);
		
		if(!opt.isPresent())
		{
			throw new ResourceNotFoundException("Category", "Category Id", String.valueOf(id));
		}
		orderDAO.deleteById(id);
	}
}
