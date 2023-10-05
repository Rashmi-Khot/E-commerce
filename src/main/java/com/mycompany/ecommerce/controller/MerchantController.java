package com.mycompany.ecommerce.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.ecommerce.dao.MerchantDao;
import com.mycompany.ecommerce.dto.MerchantDto;
import com.mycompany.ecommerce.dto.Product;
import com.mycompany.ecommerce.helper.LoginHelper;
import com.mycompany.ecommerce.service.MerchantService;

import jakarta.mail.Multipart;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
@Controller
public class MerchantController {
	
	@Autowired
	MerchantService merchantService;
	
	
	@Autowired
	MerchantDto merchantDto;
	
	@Autowired
	Product product;
	
	@GetMapping("/merchant")
	public String loadHome() {
		return "merchant";
	}
	
	@GetMapping("/merchant/home")
	public String loadHome(HttpSession session,ModelMap map) {
		if(session .getAttribute("merchantDto")!=null) {
			return "merchanthome";
		}
		else {
			map.put("neg", "invalid session");
			return "merchant";
		}
	}
	@GetMapping("/merchant/signup")
	public String loadSignup(ModelMap map) {
		map.put("merchantDto", merchantDto);
		return "merchantsignup";
	}
	

	
	
	@PostMapping("/merchant/signup")
    public String signup(@Valid MerchantDto merchantDto, BindingResult result,ModelMap modelmap) {
		if(result.hasErrors())
			return "merchantsignup";
		else
		return  merchantService.signup(merchantDto,modelmap);
	}
	
	@PostMapping("/merchant/varify-otp")
	public String verify(@RequestParam int otp,@RequestParam int id,ModelMap modelMap) {
		return merchantService.varifyOtp(id, otp, modelMap);
	}
	
	@PostMapping("/merchant/login")
		public String login(LoginHelper helper,ModelMap map,HttpSession session) {
		return merchantService.login(helper, map,session);
			
		}
	
	@GetMapping("/merchant/add-product")
	public String addProduct(ModelMap map,HttpSession session) {  
		MerchantDto merchantDto=(MerchantDto) session.getAttribute("merchantDto");
		if(merchantDto!=null) {
		map.put("product", product);
		return "AddProduct";
		}
		else {
			map.put("neg", "invalid session");
			return "main";
		}
	}
	
	@PostMapping("/merchant/add-product")
	public String addProduct(@Valid Product product, BindingResult result, @RequestParam MultipartFile pic,
			ModelMap map, HttpSession session) throws IOException {
		MerchantDto merchantDto = (MerchantDto) session.getAttribute("merchantDto");
		if (merchantDto != null) {
			if (result.hasErrors())
				return "addproduct";
			else {
				return merchantService.addProduct(product, pic, map, merchantDto, session);
			}
		} else {
			map.put("neg", "Invalid Session");
			return "main";
		}
	}
	
	
	@GetMapping("/merchant/fetch-products")
	public String fetchProducts(HttpSession session,ModelMap map) {
	MerchantDto merchantDto=(MerchantDto) session.getAttribute("merchantDto");
	if(merchantDto!=null) {
		return merchantService.fetchProducts(merchantDto,map);
	}
	else {
		map.put("neg", "invalid session");
		return "main";
	}
	
		
	}
	
	@GetMapping("/merchant/delete/{id}")
	public String deleteProduct(@PathVariable int id,HttpSession session,ModelMap modelMap) {
		MerchantDto merchantDto=(MerchantDto) session.getAttribute("merchantDto");
		if(merchantDto!=null) {
			return merchantService.delete(id,modelMap,merchantDto,session);
		}
		else {
			modelMap.put("neg", "invalid session");
			return "main";
		}
	}
	
	
  @GetMapping("/merchant/edit/{id}")
  public String editProduct(@PathVariable int id ,ModelMap modelMap,HttpSession session) {
		MerchantDto merchantDto=(MerchantDto) session.getAttribute("merchantDto");
		if(merchantDto!=null) {
			return merchantService.edit(id,modelMap);
		}
		else {
			modelMap.put("neg", "invalid session");
			return "main";
		}
	}
  
  @PostMapping("/merchant/update-product")
	public String updateProduct(@Valid Product product,BindingResult result,@RequestParam MultipartFile pic, ModelMap map,HttpSession session) throws IOException {  //modelmap is uesd to recive the msg from fronted
		MerchantDto merchantDto=(MerchantDto) session.getAttribute("merchantDto");
		if(merchantDto!=null) {
			if(result.hasErrors()) 
	
		return "editproduct";
			else {
//				byte[] picture=new byte[pic.getInputStream().available()];
//				pic.getInputStream().read(picture);
//				
//				product.setPicture(picture);
//				System.out.println(product);
				return merchantService.editProduct(product,pic,map,merchantDto,session);  //productrepository.fetchal
			}
		}
		else {
			map.put("neg", "invalid session");
			return "main";
		}
	}
  
  
  

}