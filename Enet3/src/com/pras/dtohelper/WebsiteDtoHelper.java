package com.pras.dtohelper;

import com.pras.dto.WebsiteDto;
import com.pras.model.Website;

public class WebsiteDtoHelper {

	public static Website getEntityFromDto(WebsiteDto dto) {
		Website w = new Website();
		w.setName(dto.getName());
		return w;
	}
	
	public static WebsiteDto getDtoFromEntity(Website w) {
		WebsiteDto dto = new WebsiteDto();
		dto.setId(w.getId());
		dto.setName(w.getName());
		return dto;
	}
	
}
