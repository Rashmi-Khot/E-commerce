package com.mycompany.ecommerce.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.ecommerce.dto.CustomerDto;
import com.mycompany.ecommerce.dto.MerchantDto;
import com.mycompany.ecommerce.repository.CustomerRepository;

@Component
public class CustomerDao {

	@Autowired
	CustomerRepository customerRepository;
	
	public CustomerDto fetchByEmail(String email) {
		return customerRepository.findByEmail(email);
	}
	public CustomerDto fetchByMobile(long mobile) {
		return customerRepository.findByMobile(mobile);
	}
	
	public CustomerDto save(CustomerDto customerDto) {
		return customerRepository.save(customerDto);
	}
	 public CustomerDto fetchById(int id) {
		 return customerRepository.findById(id).orElse(null);
	 }
}
