package com.pras.dtohelper;

import java.util.ArrayList;
import java.util.List;

import com.pras.dto.CustomerDetailsDto;
import com.pras.dto.CustomerDto;
import com.pras.dto.RaoDto;
import com.pras.dto.WebsiteDto;
import com.pras.model.Customer;
import com.pras.model.Rao;
import com.pras.model.Website;

public class CustomerDtoHelper {

	public static Customer getEntityFromDto(CustomerDto dto) {
		Customer c = new Customer();
		c.setName(dto.getName());
		c.setAddress(dto.getAddress());
		c.setContact(dto.getContact());
		c.setEmail(dto.getEmail());
		return c;
	}
	
	public static CustomerDto getDtoFromEntity(Customer c) {
		CustomerDto dto = new CustomerDto();
		dto.setId(c.getId());
		dto.setName(c.getName());
		dto.setAddress(c.getAddress());
		dto.setContact(c.getContact());
		dto.setEmail(c.getEmail());
		return dto;
	}

	public static CustomerDetailsDto getDetailsDtoFromEntity(Customer c) {
		CustomerDetailsDto dto = new CustomerDetailsDto();
		dto.setId(c.getId());
		dto.setName(c.getName());
		dto.setAddress(c.getAddress());
		dto.setContact(c.getContact());
		dto.setEmail(c.getEmail());
		
		List<Rao> raos = c.getRaos();
		List<RaoDto> raoDtos = new ArrayList<RaoDto>();
		for(Rao r : raos) {
			raoDtos.add(RaoDtoHelper.getDtoFromEntity(r));
		}
		dto.setRaos(raoDtos);
		
		return dto;
	}
	
}
