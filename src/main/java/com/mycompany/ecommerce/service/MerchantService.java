package com.mycompany.ecommerce.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.ecommerce.dao.MerchantDao;
import com.mycompany.ecommerce.dao.ProductDao;
import com.mycompany.ecommerce.dto.MerchantDto;
import com.mycompany.ecommerce.dto.Product;
import com.mycompany.ecommerce.helper.Aes;
import com.mycompany.ecommerce.helper.LoginHelper;
import com.mycompany.ecommerce.helper.MailHelper;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
@Service
public class MerchantService {
	@Autowired
	MerchantDao merchantDao;
	
	@Autowired
	ProductDao productDao;
	
	@Autowired
	MailHelper mailHelper;
	
	public String signup(MerchantDto merchantDto,ModelMap modelmap) {
		MerchantDto merchantDto1=merchantDao.fetchByEmail(merchantDto.getEmail());
		MerchantDto merchantDto2=merchantDao.fetchByMobile(merchantDto.getMobile());
		
   if(merchantDto1==null&& merchantDto2==null) {
			int otp=new Random().nextInt(1000,9999);
			merchantDto.setOtp(otp);
			merchantDto.setPassword(Aes.encrypt(merchantDto.getPassword(), "123"));//it will protect previus password,not save directly in database
			merchantDao.save(merchantDto);
			mailHelper.sendOtp(merchantDto);
			modelmap.put("id", merchantDto.getId());
			return "varifyotp";
}
			else {
				if(merchantDto1!=null) {
					
				if(merchantDto1.isStatus()){
				modelmap.put("neg", "Email or phone already exist");
				return "merchantsignup";
				}else {
					if(merchantDto2!=null) {
						mailHelper.sendOtp(merchantDto1);
						modelmap.put("id", merchantDto1.getId());
						return "varifyotp";
					}else {
						modelmap.put("neg", "same email with different number exists");
						return "merchantsignup";
					}
					}
				}else {
					modelmap.put("neg", "phone number already exista");
					return "merchantsignup";
				}
			}
			
		}
	public String varifyOtp(int id,int otp, ModelMap modelMap) {
		MerchantDto merchantDto=merchantDao.fetchById(id);
		if(merchantDto==null) {
			modelMap.put("neg", "something went wrong");
			return "main";
		}
		else {
			if(merchantDto.getOtp()==otp) {
				merchantDto.setStatus(true);
				merchantDao.save(merchantDto);
				modelMap.put("pos", "Account varified succussfully");
				return "merchant";
			}
			else {
				modelMap.put("neg", "otp missmatch");
				modelMap.put("id", id);
				return "varifyotp";
			}
		}
	}
	
	public String login(LoginHelper helper,ModelMap map,HttpSession session) {
		MerchantDto merchantDto=merchantDao.fetchByEmail(helper.getEmail());
		if(merchantDto==null)
		{
			map.put("neg", "InCorrect Email");
			return "merchant";
		}
		else {
			if(Aes.decrypt(merchantDto.getPassword(),"123").equals(helper.getPassword()))
			{
				if(merchantDto.isStatus())
				{
					session.setMaxInactiveInterval(100);
					session.setAttribute("merchantDto", merchantDto);
				map.put("pos", "Login Success");
				return "merchanthome";
				}
				else {
					map.put("neg", "Verify Your OTP First");
					return "merchant";
				}
			}
			else {
				map.put("neg", "InCorrect Password");
				return "merchant";
			}
		}

	}
	public String addProduct( Product product, MultipartFile pic, ModelMap map,MerchantDto merchantDto) throws IOException {
		byte[] picture=new byte[pic.getInputStream().available()];
		pic.getInputStream().read(picture);
		
		product.setPicture(picture);
		List<Product> list=merchantDto.getProducts();
		if(list==null)
		list=new ArrayList<Product>();
		list.add(product);
		merchantDto.setProducts(list);
		
		merchantDao.save(merchantDto);
		map.put("pos", "product addede success");
		return "merchanthome";
	}
	public String fetchProducts(MerchantDto merchantDto, ModelMap map) {
	   
		List<Product> list=merchantDto.getProducts();
		if(list.isEmpty()) {
			map.put("neg", "no products available");
			return "merchanthome";
		}
		else {
			map.put("list", list);
			return "merchantproducts";
		}
	}
	public String delete(int id, ModelMap modelMap, MerchantDto merchantDto) {
		    Product product=productDao.findById(id);
		    if(product==null) {
		    	modelMap.put("neg", "something went wrong");
		    	return "main";
		    }
		    else {
		    	merchantDto.getProducts().remove(product);
		    	merchantDao.save(merchantDto);
		    	productDao.delete(product);
		    	modelMap.put("pos", "product deleted succussfully");
		    	return fetchProducts(merchantDto, modelMap);
		    }
			}
			
		
	

}
