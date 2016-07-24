package com.pras.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Rao implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8107886595661622432L;
	@Id
	@GeneratedValue
	private long id;
	private String description;
	
	@OneToMany(mappedBy="rao", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private List<LineItem> items;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="website_id")
	private Website website;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="customer_id")
	private Customer customer;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="branch_id")
	private Branch branch;
	
	@ManyToOne (fetch=FetchType.EAGER)
    @JoinColumn(name="user_id")
	private User user;
	
	private String orderNumber;
	
	private String deliveryAddress;
	
	private Date orderDate;
	
	private String status;
	
	private float deliveryCharge;
	
	private float total;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<LineItem> getItems() {
		return items;
	}

	public void setItems(List<LineItem> items) {
		this.items = items;
	}

	public Website getWebsite() {
		return website;
	}

	public void setWebsite(Website website) {
		this.website = website;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public float getDeliveryCharge() {
		return deliveryCharge;
	}

	public void setDeliveryCharge(float deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}
	
}
