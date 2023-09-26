package com.mycompany.ecommerce.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.ecommerce.dto.CustomerDto;
import com.mycompany.ecommerce.dto.MerchantDto;
import com.mycompany.ecommerce.dto.Product;
import com.mycompany.ecommerce.helper.LoginHelper;
import com.mycompany.ecommerce.service.CustomerService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class CustomerController {
	@Autowired
	CustomerService customerService;
	
	@Autowired
	CustomerDto customerDto;
	
	@Autowired
	Product product;
	
	@GetMapping("/customer")
	public String loadHome() {
		return "customer";
	}
	@GetMapping("/customer/signup")
	public String loadSignup(ModelMap map) {
		map.put("customerDto", customerDto);
		return "customersignup";
	}
	@PostMapping("/customer/signup")
    public String signup(@Valid CustomerDto customerDto, BindingResult result,ModelMap modelmap) {
		if(result.hasErrors())
			return "customersignup";
		else
		return  customerService.signup(customerDto,modelmap);
	}
	
	@PostMapping("/customer/varify-otp1")
	public String verify(@RequestParam int otp,@RequestParam int id,ModelMap modelMap) {
		return customerService.varifyOtp(id, otp, modelMap);
	}
	
	@PostMapping("/customer/login")
	public String login(LoginHelper helper,ModelMap map,HttpSession session) {
	return customerService.login(helper, map,session);
		
	}
	@GetMapping("customer/add-product")
	public String addProduct(ModelMap map,HttpSession session) {
		CustomerDto customerDto=(CustomerDto) session.getAttribute("customerDto");
		if(customerDto!=null) {
			map.put("product", product);
			return "AddProduct";
		}
		else {
			map.put("neg", "invalid session");
			return "main";
		}
	}
	
	
	
}
