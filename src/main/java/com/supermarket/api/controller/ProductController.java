package com.supermarket.api.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.supermarket.api.entity.Category;
import com.supermarket.api.entity.Product;
import com.supermarket.api.form.ResponseForm;
import com.supermarket.api.service.CategoryService;
import com.supermarket.api.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductService productService;

	@Autowired
	CategoryService categoryService;

	@PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@RequestParam String proName, @RequestParam MultipartFile file,
			@RequestParam Long price, @RequestParam Integer quantity, @RequestParam Long categoryId) throws Exception {

		// upload image to server
		String imgUrlString = productService.UploadImgProduct(file, proName);

		// find category
		Category category = categoryService.findCategory(categoryId);

		return productService.CreateProduct(proName, imgUrlString, price, quantity, category);
	}

	@GetMapping("/list")
	public ResponseEntity<?> getAll() {
		List<Product> products = productService.findAll();

		ResponseForm<List<Product>> responseForm = new ResponseForm<>();
		responseForm.setData(products);
		responseForm.setMessage("get Products successfully");
		responseForm.setResult(true);

		return new ResponseEntity<>(responseForm, HttpStatus.OK);
	}

	@GetMapping("/byId/{id}")
	public ResponseEntity<?> get(@PathVariable(value = "id") Long id) {

		Product product = productService.findProduct(id);

		ResponseForm<Product> responseForm = new ResponseForm<>();
		responseForm.setData(product);
		responseForm.setMessage("get Product info successfully");
		responseForm.setResult(true);

		return new ResponseEntity<>(responseForm, HttpStatus.OK);
	}

	@GetMapping("/bycatId/{id}")
	public ResponseEntity<?> getAllByCatId(@PathVariable(value = "id") Long id) {

		List<Product> products = productService.findProductByCategoryId(id);

		ResponseForm<List<Product>> responseForm = new ResponseForm<>();

		responseForm.setData(products);
		responseForm.setMessage("get Product info successfully");
		responseForm.setResult(true);

		return new ResponseEntity<>(responseForm, HttpStatus.OK);
	}

	@GetMapping("/byName")
	public ResponseEntity<?> getAllByName(@RequestParam(value = "name") String name) {

		List<Product> products = productService.findProductByName(name);

		ResponseForm<List<Product>> responseForm = new ResponseForm<>();

		responseForm.setData(products);
		responseForm.setMessage("get Product info successfully");
		responseForm.setResult(true);

		return new ResponseEntity<>(responseForm, HttpStatus.OK);
	}

	@PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> update(@Nullable @RequestParam String proName, @Nullable @RequestParam MultipartFile file,
			@Nullable @RequestParam Long price, @Nullable @RequestParam Integer quantity,
			@Nullable @RequestParam Long categoryId, @Nullable @RequestParam Integer status, @RequestParam Long proId)
			throws IOException {

		Product productUpdate = productService.findProduct(proId);

		if (file != null) {
			productService.deleteProductImg(productUpdate.getImg());
			String img = productService.UploadImgProduct(file, productUpdate.getName());
			productUpdate.setImg(img);
		}

		if (categoryId != null) {
			Category category = categoryService.findCategory(categoryId);
			productUpdate.setCategory(category);
		}

		return productService.UpdateProduct(productUpdate, proName, price, quantity, status);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		return productService.deleteProductById(id);
	}
}
