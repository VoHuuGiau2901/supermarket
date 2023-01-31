package com.supermarket.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.supermarket.api.dao.CategoryDAO;
import com.supermarket.api.entity.Category;
import com.supermarket.api.exception.DuplicateException;
import com.supermarket.api.exception.NotFoundException;
import com.supermarket.api.form.ResponseForm;
import com.supermarket.api.form.Category.UpdateCategoryForm;
import com.supermarket.api.service.GlobalService.Constant;

@Service
public class CategoryService {
	@Autowired
	CategoryDAO categoryDAO;

	public Category findCategory(Long id) {
		Category category = categoryDAO.findById(id).orElse(null);
		if (category == null) {
			throw new NotFoundException("There is no Category with id = " + id);
		}
		return category;
	}

	public ResponseEntity<?> CreateCategory(String name) {
		Category categoryNew = categoryDAO.findFirstByName(name);

		if (categoryNew != null) {
			throw new DuplicateException("There is one Category exsists with name = " + name);
		}

		categoryNew = new Category();
		categoryNew.setName(name);

		categoryNew.setCreatedDate(Constant.getCurrentDateTime());
		categoryNew.setModifiedDate(null);
		categoryNew.setStatus(Constant.ACTIVE_STATUS);

		categoryDAO.save(categoryNew);

		return new ResponseEntity<>(new ResponseForm<>("Category Created", true), HttpStatus.OK);
	}

	public ResponseEntity<?> UpdateCategory(UpdateCategoryForm updateCategoryForm) {
		Category categoryUpdate = this.findCategory(updateCategoryForm.getId());

		categoryUpdate.setName(updateCategoryForm.getName());

		categoryUpdate.setModifiedDate(Constant.getCurrentDateTime());
		categoryUpdate.setStatus(updateCategoryForm.getStatus());

		categoryDAO.save(categoryUpdate);

		return new ResponseEntity<>(new ResponseForm<>("Category Updated", true), HttpStatus.OK);
	}

	public List<Category> getAllCategory() {
		return categoryDAO.findAll();
	}

	public ResponseEntity<?> deleteCategory(Long id) {
		Category categoryDelete = this.findCategory(id);

		categoryDAO.deleteById(categoryDelete.getId());

		return new ResponseEntity<>(new ResponseForm<>("Category Deleted", true), HttpStatus.OK);
	}
}
