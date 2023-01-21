package com.supermarket.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supermarket.api.dao.CartItemDAO;
import com.supermarket.api.entity.CartItem;
import com.supermarket.api.exception.NotFoundException;

@Service
public class CartItemService {
	@Autowired
	CartItemDAO cartItemDAO;

	public List<CartItem> findAllByUserId(Long id) {
		return cartItemDAO.findAllByUserId(id);
	}

	public CartItem findCartItemById(Long id) {
		CartItem cartItem = cartItemDAO.findById(id).orElse(null);
		if (cartItem == null) {
			throw new NotFoundException("no cartItem with id = " + id);
		}
		return cartItem;
	}

	public void saveCartItem(CartItem cartItem) {
		cartItemDAO.save(cartItem);
	}

	public CartItem findByProIdAndUserId(Long proId, Long userId) {
		return cartItemDAO.findByProductIdAndUserId(proId, userId);
	}
	
	public void deleteCartItemById(Long id) {
		CartItem cartItem = this.findCartItemById(id);
		cartItemDAO.delete(cartItem);
	}
}
