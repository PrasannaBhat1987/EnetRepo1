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
		User u = dao.getUserByEmail(login.getUsername());
		if(u != null) {
			if(u.getPassword().equals(login.getPassword())) {
				return u;
			}
		}
		return null;
	}

}
