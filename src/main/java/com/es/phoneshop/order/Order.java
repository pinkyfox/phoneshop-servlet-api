package com.es.phoneshop.order;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import java.math.BigDecimal;

public class Order implements Serializable {
	private List<CartItem> orderItems;

	private BigDecimal deliveryCost = new BigDecimal(20);
	private BigDecimal subtotal;

	private String orderId;
	private String FirstName;
	private String LastName;
	private String phoneNumber;
	private String paymentMethod;
	private String date;
	private String deliveryAddress;

	public Order() {
		this.orderItems = new ArrayList<>();
	}

	public Order(Cart cart) {
		ArrayList<CartItem> source = (ArrayList<CartItem>) cart.getCartItemList();
		this.orderItems = (List<CartItem>) source.clone();
		this.subtotal = cart.getTotalPrice();
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getDeliveryCost() {
		return deliveryCost;
	}

	public void setDeliveryCost(BigDecimal deliveryCost) {
		this.deliveryCost = deliveryCost;
	}

	public List<CartItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<CartItem> orderItems) {
		this.orderItems = orderItems;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
}
