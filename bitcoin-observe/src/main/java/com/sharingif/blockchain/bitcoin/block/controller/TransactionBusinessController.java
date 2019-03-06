package com.sharingif.blockchain.bitcoin.block.controller;


import com.sharingif.blockchain.bitcoin.block.model.entity.TransactionBusiness;
import com.sharingif.blockchain.bitcoin.block.service.TransactionBusinessService;
import com.sharingif.cube.batch.core.request.JobRequest;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;


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
	public void settle(JobRequest<List<TransactionBusiness>> jobRequest) {
		transactionBusinessService.settle(jobRequest.getData());
	}
	
}
