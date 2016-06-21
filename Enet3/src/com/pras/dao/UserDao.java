package com.pras.dao;

import java.util.List;

import com.pras.dto.UserDetailsDto;
import com.pras.dto.UserDto;
import com.pras.model.User;

public interface UserDao {

	public int addUser(UserDto user);
	public int editUser(long id, UserDto dto);
	public UserDto getUser(long id);
	public List<UserDto> getUsers();
	public void removeUserById(long id);
	public User getUserByEmail(String email);
	public int getUserType(String string);
	public UserDetailsDto getUserDetails(long id);
}
