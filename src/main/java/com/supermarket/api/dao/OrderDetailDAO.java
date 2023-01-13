package com.supermarket.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supermarket.api.entity.OrderDetail;



public interface OrderDetailDAO extends JpaRepository<OrderDetail, Long> {

}
