package com.sharingif.blockchain.ether.block.controller;


import com.sharingif.blockchain.ether.block.model.entity.BlockTransaction;
import com.sharingif.blockchain.ether.block.service.TransactionService;
import com.sharingif.cube.batch.core.request.JobRequest;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;


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

	@RequestMapping(value="analysis", method= RequestMethod.POST)
	public void analysis(JobRequest<BlockTransaction> jobRequest) {
		transactionService.analysis(jobRequest.getData());
	}
	
}
