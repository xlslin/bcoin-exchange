package com.sharingif.blockchain.notice.controller;


import com.sharingif.blockchain.api.notice.entity.TransactionNoticeAddReq;
import com.sharingif.blockchain.notice.service.TransactionNoticeService;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;
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

	/**
	 * 添加通知监听
	 * @return
	 */
	@RequestMapping(value="add", method= RequestMethod.POST)
	public void add(TransactionNoticeAddReq req) {
		transactionNoticeService.add(req);
	}
	
}
