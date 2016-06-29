package com.pras.dto;

import java.util.List;

public class BranchDetailsDto {

	private long id;
	private String place;
	private String address;
	private long pincode;
	private String contact;
	private UserDto branchmanager;
	private List<UserDto> branchEmployees;
	private List<RaoDto> raos;
	private String status;
	
	
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
	public UserDto getBranchmanager() {
		return branchmanager;
	}
	public void setBranchmanager(UserDto branchmanager) {
		this.branchmanager = branchmanager;
	}
	public List<UserDto> getBranchEmployees() {
		return branchEmployees;
	}
	public void setBranchEmployees(List<UserDto> branchEmployees) {
		this.branchEmployees = branchEmployees;
	}
	public List<RaoDto> getRaos() {
		return raos;
	}
	public void setRaos(List<RaoDto> raos) {
		this.raos = raos;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}	
}
