package com.pras.dto;

public class LineItemDto {

	private long id;
	private String itemDescription;
	private int quantity;
	private double price;
	private long raoId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public long getRaoId() {
		return raoId;
	}
	public void setRaoId(long raoId) {
		this.raoId = raoId;
	}
	
}
