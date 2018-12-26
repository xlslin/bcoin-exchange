package com.sharingif.blockchain.ether.account.controller;


import com.sharingif.blockchain.ether.account.service.AccountFrozenJnlService;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;


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
