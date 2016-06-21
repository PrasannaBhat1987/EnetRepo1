package com.pras.dtohelper;

import java.util.ArrayList;
import java.util.List;

import com.pras.dto.BranchDto;
import com.pras.dto.RaoDto;
import com.pras.dto.UserDetailsDto;
import com.pras.dto.UserDto;
import com.pras.model.Branch;
import com.pras.model.Rao;
import com.pras.model.User;

public class UserDtoHelper {

	public static User getEntityFromDto(UserDto dto) {
		User u = new User();
		u.setId(dto.getId());
		u.setAddress(dto.getAddress());
		u.setContact(dto.getContact());
		u.setEmail(dto.getEmail());
		u.setName(dto.getName());
		if(dto.getStatus() != null) {
			u.setStatus(dto.getStatus());
		}
		
		if(dto.getPassword() != null) {
			u.setPassword(dto.getPassword());
		}
		if(dto.getNewPassword() != null) {
			u.setPassword(dto.getNewPassword());
		}
		u.setRole(dto.getRole());
		u.setManagerId(dto.getManagerId());
		return u;
	}
	
	public static UserDto getDtoFromEntity(User user) {
		UserDto dto = new UserDto();
		dto.setId(user.getId());
		dto.setAddress(user.getAddress());
		dto.setContact(user.getContact());
		dto.setEmail(user.getEmail());
		dto.setName(user.getName());
		dto.setPassword(user.getPassword());
		dto.setRole(user.getRole());
		dto.setManagerId(user.getManagerId());
		if(user.getBranch() != null) {
			dto.setBranchId(user.getBranch().getId());
		}
		dto.setStatus(user.getStatus());
		return dto;
	}

	public static UserDetailsDto getDetailsDtoFromEntity(User user, User manager) {
		UserDetailsDto dto = new UserDetailsDto();
		dto.setId(user.getId());
		dto.setAddress(user.getAddress());
		dto.setContact(user.getContact());
		dto.setEmail(user.getEmail());
		dto.setName(user.getName());
		dto.setPassword(user.getPassword());
		dto.setRole(user.getRole());
		if(manager != null) {
			dto.setManager(UserDtoHelper.getDtoFromEntity(manager));
		}
		
		
		Branch b = user.getBranch();
		BranchDto bdto = null;
		if(b != null) {
			bdto = BranchDtoHelper.getDtoFromEntity(b);
		}
		dto.setBranch(bdto);
		
		List<Rao> raos = user.getRaos();
		List<RaoDto> raoDtos = new ArrayList<RaoDto>();
		for(Rao r : raos) {
			raoDtos.add(RaoDtoHelper.getDtoFromEntity(r));
		}
		dto.setRaos(raoDtos);
		
		return dto;
	}
}
