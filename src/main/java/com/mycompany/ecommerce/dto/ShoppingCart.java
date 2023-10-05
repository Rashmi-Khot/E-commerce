package com.mycompany.ecommerce.dto;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
@Entity
public class ShoppingCart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
  
  @Override
	public String toString() {
		return "ShoppingCart [id=" + id + ", customerProducts=" + customerProducts + "]";
	}

public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<CustomerProduct> getCustomerProducts() {
		return customerProducts;
	}

	public void setCustomerProducts(List<CustomerProduct> customerProducts) {
		this.customerProducts = customerProducts;
	}

@OneToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER) 
	List<CustomerProduct> customerProducts;

}
