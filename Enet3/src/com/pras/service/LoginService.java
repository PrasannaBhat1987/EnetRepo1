package com.pras.service;

import com.pras.dto.LoginDto;
import com.pras.model.User;

public interface LoginService {

	User validUser(LoginDto login);
}
