package com.pras.dto;

import java.util.List;

public class UserDetailsDto {

	private long id;
	private String name;
	private String password;
	private String email;
	private String contact;
	private String address;
	private String role;
	private UserDto manager;
	private BranchDto branch;
	private String newPassword;
	private String status;
	private List<RaoDto> raos;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public BranchDto getBranch() {
		return branch;
	}
	public void setBranch(BranchDto branch) {
		this.branch = branch;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<RaoDto> getRaos() {
		return raos;
	}
	public void setRaos(List<RaoDto> raos) {
		this.raos = raos;
	}
	public UserDto getManager() {
		return manager;
	}
	public void setManager(UserDto manager) {
		this.manager = manager;
	}

	
}
