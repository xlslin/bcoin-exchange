package com.sharingif.blockchain.account.controller;


import com.sharingif.blockchain.account.service.AccountService;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;


@Controller
@RequestMapping(value="account")
public class AccountController {
	
	private AccountService accountService;

	public AccountService getAccountService() {
		return accountService;
	}
	@Resource
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	
}
