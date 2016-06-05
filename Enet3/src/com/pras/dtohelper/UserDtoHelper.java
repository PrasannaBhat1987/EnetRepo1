package com.pras.dtohelper;

import com.pras.dto.UserDto;
import com.pras.model.User;

public class UserDtoHelper {

	public static User getEntityFromDto(UserDto dto) {
		User u = new User();
		u.setId(dto.getId());
		u.setAddress(dto.getAddress());
		u.setContact(dto.getContact());
		u.setEmail(dto.getEmail());
		u.setName(dto.getName());
		u.setPassword(dto.getPassword());
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
		return dto;
	}
}
