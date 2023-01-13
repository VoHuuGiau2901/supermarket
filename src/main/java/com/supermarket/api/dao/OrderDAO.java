package com.supermarket.api.dao;



import org.springframework.data.jpa.repository.JpaRepository;


import com.supermarket.api.entity.Order;

public interface OrderDAO extends JpaRepository<Order, Long>{
}
