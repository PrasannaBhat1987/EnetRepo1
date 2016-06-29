package com.pras.dto;

import java.util.List;

public class CustomerDetailsDto {

	private long id;
	private String name;
	private String email;
	private String contact;
	private String address;
	private List<RaoDto> raos;
	private String status;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
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
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
