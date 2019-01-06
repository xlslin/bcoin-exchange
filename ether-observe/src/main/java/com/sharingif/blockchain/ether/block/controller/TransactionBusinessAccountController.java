package com.sharingif.blockchain.ether.block.controller;


import com.sharingif.blockchain.ether.block.service.TransactionBusinessAccountService;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
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


	
}
