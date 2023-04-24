package com.thiruacademy.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.thiruacademy.entity.Product;
import com.thiruacademy.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@PostMapping("/save")
	public ResponseEntity<Product> saveProduct(@RequestBody Product product){
		return new ResponseEntity<Product> (productService.saveProduct(product), HttpStatus.CREATED);
	}
	
	@GetMapping("/fetchAll")
	public ResponseEntity<List<Product>> getAllCustomers(){
		return new ResponseEntity<List<Product>> (productService.fetchAllProducts(), HttpStatus.CREATED);
	}
	
	@GetMapping("/fetch/{id}")
	public Product getCustomer(@PathVariable Long id){
		return productService.fetchProduct(id);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Product> patchUpdateCustomer(@PathVariable Long id, @RequestBody Product product) {
		return new ResponseEntity<Product> (productService.updateProduct(id, product), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable Long id){
		return new ResponseEntity<String> (productService.deleteProduct(id), HttpStatus.CREATED);
	}
	

}
