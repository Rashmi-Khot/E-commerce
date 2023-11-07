package com.mycompany.ecommerce.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.ecommerce.dao.CustomerDao;
import com.mycompany.ecommerce.dao.ProductDao;
import com.mycompany.ecommerce.dto.CustomerDto;
import com.mycompany.ecommerce.dto.CustomerProduct;
import com.mycompany.ecommerce.dto.MerchantDto;
import com.mycompany.ecommerce.dto.PaymentDetails;
import com.mycompany.ecommerce.dto.Product;
import com.mycompany.ecommerce.dto.ShoppingCart;
import com.mycompany.ecommerce.dto.ShoppingOrder;
import com.mycompany.ecommerce.helper.Aes;
import com.mycompany.ecommerce.helper.CustomerHelper;
import com.mycompany.ecommerce.helper.LoginHelper;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import jakarta.servlet.http.HttpSession;

@Service
public class CustomerService {
	@Autowired
	CustomerDao customerDao;
	
	@Autowired
	CustomerHelper customerHelper;
	
	@Autowired
	ProductDao productDao;
	

		
	@Autowired
	ShoppingOrder order;
	
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
			if(customerDto.getCart()!=null)
			{
				if(customerDto.getCart().getCustomerProducts()!=null);
			cartitem=customerDto.getCart().getCustomerProducts();
			}
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
	public String viewCart(ModelMap modelMap, CustomerDto customerDto, HttpSession session) throws RazorpayException {
		 ShoppingCart cart=customerDto.getCart();
		 if(cart==null) {
			 modelMap.put("neg", "no items in cart");
			 return fetchProducts(modelMap, customerDao.fetchById(customerDto.getId()));
		 }
		 else {
			 List<CustomerProduct> list=cart.getCustomerProducts();
			 if(list==null || list.isEmpty()) {
				 modelMap.put("neg", "no items in cart");
				 return fetchProducts(modelMap, customerDao.fetchById(customerDto.getId()));
			 }
			 else {
				 boolean flag=true;
				 for(CustomerProduct customerProduct:list) {
					 if(customerProduct.getQuantity()>0) {
						 flag=false;
						 break;
					 }
				 }
					 if(flag) {
						 modelMap.put("neg", "no items in cart");
						 return fetchProducts(modelMap, customerDao.fetchById(customerDto.getId()));
					 }
					 else {
						 double amount=0;
						 for(CustomerProduct customerProduct1:list) {
							 amount=amount+customerProduct1.getPrice();
						 }
						 JSONObject object = new JSONObject();
							object.put("amount", (int) (amount * 100));
							object.put("currency", "INR");
							
							RazorpayClient client = new RazorpayClient("rzp_test_u7jn1DIfYvm8Ef", "Alz3ROWEhbC0SNTR2JEd0Mpc");
							Order order=client.orders.create(object);
							
							PaymentDetails details=new PaymentDetails();

							details.setAmount(order.get("amount").toString());
							details.setCurrency(order.get("currency").toString());
							details.setPaymentId(null);
							details.setOrderId(order.get("id").toString());
							details.setStatus(order.get("status"));
							details.setKeyDetails("rzp_test_u7jn1DIfYvm8Ef");
							
							modelMap.put("details", productDao.saveDetails(details));
							modelMap.put("items", list);
							modelMap.put("customer", customerDto);
						 return "viewcart";
					 }
				 }
			 }
		 }
	public String checkPayment(int id, CustomerDto customerDto, String razorpay_payment_id, HttpSession session,
			ModelMap map) throws RazorpayException {
		PaymentDetails details = productDao.find(id);
		if (details == null) {
			map.put("neg", "Something went wrong");
			return "main";
		} else {
			if (razorpay_payment_id != null) {
				details.setStatus("success");
				details.setTime(LocalDateTime.now());
				details.setPaymentId(razorpay_payment_id);
				productDao.saveDetails(details);

				order.setDateTime(LocalDateTime.now());
				order.setPayment_id(razorpay_payment_id);
				order.setPrice(Double.parseDouble(details.getAmount()) / 100);
				order.setCustomerProducts(customerDto.getCart().getCustomerProducts());

				List<ShoppingOrder> list = customerDto.getOrders();
				if (list == null)
					list = new ArrayList<ShoppingOrder>();
				list.add(order);

				customerDto.setOrders(list);
				customerDto.setCart(null);
				customerDao.save(customerDto);
				
				session.setAttribute("customerDto", customerDao.fetchById(customerDto.getId()));

				map.put("pos", "Payment Done, Ordere Placed");
				return "customerhome";

			} else {
				map.put("neg", "Payment Not Done");
				return viewCart(map, customerDto, session);
			}
		}
			}
	

	
	public String fetchOrders(ModelMap modelMap, CustomerDto customerDto) {
		List<ShoppingOrder> orders = customerDto.getOrders();
		if(orders==null || orders.isEmpty())
		{
			modelMap.put("neg", "No Orders Found");
			return "customerhome";
		}
		else {
			modelMap.put("orders", orders);
			return "customerorders";
		}
	}



			
		
	

}
