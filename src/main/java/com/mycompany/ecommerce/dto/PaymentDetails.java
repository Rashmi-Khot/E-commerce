package com.mycompany.ecommerce.dto;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
@Component
public class PaymentDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private LocalDateTime time;
	private String amount;
	private String currency;
	private String paymentId;
	private String orderId;
	private String status;
	private String keyDetails;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getKeyDetails() {
		return keyDetails;
	}
	public void setKeyDetails(String keyDetails) {
		this.keyDetails = keyDetails;
	}
	@Override
	public String toString() {
		return "PaymentDetails [id=" + id + ", time=" + time + ", amount=" + amount + ", currency=" + currency
				+ ", paymentId=" + paymentId + ", orderId=" + orderId + ", status=" + status + ", keyDetails="
				+ keyDetails + "]";
	}
	
	

}
