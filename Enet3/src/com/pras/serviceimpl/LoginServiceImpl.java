package com.pras.serviceimpl;

import java.util.List;

import com.pras.dao.UserDao;
import com.pras.daoimpl.UserDaoImpl;
import com.pras.dto.LoginDto;
import com.pras.model.User;
import com.pras.service.LoginService;

public class LoginServiceImpl implements LoginService {

	@Override
	public User validUser(LoginDto login) {
		// TODO Auto-generated method stub
		UserDao dao = new UserDaoImpl();
		List<User> users = dao.getUserByEmail(login.getUsername());
		if(users.size() == 1) {
			User u = users.get(0);
			if(u.getPassword().equals(login.getPassword())) {
				return u;
			}
		}
		return null;
	}

}
