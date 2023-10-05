package com.mycompany.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.mycompany.ecommerce.dao.CustomerDao;
import com.mycompany.ecommerce.dao.MerchantDao;
import com.mycompany.ecommerce.dao.ProductDao;
import com.mycompany.ecommerce.dto.CustomerDto;
import com.mycompany.ecommerce.dto.MerchantDto;
import com.mycompany.ecommerce.dto.Product;
import com.mycompany.ecommerce.helper.LoginHelper;

import jakarta.servlet.http.HttpSession;

@Service
public class AdminService {
	@Autowired
	ProductDao productDao;
	
	@Autowired
	MerchantDao merchantDao;
	
	@Autowired
	CustomerDao customerDao;

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
	
	public String fetchProducts(ModelMap modelMap) {
		List<Product> list = productDao.fetchAllProducts();
		if (list.isEmpty()) {
			modelMap.put("neg", "No Products Available");
			return "adminhome";
		} else {
			modelMap.put("list", list);
			return "adminproducts";
		}
	}
	public String changeProductStatus(int id, ModelMap map) {
		Product product = productDao.findById(id);
		if (product == null) {
			map.put("neg", "Something Went Wrong");
			return "main";
		} else {
			if (product.isApproved())
				product.setApproved(false);
			else
				product.setApproved(true);

			productDao.save(product);
			map.put("pos", "Status Updated Success");
			return fetchProducts(map);
		}
	}

	public String fetchMerchants(ModelMap modelMap) {
		List<MerchantDto> list = merchantDao.fetchAllMerchants();
		if (list.isEmpty()) {
			modelMap.put("neg", "No Merchant Enrolled");
			return "adminhome";
		} else {
			modelMap.put("list", list);
			return "adminmerchants";
		}
	}
	
	public String fetchCustomers(ModelMap modelMap) {
		List<CustomerDto> list = customerDao.fetchAllCustomers();
		if (list.isEmpty()) {
			modelMap.put("neg", "No Customer Enrolled");
			return "adminhome";
		} else {
			modelMap.put("list", list);
			return "admincustomers";
		}
	}


	

	
	
	
}
