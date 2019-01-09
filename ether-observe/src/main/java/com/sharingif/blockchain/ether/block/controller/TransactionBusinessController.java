package com.sharingif.blockchain.ether.block.controller;


import com.sharingif.blockchain.ether.block.service.TransactionBusinessService;
import com.sharingif.cube.batch.core.request.JobRequest;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;
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

	@RequestMapping(value="settle", method= RequestMethod.POST)
	public void settle() {
		transactionBusinessService.settle();
	}

}
