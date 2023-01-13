package com.supermarket.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supermarket.api.entity.Category;
import com.supermarket.api.form.CreateCategoryForm;
import com.supermarket.api.form.UpdateCategoryForm;
import com.supermarket.api.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	@PostMapping("/create")
	public String create(@RequestBody CreateCategoryForm createCategoryForm) {
		return categoryService.CreateCategory(createCategoryForm.getName());
	}

	@GetMapping("/list")
	public List<Category> getAll() {
		return categoryService.getAllCategory();
	}

	@GetMapping("/byId/{id}")
	public Category get(@PathVariable(value = "id") Long id) {
		return categoryService.findCategory(id);
	}


	
}
