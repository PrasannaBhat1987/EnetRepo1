package com.pras.dto;


public class BranchDto {

	private long id;
	private String place;
	private String address;
	private long pincode;
	private String contact;
	private long branchmanagerId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public long getPincode() {
		return pincode;
	}
	public void setPincode(long pincode) {
		this.pincode = pincode;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public long getBranchmanagerId() {
		return branchmanagerId;
	}
	public void setBranchmanagerId(long branchmanager) {
		this.branchmanagerId = branchmanager;
	}
	
}
