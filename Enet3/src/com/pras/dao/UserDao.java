package com.pras.dao;

import java.util.List;

import com.pras.dto.UserDto;
import com.pras.model.User;

public interface UserDao {

	public void addUser(UserDto user);
	public void editUser(long id, User user);
	public UserDto getUser(long id);
	public List<UserDto> getUsers();
	public void removeUserById(long id);
	public List<User> getUserByEmail(String email);
}
