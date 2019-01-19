package com.sharingif.wallet.user.controller;


import com.sharingif.wallet.user.service.UserLoginJnlService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;


@Controller
@RequestMapping(value="userLoginJnl")
public class UserLoginJnlController {
	
	private UserLoginJnlService userLoginJnlService;

	public UserLoginJnlService getUserLoginJnlService() {
		return userLoginJnlService;
	}
	@Resource
	public void setUserLoginJnlService(UserLoginJnlService userLoginJnlService) {
		this.userLoginJnlService = userLoginJnlService;
	}
	
}
