package com.sharingif.blockchain.ether.account.controller;


import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sharingif.blockchain.ether.account.service.AccountFrozenJnlService;


@Controller
@RequestMapping(value="accountFrozenJnl")
public class AccountFrozenJnlController {
	
	private AccountFrozenJnlService accountFrozenJnlService;

	public AccountFrozenJnlService getAccountFrozenJnlService() {
		return accountFrozenJnlService;
	}
	@Resource
	public void setAccountFrozenJnlService(AccountFrozenJnlService accountFrozenJnlService) {
		this.accountFrozenJnlService = accountFrozenJnlService;
	}
	
}
