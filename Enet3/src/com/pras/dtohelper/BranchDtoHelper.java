package com.pras.dtohelper;

import com.pras.dto.BranchDto;
import com.pras.model.Branch;

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
		dto.setContact(b.getAddress());
		dto.setPincode(b.getPincode());
		dto.setPlace(b.getPlace());
		if(b.getBranchmanager() != null) {
			dto.setBranchmanagerId(b.getBranchmanager().getId());
		}
		return dto;
	}
	
}
