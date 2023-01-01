package com.supermarket.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.supermarket.api.dao.CategoryDAO;
import com.supermarket.api.dao.ProductDAO;
import com.supermarket.api.entity.Category;
import com.supermarket.api.entity.Product;
import com.supermarket.api.form.product.CreateProductForm;
import com.supermarket.api.form.product.UpdateProductForm;
import com.supermarket.api.service.Constant;
import com.supermarket.api.service.StorageService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductDAO productDAO;

	@Autowired
	CategoryDAO categoryDAO;

	@Autowired
	StorageService storageService;

	@PostMapping(value = "/myUpload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String myUpload(@RequestParam MultipartFile file) {
		return storageService.uploadImage(file, file.getOriginalFilename());
	}

	@PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String createProduct(@RequestParam String proName, @RequestParam MultipartFile file,
			@RequestParam Long price, @RequestParam Integer quantity, @RequestParam Long categoryId) throws Exception {

		System.out.println(proName);
		System.out.println(price);
		System.out.println(quantity);
		System.out.println(categoryId);
		System.out.println(file);

		// upload image to server
		String img = storageService.uploadImage(file, proName + ".png");

		Product productNew = new Product();

		productNew.setName(proName);
		productNew.setImg(img);
		productNew.setPrice(price);
		productNew.setQuantity(quantity);

		productNew.setCreatedDate(Constant.getCurrentDateTime());
		productNew.setModifiedDate(null);
		productNew.setStatus(Constant.ACTIVE_STATUS);

		Category category = categoryDAO.findById(categoryId).orElse(null);

		if (category == null) {
			return "category not found";
		}

		productNew.setCategory(category);

		productDAO.save(productNew);

		return "Product Created";
	}

	@GetMapping("/list")
	public List<Product> getAll() {
		return productDAO.findAll();
	}

	@GetMapping("/byId")
	public Product getById(@PathVariable(value = "proId") Long proId) {
		return productDAO.findById(proId).orElse(null);
	}

	@GetMapping("/bycatId")
	public List<Product> getAllByCatId(@PathVariable(value = "categoryId") Long categoryId) {
		return productDAO.findByCategoryId(categoryId);
	}

	@PutMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String updateProduct(@RequestBody UpdateProductForm updateProductForm) {

		Product productUpdate = productDAO.findById(updateProductForm.getId()).orElse(null);

		if (productUpdate == null) {
			return "product not found";
		}

		productUpdate.setName(updateProductForm.getProName());

		if (updateProductForm.getFile() != null) {
			StorageService.deleteImage(productUpdate.getImg());
			String img = storageService.uploadImage(updateProductForm.getFile(), productUpdate.getName());
			productUpdate.setImg(img);
		}

		productUpdate.setPrice(updateProductForm.getPrice());

		productUpdate.setQuantity(updateProductForm.getQuantity());

		productUpdate.setModifiedDate(Constant.getCurrentDateTime());

		if (updateProductForm.getCategoryId() > 0) {
			Category category = categoryDAO.findById(updateProductForm.getCategoryId()).orElse(null);
			if (category == null) {
				return "invalid category";
			}
			productUpdate.setCategory(category);
		}

		productUpdate.setStatus(updateProductForm.getStatus());

		productDAO.save(productUpdate);

		return "Product Updated";
	}

	@DeleteMapping("/delete")
	public String deleteProduct(@PathVariable(value = "proId") Long proId) {

		Product productDelete = productDAO.findById(proId).orElse(null);

		if (productDelete == null) {
			return "product not found";
		}

		StorageService.deleteImage(productDelete.getImg());

		productDAO.deleteById(productDelete.getId());

		return "product Deleted";
	}
}
