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
import com.mycompany.ecommerce.dao.ProductDao;
import com.mycompany.ecommerce.dto.CustomerDto;
import com.mycompany.ecommerce.dto.CustomerProduct;
import com.mycompany.ecommerce.dto.MerchantDto;
import com.mycompany.ecommerce.dto.Product;
import com.mycompany.ecommerce.dto.ShoppingCart;
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
	
	@Autowired
	ProductDao productDao;
	
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
	
	public String fetchProducts(ModelMap modelMap, CustomerDto customerDto) {
		List<Product> list = productDao.fetchApprovedProducts();
		if (list.isEmpty()) {
			modelMap.put("neg", "No Products Available");
			return "customerhome";
		} else {
			
			List<CustomerProduct> cartitem=null;
			if(customerDto.getCart()!=null && customerDto.getCart().getCustomerProducts()!=null);
			cartitem=customerDto.getCart().getCustomerProducts();
			modelMap.put("cartitems", cartitem);
		
			modelMap.put("list", list);
			return "customerproducts";
		}
	}
	public String addToCart(int id, HttpSession session, CustomerDto customerDto, ModelMap modelMap) {
		
		Product product = productDao.findById(id);
		if (product != null) {
			if (product.getStock() > 0) {
				ShoppingCart cart = customerDto.getCart();
				if (cart == null) {
					cart = new ShoppingCart();
					customerDto.setCart(cart);
				}
				List<CustomerProduct> customerProducts = cart.getCustomerProducts();
				if (customerProducts == null) {
					customerProducts = new ArrayList<CustomerProduct>();
				}

				boolean flag = true;
				for (CustomerProduct customerProduct : customerProducts) {
					if (customerProduct.getName().equals(product.getName())) {
						customerProduct.setQuantity(customerProduct.getQuantity() + 1);
						customerProduct.setPrice(customerProduct.getPrice() + product.getPrice());
						flag = false;
						break;
					}
				}
				if (flag) {
					CustomerProduct customerProduct = new CustomerProduct();
					customerProduct.setName(product.getName());
					customerProduct.setCategory(product.getCategory());
					customerProduct.setPicture(product.getPicture());
					customerProduct.setPrice(product.getPrice());
					customerProduct.setQuantity(1);
					customerProducts.add(customerProduct);
					cart.setCustomerProducts(customerProducts);
				}
				
				
				customerDao.save(customerDto);
				session.setAttribute("customerDto", customerDao.fetchById(customerDto.getId()));
				product.setStock(product.getStock() - 1);
				productDao.save(product);

				modelMap.put("pos", "Item Added to Cart");
				
			} else {
				modelMap.put("neg", "Out of Stock");
			}
			return fetchProducts(modelMap, customerDto);
		} else {
			modelMap.put("neg", "Something went Wrong");
			return "main";
		}
	}
	
	public String removeFromCart(int id, HttpSession session, CustomerDto customerDto, ModelMap modelMap) {
		Product product = productDao.findById(id);
		if (product != null) {
			ShoppingCart cart = customerDto.getCart();
			if (cart == null) {
				modelMap.put("neg", "No Items in Cart");
				return fetchProducts(modelMap, customerDao.fetchById(customerDto.getId()));
			} else {
				List<CustomerProduct> list = cart.getCustomerProducts();
				if (list == null) {
					modelMap.put("neg", "No Items in Cart");
					return fetchProducts(modelMap, customerDao.fetchById(customerDto.getId()));
				} else {
					CustomerProduct customerProduct = null;
					for (CustomerProduct customerProduct2 : list) {
						if (product.getName().equals(customerProduct2.getName())) {
							customerProduct = customerProduct2;
							break;
						}
					}
					if (customerProduct == null) {
						modelMap.put("neg", "No Items in Cart");
						return fetchProducts(modelMap, customerDao.fetchById(customerDto.getId()));
					} else {
						if (customerProduct.getQuantity() > 1) {
							customerProduct.setQuantity(customerProduct.getQuantity() - 1);
							customerProduct.setPrice(customerProduct.getPrice() - product.getPrice());
							product.setStock(product.getStock() + 1);
							productDao.save(product);
							productDao.save(customerProduct);
						} else {
							list.remove(customerProduct);
							product.setStock(product.getStock() + 1);
							productDao.save(product);
							productDao.save(cart);
						}
						modelMap.put("pos", "Item removed from Cart");
						session.setAttribute("customerDto", customerDao.fetchById(customerDto.getId()));
						return fetchProducts(modelMap, customerDao.fetchById(customerDto.getId()));

					}
				}
			}
		} else {
			modelMap.put("neg", "Something went Wrong");
			return "Main";
		}
	}



			
		
	

}
