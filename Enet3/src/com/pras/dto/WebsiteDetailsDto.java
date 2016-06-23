package com.pras.dto;

import java.util.List;

public class WebsiteDetailsDto {
	
	private long id;
	private String name;
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
	public List<RaoDto> getRaos() {
		return raos;
	}
	public void setRaos(List<RaoDto> raos) {
		this.raos = raos;
	}
	

	
}
