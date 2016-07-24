package com.pras.dtohelper;

import java.util.ArrayList;
import java.util.List;

import com.pras.dto.LineItemDto;
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
		r.setDeliveryCharge(dto.getDeliveryCharge());
		return r;
	}
	
	public static RaoDto getDtoFromEntity(Rao r) {
		RaoDto dto = new RaoDto();
		dto.setId(r.getId());
		
		if(r.getBranch() != null) {
			dto.setBranchId(r.getBranch().getId());
		}
		
		if(r.getCustomer() != null) {
			dto.setCustomerId(r.getCustomer().getId());
		}
		
		dto.setDescription(r.getDescription());
		if(r.getUser() != null) {
			dto.setUserId(r.getUser().getId());
		}
		
		if(r.getWebsite() != null) {
			dto.setWebsiteId(r.getWebsite().getId());
		}
		
		dto.setOrderNumber(r.getOrderNumber());
		dto.setOrderDate(r.getOrderDate());
		dto.setDeliveryAddress(r.getDeliveryAddress());
		dto.setStatus(r.getStatus());
		dto.setTotal(r.getTotal());
		dto.setDeliveryCharge(r.getDeliveryCharge());
		List<LineItem> items = r.getItems();
		List<LineItemDto> dtos = new ArrayList<LineItemDto>();
		for(int i=0;i<items.size();i++) {
			dtos.add(LineItemDtoHelper.getDtoFromEntity(items.get(i)));
		}
		dto.setLineItems(dtos);
		return dto;
	}
	
}
