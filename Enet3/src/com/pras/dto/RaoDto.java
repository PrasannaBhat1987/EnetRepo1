package com.pras.dto;

import java.util.Date;
import java.util.List;

public class RaoDto {

	private long id;
	private String description;
	private List<LineItemDto> lineItems;
	private String orderNumber;
	private long websiteId;
	private long customerId;
	private long branchId;
	private long userId;
	private String deliveryAddress;
	private Date orderDate;
	private String status;
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
	public List<LineItemDto> getLineItems() {
		return lineItems;
	}
	public void setLineItems(List<LineItemDto> lineItems) {
		this.lineItems = lineItems;
	}
	public long getWebsiteId() {
		return websiteId;
	}
	public void setWebsiteId(long websiteId) {
		this.websiteId = websiteId;
	}
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public long getBranchId() {
		return branchId;
	}
	public void setBranchId(long branchId) {
		this.branchId = branchId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
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
	
}
