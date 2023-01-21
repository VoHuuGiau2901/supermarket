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
import org.springframework.web.bind.annotation.RestController;

import com.supermarket.api.entity.Category;
import com.supermarket.api.entity.Product;
import com.supermarket.api.form.CreateCategoryForm;
import com.supermarket.api.form.ResponseForm;
import com.supermarket.api.form.UpdateCategoryForm;
import com.supermarket.api.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody CreateCategoryForm createCategoryForm) {
		return categoryService.CreateCategory(createCategoryForm.getName());
	}

	@GetMapping("/list")
	public ResponseEntity<?> getAll() {
		List<Category> categories = categoryService.getAllCategory();

		ResponseForm<List<Category>> responseForm = new ResponseForm<>();
		responseForm.setData(categories);
		responseForm.setMessage("get Categories successfully");
		responseForm.setResult(true);

		return new ResponseEntity<>(responseForm, HttpStatus.OK);
	}

	@GetMapping("/byId/{id}")
	public ResponseEntity<?> get(@PathVariable(value = "id") Long id) {
		
		Category category = categoryService.findCategory(id);

		ResponseForm<Category> responseForm = new ResponseForm<>();
		responseForm.setData(category);
		responseForm.setMessage("get Categories successfully");
		responseForm.setResult(true);

		return new ResponseEntity<>(responseForm, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody UpdateCategoryForm updateCategoryForm) {
		return categoryService.UpdateCategory(updateCategoryForm);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		return categoryService.deleteCategory(id);
	}
}
