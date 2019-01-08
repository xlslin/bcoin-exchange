package com.sharingif.blockchain.ether.block.controller;


import com.sharingif.blockchain.ether.block.service.TransactionNoticeService;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;


@Controller
@RequestMapping(value="transactionNotice")
public class TransactionNoticeController {
	
	private TransactionNoticeService transactionNoticeService;

	public TransactionNoticeService getTransactionNoticeService() {
		return transactionNoticeService;
	}
	@Resource
	public void setTransactionNoticeService(TransactionNoticeService transactionNoticeService) {
		this.transactionNoticeService = transactionNoticeService;
	}
	
}
