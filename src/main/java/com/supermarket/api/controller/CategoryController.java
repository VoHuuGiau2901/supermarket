package com.supermarket.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supermarket.api.dao.CategoryDAO;
import com.supermarket.api.entity.Category;
import com.supermarket.api.form.CreateCategoryForm;
import com.supermarket.api.service.Constant;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	CategoryDAO categoryDAO;

	@PostMapping("/create")
	public String create(@RequestBody CreateCategoryForm createCategoryForm) {
		Category categoryNew = categoryDAO.findFirstByName(createCategoryForm.getName());

		if (categoryNew != null) {
			return "Category Exists";
		}

		categoryNew = new Category();
		categoryNew.setName(createCategoryForm.getName());

		categoryNew.setCreatedDate(Constant.getCurrentDateTime());
		categoryNew.setModifiedDate(null);
		categoryNew.setStatus(Constant.ACTIVE_STATUS);

		categoryDAO.save(categoryNew);
		return "category Created";
	}

	@GetMapping("/list")
	public List<Category> getAll() {
		return categoryDAO.findAll();
	}

	@GetMapping("/byId")
	public Category getById(@PathVariable(value = "id") Long id) {
		return categoryDAO.findById(id).orElse(null);
	}
}
