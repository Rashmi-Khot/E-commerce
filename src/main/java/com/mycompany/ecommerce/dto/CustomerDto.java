package com.mycompany.ecommerce.dto;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;


@Entity
@Component
public class CustomerDto {
	@Id
	@GeneratedValue(generator = "customer_id")
	@SequenceGenerator(name="customer_id",initialValue = 111001,allocationSize = 1,sequenceName ="customer_id" )
	private int id;
	@Size(min = 5,message = "Atleast enter 5 character")
	private String name;
	@Email(message = "Email format is not correct")
	private String email;
	//@Size(max = 10,message = "number should be 10 digits")
	private long mobile;
	private String password;
	private String gender;
	private Date dob;
	private boolean status;
	private int otp;
	
	@OneToOne(cascade=CascadeType.ALL,fetch = FetchType.EAGER) 
	ShoppingCart cart;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	List<ShoppingOrder> orders;

	
	public List<ShoppingOrder> getOrders() {
		return orders;
	}
	public void setOrders(List<ShoppingOrder> orders) {
		this.orders = orders;
	}
	public ShoppingCart getCart() {
		return cart;
	}
	public void setCart(ShoppingCart cart) {
		this.cart = cart;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getMobile() {
		return mobile;
	}
	public void setMobile(long mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public int getOtp() {
		return otp;
	}
	public void setOtp(int otp) {
		this.otp = otp;
	}
	@Override
	public String toString() {
		return "CustomerDto [id=" + id + ", name=" + name + ", email=" + email + ", mobile=" + mobile + ", password="
				+ password + ", gender=" + gender + ", dob=" + dob + ", status=" + status + ", otp=" + otp + "]";
	}
	
	
	
	}