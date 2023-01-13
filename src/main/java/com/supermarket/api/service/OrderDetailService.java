package com.supermarket.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supermarket.api.dao.OrderDAO;
import com.supermarket.api.dao.OrderDetailDAO;
import com.supermarket.api.entity.Order;
import com.supermarket.api.entity.OrderDetail;
import com.supermarket.api.exception.NotFoundException;
import com.supermarket.api.exception.NullObjectException;
import com.supermarket.api.service.GlobalService.Constant;

@Service
public class OrderDetailService {
	@Autowired
	OrderDetailDAO orderdetailDAO;

	public OrderDetail findOrderDetail(Long id) {
		OrderDetail orderdetail = orderdetailDAO.findById(id).orElse(null);
		if ( orderdetail == null) {
			throw new NotFoundException("There is no Order with id = " + id);
		}
		return orderdetail;
	}




	public List<OrderDetail> getAllOrderDetail() {
		return orderdetailDAO.findAll();
	}
	
	
	public OrderDetail add(OrderDetail orderdetail) {
		if(orderdetail == null )
		{
			throw new NullObjectException("orderdetail");
		}
		orderdetail.setCreatedDate(Constant.getCurrentDateTime());
	
		return orderdetailDAO.save(orderdetail);
	}
}
