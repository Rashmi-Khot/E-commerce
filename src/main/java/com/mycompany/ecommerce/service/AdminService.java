package com.mycompany.ecommerce.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.mycompany.ecommerce.helper.LoginHelper;

import jakarta.servlet.http.HttpSession;

@Service
public class AdminService {

	public String login(LoginHelper helper, ModelMap map, HttpSession session) {
		if(helper.getEmail().equals("admin@jsp.com")) {
			if(helper.getPassword().equals("admin")) {
				session.setAttribute("admin", "admin");
				map.put("pos", "login succuss");
				return "adminhome";
			}
			else {
				map.put("neg", "incorrect password");
				return "admin";
			}
		}
		else {
			map.put("neg", "incorrect email");
			return "admin";
		}
	}

}
