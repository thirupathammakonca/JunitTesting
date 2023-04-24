package com.thiruacademy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiruacademy.entity.Product;
import com.thiruacademy.repository.ProductRepository;
import com.thiruacademy.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	boolean flag;
	@Override
	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}

	@Override
	public List<Product> fetchAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Product fetchProduct(Long id) {
		if(id != null && id != 0) {
			flag = productRepository.existsById(id);
		}
		if(flag) 
			return productRepository.findById(id).get();
		else
			return null;
	}

	@Override
	public Product updateProduct(Long id, Product product) {
        Product existingProduct = productRepository.findById(id).orElse(null);
			existingProduct.setName(product.getName());
			existingProduct.setPrice(product.getPrice());
			existingProduct.setDepartment(product.getDepartment());
            return productRepository.save(existingProduct);
    }

	@Override
	public String deleteProduct(Long id) {
		if(id != null && id != 0) {
			flag = productRepository.existsById(id);
		}
		if(flag) 
			productRepository.deleteById(id);
		return "Product deleted";
	}

}
