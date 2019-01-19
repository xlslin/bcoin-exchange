package com.sharingif.wallet.user.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sharingif.wallet.user.model.entity.User;
import com.sharingif.wallet.user.dao.UserDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.wallet.user.service.UserService;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, java.lang.String> implements UserService {
	
	private UserDAO userDAO;

	public UserDAO getUserDAO() {
		return userDAO;
	}
	@Resource
	public void setUserDAO(UserDAO userDAO) {
		super.setBaseDAO(userDAO);
		this.userDAO = userDAO;
	}
	
	
}
