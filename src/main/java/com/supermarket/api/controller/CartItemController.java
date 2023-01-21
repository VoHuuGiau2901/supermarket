package com.supermarket.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.supermarket.api.entity.CartItem;
import com.supermarket.api.entity.Product;
import com.supermarket.api.entity.User;
import com.supermarket.api.form.AddToCartForm;
import com.supermarket.api.form.ResponseForm;
import com.supermarket.api.form.UpdateCartForm;
import com.supermarket.api.service.CartItemService;
import com.supermarket.api.service.ProductService;
import com.supermarket.api.service.UserService;

@RestController
@RequestMapping("/cart")
public class CartItemController extends BaseController {

	@Autowired
	CartItemService cartItemService;

	@Autowired
	ProductService productService;

	@Autowired
	UserService userService;

	@GetMapping("/byUser/{id}")
	ResponseEntity<?> getCartItemByUserId(@PathVariable("id") Long id) {
		List<CartItem> cartItems = cartItemService.findAllByUserId(id);
		ResponseForm<List<CartItem>> responseForm = new ResponseForm<>();
		responseForm.setData(cartItems);
		responseForm.setMessage("get cart items successfully");
		responseForm.setResult(true);
		return new ResponseEntity<>(responseForm, HttpStatus.OK);
	}

	@PostMapping("/addToCart")
	ResponseEntity<?> addtoCart(@RequestBody AddToCartForm addToCartForm) {
		CartItem cartItem = cartItemService.findByProIdAndUserId(addToCartForm.getProId(), getCurrentUserId());

		if (cartItem == null) {
			cartItem = new CartItem();
			User currentUser = userService.getUserById(getCurrentUserId());
			Product product = productService.findProduct(addToCartForm.getProId());

			cartItem.setProduct(product);
			cartItem.setQuantity(addToCartForm.getQuantity());
			cartItem.setUser(currentUser);
		} else {
			cartItem.setQuantity(cartItem.getQuantity() + addToCartForm.getQuantity());
		}

		cartItemService.saveCartItem(cartItem);

		return new ResponseEntity<>(new ResponseForm<>("add to cart successfully", true), HttpStatus.OK);
	}

	@PutMapping("/update")
	ResponseEntity<?> updateCart(@RequestBody UpdateCartForm updateCartForm) {
		CartItem cartItem = cartItemService.findCartItemById(updateCartForm.getCartItemId());

		cartItem.setQuantity(updateCartForm.getQuantity());

		cartItemService.saveCartItem(cartItem);

		return new ResponseEntity<>(new ResponseForm<>("add to cart successfully", true), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	ResponseEntity<?> updateCart(@PathVariable("id") Long id) {
		cartItemService.deleteCartItemById(id);

		return new ResponseEntity<>(new ResponseForm<>("add to cart successfully", true), HttpStatus.OK);
	}
}
