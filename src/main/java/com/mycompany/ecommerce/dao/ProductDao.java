package com.mycompany.ecommerce.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

import com.mycompany.ecommerce.dto.CustomerProduct;
import com.mycompany.ecommerce.dto.PaymentDetails;
import com.mycompany.ecommerce.dto.Product;
import com.mycompany.ecommerce.dto.ShoppingCart;
import com.mycompany.ecommerce.repository.CartRepository;
import com.mycompany.ecommerce.repository.CustomerProductRepository;
import com.mycompany.ecommerce.repository.PaymentDetailsRepository;
import com.mycompany.ecommerce.repository.ProductRepository;


import jakarta.validation.Valid;
@Repository
public class ProductDao {
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CustomerProductRepository customerProductRepository;
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	PaymentDetailsRepository detailsRepository;
	
	public Product findById(int id) {
	return	productRepository.findById(id).orElse(null);
	}
	
	public void delete(Product product) {
		productRepository.delete(product);
	}

	public void save(@Valid Product Product) {
		productRepository.save(Product);
		
	}

	public  List<Product> fetchAllProducts() {
	return	productRepository.findAll();
	}

	public List<Product> fetchApprovedProducts() {
		return productRepository.findByApprovedTrue();
	}
	
	public void save(CustomerProduct product) {
		customerProductRepository.save(product);
	}
	
	public void save(ShoppingCart cart) {
		cartRepository.save(cart);
	}
	
	public void delete(CustomerProduct customerProduct) {
		customerProductRepository.delete(customerProduct);
	}

	public PaymentDetails saveDetails(PaymentDetails details) {
		return detailsRepository.save(details);
			}
	
	public PaymentDetails find(int id)
	{
		return detailsRepository.findById(id).orElse(null);
	}


	

	
		

}
