package com.supermarket.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.supermarket.api.entity.Category;

@Repository
public interface CategoryDAO extends JpaRepository<Category, Long> {
	Category findFirstByName(String name);
}
