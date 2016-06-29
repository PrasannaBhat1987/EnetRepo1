package com.pras.dto;

import java.util.List;

public class WebsiteDetailsDto {
	
	private long id;
	private String name;
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
