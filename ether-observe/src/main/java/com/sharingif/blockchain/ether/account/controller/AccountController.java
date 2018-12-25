package com.sharingif.blockchain.ether.account.controller;


import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sharingif.blockchain.ether.account.service.AccountService;


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
