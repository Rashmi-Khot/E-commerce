package com.mycompany.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
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

}
