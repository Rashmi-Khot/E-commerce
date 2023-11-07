package com.mycompany.ecommerce.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.ecommerce.dto.CustomerDto;
import com.mycompany.ecommerce.dto.MerchantDto;
import com.mycompany.ecommerce.dto.Product;
import com.mycompany.ecommerce.helper.LoginHelper;
import com.mycompany.ecommerce.service.CustomerService;
import com.razorpay.RazorpayException;

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
	
	
	@GetMapping("/customer/fetch-product")
	public String fetchProducts(HttpSession session,ModelMap map) {
	CustomerDto customerDto=(CustomerDto) session.getAttribute("customerDto");
	if(customerDto!=null) {
		return customerService.fetchProducts(map, customerDto);
	}
	else {
		map.put("neg", "invalid session");
		return "main";
	}
	
		
	}
	
	@GetMapping("/customer/cart-add/{id}")
	public String addToCart(@PathVariable int id, HttpSession session,ModelMap modelMap) {
		CustomerDto customerDto=(CustomerDto) session.getAttribute("customerDto");
		if(customerDto!=null) {
			return customerService.addToCart(id,session,customerDto,modelMap);
		}
		else {
			modelMap.put("neg", "invalid session");
			return "main";
		}
		
	}
	@GetMapping("customer/cart-remove/{id}")
	public String removeFromCart(@PathVariable int id,HttpSession session, ModelMap modelMap)
	{
		CustomerDto customerDto =(CustomerDto) session.getAttribute("customerDto");
		if (customerDto != null) {
			return customerService.removeFromCart(id,session,customerDto,modelMap);
		} else {
			modelMap.put("neg", "Invalid Session");
			return "main";
		}
	}
	
	@GetMapping("/customer/cart-view")
	public String viewCart(HttpSession session,ModelMap modelMap) throws RazorpayException {
		CustomerDto customerDto=(CustomerDto) session.getAttribute("customerDto");
		if(customerDto!=null) {
			return customerService.viewCart(modelMap, customerDto,session);
		}
		else {
			modelMap.put("neg", "invalid session");
			return "main";
		}
		
	}

	
	@PostMapping("/customer/payment/{id}")
	public String payment(@PathVariable int id,@RequestParam String razorpay_payment_id,HttpSession session,ModelMap map) throws RazorpayException {
		CustomerDto customerDto = (CustomerDto) session.getAttribute("customerDto");
		if (customerDto != null) {
			return customerService.checkPayment(id,customerDto,razorpay_payment_id,session,map);
		} else {
			map.put("neg", "Invalid Session");
			return "main";
		}
		
	}
	
	@GetMapping("/customer/fetch-orders")
	public String fetchOrders(HttpSession session, ModelMap modelMap) {
		CustomerDto customerDto = (CustomerDto) session.getAttribute("customerDto");
		if (customerDto != null) {
			return customerService.fetchOrders(modelMap, customerDto);
		} else {
			modelMap.put("neg", "Invalid Session");
			return "main";
		}
	}
	
	
	
	
}

