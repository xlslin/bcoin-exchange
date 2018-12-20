package com.sharingif.blockchain.ether.block.controller;


import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sharingif.blockchain.ether.block.service.TransactionService;


@Controller
@RequestMapping(value="transaction")
public class TransactionController {
	
	private TransactionService transactionService;

	public TransactionService getTransactionService() {
		return transactionService;
	}
	@Resource
	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}
	
}
