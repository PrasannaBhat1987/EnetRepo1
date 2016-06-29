package com.pras.dtohelper;

import java.util.ArrayList;
import java.util.List;

import com.pras.dto.RaoDto;
import com.pras.dto.WebsiteDetailsDto;
import com.pras.dto.WebsiteDto;
import com.pras.model.Rao;
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
		dto.setStatus(w.getStatus());
		return dto;
	}

	public static WebsiteDetailsDto getDetailsDtoFromEntity(Website w) {
		WebsiteDetailsDto dto = new WebsiteDetailsDto();
		dto.setId(w.getId());
		dto.setName(w.getName());
		List<Rao> raos = w.getRaos();
		List<RaoDto> raoDtos = new ArrayList<RaoDto>();
		for(Rao r : raos) {
			raoDtos.add(RaoDtoHelper.getDtoFromEntity(r));
		}
		dto.setRaos(raoDtos);
		dto.setStatus(w.getStatus());
		return dto;
	}
	
}
