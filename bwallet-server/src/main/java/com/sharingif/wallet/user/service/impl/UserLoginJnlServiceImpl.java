package com.sharingif.wallet.user.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sharingif.wallet.user.model.entity.UserLoginJnl;
import com.sharingif.wallet.user.dao.UserLoginJnlDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.wallet.user.service.UserLoginJnlService;

@Service
public class UserLoginJnlServiceImpl extends BaseServiceImpl<UserLoginJnl, java.lang.String> implements UserLoginJnlService {
	
	private UserLoginJnlDAO userLoginJnlDAO;

	public UserLoginJnlDAO getUserLoginJnlDAO() {
		return userLoginJnlDAO;
	}
	@Resource
	public void setUserLoginJnlDAO(UserLoginJnlDAO userLoginJnlDAO) {
		super.setBaseDAO(userLoginJnlDAO);
		this.userLoginJnlDAO = userLoginJnlDAO;
	}
	
	
}
