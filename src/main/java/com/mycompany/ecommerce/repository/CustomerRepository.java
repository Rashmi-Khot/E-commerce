package com.mycompany.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mycompany.ecommerce.dto.CustomerDto;
import com.mycompany.ecommerce.dto.MerchantDto;
@Repository
public interface CustomerRepository extends JpaRepository<CustomerDto, Integer>{
	CustomerDto findByEmail(String email);
	CustomerDto findByMobile(long mobile);

}
