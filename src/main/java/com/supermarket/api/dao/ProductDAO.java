package com.supermarket.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.supermarket.api.entity.Product;

@Repository
public interface ProductDAO extends JpaRepository<Product, Long> {
	List<Product> findAllByCategoryId(Long categoryId);

	List<Product> findAllByNameLikeIgnoreCase(String name);
}
