package com.mycompany.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mycompany.ecommerce.dto.MerchantDto;
@Repository
public interface MerchantRepository extends JpaRepository<MerchantDto, Integer>{
	MerchantDto findByEmail(String email);
	MerchantDto findByMobile(long mobile);



}
