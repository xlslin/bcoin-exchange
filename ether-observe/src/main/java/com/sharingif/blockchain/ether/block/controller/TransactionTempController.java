package com.sharingif.blockchain.ether.block.controller;


import com.sharingif.blockchain.ether.block.service.TransactionTempService;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;


@Controller
@RequestMapping(value="transactionTemp")
public class TransactionTempController {
	
	private TransactionTempService transactionTempService;

	public TransactionTempService getTransactionTempService() {
		return transactionTempService;
	}
	@Resource
	public void setTransactionTempService(TransactionTempService transactionTempService) {
		this.transactionTempService = transactionTempService;
	}
	
}
