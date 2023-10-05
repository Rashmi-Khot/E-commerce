package com.mycompany.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.mycompany.ecommerce.helper.LoginHelper;
import com.mycompany.ecommerce.service.AdminService;

import jakarta.servlet.http.HttpSession;
@Controller
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	
	@GetMapping("/admin")
	public String loadHome() {
		return "admin";
	}
	
	@PostMapping("/admin/login")
	public String login(LoginHelper helper,ModelMap map,HttpSession session) {
		return adminService.login(helper,map,session);
		
	}
	@GetMapping("/admin/fetch-products")
	public String fetchProducts(HttpSession session,ModelMap modelMap) {
		String admin=(String) session.getAttribute("admin");
		if(admin!=null) {
			return  adminService.fetchProducts(modelMap);
		}
		else {
			modelMap.put("neg", "invalid session");
			return "main";
		}
	}
	@GetMapping("/admin/change-status/{id}")
	public String  changeStatus(@PathVariable int id,HttpSession session,ModelMap map) {
		String  admin=(String) session.getAttribute("admin");
		if(admin!=null) {
		return	adminService.changeProductStatus(id,map);
		}
		else {
			map.put("neg", "invalid session");
			return "main";
		}
	}
	
	@GetMapping("/admin/fetch-merchants")
	public String fetchMerchants(HttpSession session, ModelMap modelMap) {
		String admin =(String) session.getAttribute("admin");
		if (admin != null) {
			return adminService.fetchMerchants(modelMap);
		} else {
			modelMap.put("neg", "Invalid Session");
			return "main";
		}
	}
	
	@GetMapping("/admin/fetch-customers")
	public String fetchCustomers(HttpSession session, ModelMap modelMap) {
		String admin =(String) session.getAttribute("admin");
		if (admin != null) {
			return adminService.fetchCustomers(modelMap);
		} else {
			modelMap.put("neg", "Invalid Session");
			return "main";
		}
	}
	
		

}
