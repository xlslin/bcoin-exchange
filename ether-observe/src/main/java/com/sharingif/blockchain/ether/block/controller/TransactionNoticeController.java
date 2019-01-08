package com.sharingif.blockchain.ether.block.controller;


import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sharingif.blockchain.ether.block.service.TransactionNoticeService;


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
