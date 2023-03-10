package com.supermarket.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.supermarket.api.dao.ProductDAO;
import com.supermarket.api.entity.Category;
import com.supermarket.api.entity.Product;
import com.supermarket.api.exception.NotFoundException;
import com.supermarket.api.form.ResponseForm;
import com.supermarket.api.service.GlobalService.Constant;
import com.supermarket.api.service.GlobalService.StorageService;

@Service
public class ProductService {

	@Autowired
	ProductDAO productDAO;

	@Autowired
	StorageService storageService;

	public ResponseEntity<?> CreateProduct(String proName, String imgUrl, Long price, Integer quantity,
			Category category) {
		Product productNew = new Product();

		productNew.setName(proName);
		productNew.setImg(imgUrl);
		productNew.setPrice(price);
		productNew.setQuantity(quantity);

		productNew.setCreatedDate(Constant.getCurrentDateTime());
		productNew.setModifiedDate(null);
		productNew.setStatus(Constant.ACTIVE_STATUS);

		productNew.setCategory(category);

		productDAO.save(productNew);

		return new ResponseEntity<>(new ResponseForm<>("Product Created", true), HttpStatus.OK);
	}

	public ResponseEntity<?> UpdateProduct(Product productUpdate, String proName, Long price, Integer quantity,
			Integer status) {
		productUpdate.setName(proName);
		productUpdate.setPrice(price);
		productUpdate.setQuantity(quantity);

		productUpdate.setModifiedDate(Constant.getCurrentDateTime());
		productUpdate.setStatus(status);

		productDAO.save(productUpdate);

		return new ResponseEntity<>(new ResponseForm<>("Product Updated", true), HttpStatus.OK);
	}

	public String UploadImgProduct(MultipartFile file, String proName) {
		return storageService.uploadImage(file, proName);
	}

	public void deleteProductImg(String imgUrl) {
		storageService.deleteImage(imgUrl);
	}

	public List<Product> findAll() {
		return productDAO.findAll();
	}

	public Product findProduct(Long id) {
		Product findProduct = productDAO.findById(id).orElse(null);
		if (findProduct == null) {
			throw new NotFoundException("There is no product with id = " + id);
		}
		return findProduct;
	}

	public List<Product> findProductByCategoryId(Long categoryId) {
		return productDAO.findAllByCategoryId(categoryId);
	}

	public List<Product> findProductByName(String name) {
		return productDAO.findAllByNameLikeIgnoreCase(name);
	}

	public ResponseEntity<?> deleteProductById(Long id) {
		Product productDelete = this.findProduct(id);

		storageService.deleteImage(productDelete.getImg());
		productDAO.deleteById(productDelete.getId());

		return new ResponseEntity<>(new ResponseForm<>("Product Deleted", true), HttpStatus.OK);
	}
}
