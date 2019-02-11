package com.sharingif.blockchain.bitcoin.block.controller;


import com.sharingif.blockchain.bitcoin.block.service.TransactionBusinessAccountService;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;


@Controller
@RequestMapping(value="transactionBusinessAccount")
public class TransactionBusinessAccountController {
	
	private TransactionBusinessAccountService transactionBusinessAccountService;

	public TransactionBusinessAccountService getTransactionBusinessAccountService() {
		return transactionBusinessAccountService;
	}
	@Resource
	public void setTransactionBusinessAccountService(TransactionBusinessAccountService transactionBusinessAccountService) {
		this.transactionBusinessAccountService = transactionBusinessAccountService;
	}

	@RequestMapping(value="settleAccounts", method= RequestMethod.POST)
	public void settleAccounts() {
		transactionBusinessAccountService.settleAccounts();
	}
	
}
