package com.sharingif.blockchain.ether.block.controller;


import com.sharingif.blockchain.ether.block.service.TransactionBusinessService;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;


@Controller
@RequestMapping(value="transactionBusiness")
public class TransactionBusinessController {
	
	private TransactionBusinessService transactionBusinessService;

	public TransactionBusinessService getTransactionBusinessService() {
		return transactionBusinessService;
	}
	@Resource
	public void setTransactionBusinessService(TransactionBusinessService transactionBusinessService) {
		this.transactionBusinessService = transactionBusinessService;
	}

}
