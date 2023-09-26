package com.mycompany.ecommerce.dto;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Component
@Entity
@Data
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotEmpty(message="*This is required field")
	private String name;
	@NotNull(message="*This is required field") //for string use notEmpty and for int,double use notNull
	@DecimalMin(value = "1",message="*emter value greater than 1")
	private int stock;
	@NotNull(message="*This is required field")
	@DecimalMin(value = "1", message= "*emter value greater than 1") //atleast one product should be present
    private double price;
	@NotEmpty(message="*This is required field")
	private String category;
	boolean approved;
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")  //this is medium type we can store image uptp 16mb
	byte[] picture;
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
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
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public byte[] getPicture() {
		return picture;
	}
	public void setPicture(byte[] picture) {
		this.picture = picture;
	}
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", stock=" + stock + ", price=" + price + ", category="
				+ category + ", picture=" + Arrays.toString(picture) + "]";
	}
	

}
