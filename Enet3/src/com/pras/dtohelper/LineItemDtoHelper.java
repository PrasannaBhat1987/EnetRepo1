package com.pras.dtohelper;

import com.pras.dto.BranchDto;
import com.pras.dto.LineItemDto;
import com.pras.model.Branch;
import com.pras.model.LineItem;

public class LineItemDtoHelper {
	
	public static LineItem getEntityFromDto(LineItemDto dto) {
		LineItem li = new LineItem();
		li.setId(dto.getId());
		li.setItemDescription(dto.getItemDescription());
		li.setPrice(dto.getPrice());
		li.setQuantity(dto.getQuantity());
		return li;
	}
	
	public static LineItemDto getDtoFromEntity(LineItem li) {
		LineItemDto dto = new LineItemDto();
		dto.setId(li.getId());
		dto.setItemDescription(li.getItemDescription());
		dto.setPrice(li.getPrice());
		dto.setQuantity(li.getQuantity());
		if(li.getRao() != null) {
			dto.setRaoId(li.getRao().getId());
		}
		return dto;
	}

}
