package com.mycompany.ecommerce.dao;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.ecommerce.dto.MerchantDto;
import com.mycompany.ecommerce.repository.MerchantRepository;

@Component
public class MerchantDao {
	@Autowired
	MerchantRepository merchantRepository;
	
	public MerchantDto fetchByEmail(String email) {
		return merchantRepository.findByEmail(email);
	}
	public MerchantDto fetchByMobile(long mobile) {
		return merchantRepository.findByMobile(mobile);
	}
	
	public MerchantDto save(MerchantDto merchantDto) {
		return merchantRepository.save(merchantDto);
	}
	 public MerchantDto fetchById(int id) {
		 return merchantRepository.findById(id).orElse(null);
	 }
	public List<MerchantDto> fetchAllMerchants() {
		return merchantRepository.findAll();
	}
	public MerchantDto findById(int id) {
		
		return  merchantRepository.findById(id).orElse(null);
	}
	
	

	
}
