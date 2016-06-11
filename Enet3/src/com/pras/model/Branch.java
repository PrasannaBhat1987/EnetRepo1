package com.pras.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Branch implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1777563140335291995L;
	@Id
	@GeneratedValue
	private long id;
	private String place;
	private String address;
	private long pincode;
	private String contact;
	
	@ManyToOne
    @JoinColumn(name="manager_id", nullable= true)
	private User branchmanager;

	@OneToMany(mappedBy="branch")
	private List<Rao> raos;
	
	@OneToMany(mappedBy="branch")
	private List<User> branchEmployees;
	
	public List<User> getBranchEmployees() {
		return branchEmployees;
	}

	public void setBranchEmployees(List<User> branchEmployees) {
		this.branchEmployees = branchEmployees;
	}

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

	public User getBranchmanager() {
		return branchmanager;
	}

	public void setBranchmanager(User branchmanager) {
		this.branchmanager = branchmanager;
	}

	public List<Rao> getRaos() {
		return raos;
	}

	public void setRaos(List<Rao> raos) {
		this.raos = raos;
	}

}
