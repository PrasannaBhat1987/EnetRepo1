package com.pras.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Website implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6590551770546808335L;
	@Id
	@GeneratedValue
	private long id;
	private String name;
	
	@OneToMany(mappedBy="website")
	private List<Rao> raos;

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

	public List<Rao> getRaos() {
		return raos;
	}

	public void setRaos(List<Rao> raos) {
		this.raos = raos;
	}

}
