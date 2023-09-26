package com.mycompany.ecommerce.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.ecommerce.dao.CustomerDao;
import com.mycompany.ecommerce.dto.CustomerDto;
import com.mycompany.ecommerce.dto.MerchantDto;
import com.mycompany.ecommerce.dto.Product;
import com.mycompany.ecommerce.helper.Aes;
import com.mycompany.ecommerce.helper.CustomerHelper;
import com.mycompany.ecommerce.helper.LoginHelper;

import jakarta.servlet.http.HttpSession;

@Service
public class CustomerService {
	@Autowired
	CustomerDao customerDao;
	
	@Autowired
	CustomerHelper customerHelper;
	
	public String signup(CustomerDto customerDto,ModelMap modelmap) {
		CustomerDto customerDto1=customerDao.fetchByEmail(customerDto.getEmail());
		CustomerDto customerDto2=customerDao.fetchByMobile(customerDto.getMobile());
		
   if(customerDto1==null&& customerDto2==null) {
			int otp= new Random().nextInt(1000,9999);
			customerDto.setOtp(otp);
			customerDto.setPassword(Aes.encrypt(customerDto.getPassword(), "123"));
			customerDao.save(customerDto);
			
			customerHelper.sendOtp(customerDto);
			modelmap.put("id", customerDto.getId());
			return "varifyotp1";
}
			else {
				if(customerDto1!=null) {
					
				if(customerDto1.isStatus()){
				modelmap.put("neg", "Email or phone already exist");
				return "customersignup";
				}else {
					if(customerDto2!=null) {
						customerHelper.sendOtp(customerDto1);
						modelmap.put("id", customerDto1.getId());
						return "varifyotp1";
					}else {
						modelmap.put("neg", "same email with different number exists");
						return "customersignup";
					}
					}
				}else {
					modelmap.put("neg", "phone number already exista");
					return "customersignup";
				}
			}
			
		}
	public String varifyOtp(int id,int otp, ModelMap modelMap) {
		CustomerDto customerDto=customerDao.fetchById(id);
		if(customerDto==null) {
			modelMap.put("neg", "something went wrong");
			return "main";
		}
		else {
			if(customerDto.getOtp()==otp) {
				customerDto.setStatus(true);
				customerDao.save(customerDto);
				modelMap.put("pos", "Account varified succussfully");
				return "customer";
			}
			else {
				modelMap.put("neg", "otp missmatch");
				modelMap.put("id", id);
				return "varifyotp1";
			}
		}
	}
	
	public String login(LoginHelper helper,ModelMap map,HttpSession session) {
		CustomerDto customerDto=customerDao.fetchByEmail(helper.getEmail());
		if(customerDto==null)
		{
			map.put("neg", "InCorrect Email");
			return "customer";
		}
		else {
			if(Aes.decrypt(customerDto.getPassword(),"123").equals(helper.getPassword()))
			{
				if(customerDto.isStatus())
				{
					session.setMaxInactiveInterval(100);
					session.setAttribute("customerDto", customerDto);
				map.put("pos", "Login Success");
				return "customerhome";
				}
				else {
					map.put("neg", "Verify Your OTP First");
					return "customer";
				}
			}
			else {
				map.put("neg", "InCorrect Password");
				return "customer";
			}
		}

	}
				


			
		
	

}
