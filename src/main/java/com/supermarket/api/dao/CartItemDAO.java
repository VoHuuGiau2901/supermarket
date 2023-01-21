package com.supermarket.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.supermarket.api.entity.CartItem;

@Repository
public interface CartItemDAO extends JpaRepository<CartItem, Long> {
	List<CartItem> findAllByUserId(Long id);

	CartItem findByProductIdAndUserId(Long proId,Long userId);
}
