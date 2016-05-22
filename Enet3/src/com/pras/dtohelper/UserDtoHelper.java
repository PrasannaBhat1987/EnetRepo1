package com.pras.dtohelper;

import com.pras.dto.UserDto;
import com.pras.model.User;

public class UserDtoHelper {

	public static User getEntityFromDto(UserDto dto) {
		User u = new User();
		u.setAddress(dto.getAddress());
		u.setContact(dto.getContact());
		u.setEmail(dto.getEmail());
		u.setName(dto.getName());
		u.setPassword(dto.getPassword());
		u.setRole(dto.getRole());
		return u;
	}
	
	public static UserDto getDtoFromEntity(User user) {
		UserDto dto = new UserDto();
		dto.setAddress(user.getAddress());
		dto.setContact(user.getContact());
		dto.setEmail(user.getEmail());
		dto.setName(user.getName());
		dto.setPassword(user.getPassword());
		dto.setRole(user.getRole());
		if (user.getManager() != null) {
			dto.setManagerId(user.getManager().getId());
		}
		return dto;
	}
}
