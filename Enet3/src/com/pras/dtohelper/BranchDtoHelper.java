package com.pras.dtohelper;

import java.util.ArrayList;
import java.util.List;

import com.pras.dto.BranchDetailsDto;
import com.pras.dto.BranchDto;
import com.pras.dto.RaoDto;
import com.pras.dto.UserDto;
import com.pras.model.Branch;
import com.pras.model.Rao;

public class BranchDtoHelper {

	public static Branch getEntityFromDto(BranchDto dto) {
		Branch b = new Branch();
		b.setAddress(dto.getAddress());
		b.setContact(dto.getContact());
		b.setPincode(dto.getPincode());
		b.setPlace(dto.getPlace());
		return b;
	}
	
	public static BranchDto getDtoFromEntity(Branch b) {
		BranchDto dto = new BranchDto();
		dto.setAddress(b.getAddress());
		dto.setContact(b.getContact());
		dto.setPincode(b.getPincode());
		dto.setPlace(b.getPlace());
		if(b.getBranchmanager() != null) {
			dto.setBranchmanagerId(b.getBranchmanager().getId());
		}
		dto.setStatus(b.getStatus());
		return dto;
	}

	public static BranchDetailsDto getDetailsDtoFromEntity(Branch b) {
		BranchDetailsDto dto = new BranchDetailsDto();
		dto.setId(b.getId());
		dto.setAddress(b.getAddress());
		dto.setContact(b.getContact());
		dto.setPincode(b.getPincode());
		dto.setPlace(b.getPlace());
		UserDto manager = UserDtoHelper.getDtoFromEntity(b.getBranchmanager());
		dto.setBranchmanager(manager);
		
		List<Rao> raos = b.getRaos();
		List<RaoDto> raoDtos = new ArrayList<RaoDto>();
		for(Rao r : raos) {
			raoDtos.add(RaoDtoHelper.getDtoFromEntity(r));
		}
		dto.setRaos(raoDtos);
		dto.setStatus(b.getStatus());		
		return dto;
	}
	
}
