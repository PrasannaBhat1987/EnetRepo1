package com.pras.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonManagedReference;

@Entity
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5917566268338057801L;

	@Id
	@GeneratedValue
	private long id;
	private String name;
	private String password;
	private String email;
	private String contact;
	private String address;
	private String role;
	
	@JsonIgnore
	@OneToMany(mappedBy="branchmanager")
	private List<Branch> branches;

	@JsonBackReference
	@ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="manager_id")
	private User manager;
	
	@JsonManagedReference
	@OneToMany(mappedBy="manager")
	private List<User> subordinates;
	
	@JsonIgnore
	@OneToMany(mappedBy="user")
	private List<Rao> raos;
	
	public List<Rao> getRaos() {
		return raos;
	}

	public void setRaos(List<Rao> raos) {
		this.raos = raos;
	}
	
	public List<Branch> getBranches() {
		return branches;
	}

	public void setBranches(List<Branch> branches) {
		this.branches = branches;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	public List<User> getSubordinates() {
		return subordinates;
	}

	public void setSubordinates(List<User> subordinates) {
		this.subordinates = subordinates;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
