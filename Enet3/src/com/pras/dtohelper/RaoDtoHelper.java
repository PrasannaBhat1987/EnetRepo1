package com.pras.dtohelper;

import java.util.List;

import com.pras.dto.RaoDto;
import com.pras.model.LineItem;
import com.pras.model.Rao;

public class RaoDtoHelper {

	public static Rao getEntityFromDto(RaoDto dto) {
		Rao r = new Rao();
		r.setId(dto.getId());
		r.setDeliveryAddress(dto.getDeliveryAddress());
		r.setDescription(dto.getDescription());
		r.setOrderDate(dto.getOrderDate());
		r.setOrderNumber(dto.getOrderNumber());
		r.setStatus(dto.getStatus());
		r.setTotal(dto.getTotal());
		return r;
	}
	
	public static RaoDto getDtoFromEntity(Rao r) {
		RaoDto dto = new RaoDto();
		dto.setId(r.getId());
		dto.setBranchId(r.getBranch().getId());
		dto.setCustomerId(r.getCustomer().getId());
		dto.setDescription(r.getDescription());
		dto.setUserId(r.getUser().getId());
		dto.setUserId(r.getWebsite().getId());
		dto.setOrderNumber(r.getOrderNumber());
		dto.setOrderDate(r.getOrderDate());
		dto.setDeliveryAddress(r.getDeliveryAddress());
		dto.setStatus(r.getStatus());
		dto.setTotal(r.getTotal());
		List<LineItem> items = r.getItems();
		for(int i=0;i<items.size();i++) {
			
		}
		return dto;
	}
	
}
