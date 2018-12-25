package com.sharingif.blockchain.ether.account.controller;


import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sharingif.blockchain.ether.account.service.AccountJnlService;


@Controller
@RequestMapping(value="accountJnl")
public class AccountJnlController {
	
	private AccountJnlService accountJnlService;

	public AccountJnlService getAccountJnlService() {
		return accountJnlService;
	}
	@Resource
	public void setAccountJnlService(AccountJnlService accountJnlService) {
		this.accountJnlService = accountJnlService;
	}
	
}
