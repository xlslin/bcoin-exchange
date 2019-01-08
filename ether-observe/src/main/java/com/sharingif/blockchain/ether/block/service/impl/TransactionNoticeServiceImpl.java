package com.sharingif.blockchain.ether.block.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sharingif.blockchain.ether.block.model.entity.TransactionNotice;
import com.sharingif.blockchain.ether.block.dao.TransactionNoticeDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.ether.block.service.TransactionNoticeService;

@Service
public class TransactionNoticeServiceImpl extends BaseServiceImpl<TransactionNotice, java.lang.String> implements TransactionNoticeService {
	
	private TransactionNoticeDAO transactionNoticeDAO;

	public TransactionNoticeDAO getTransactionNoticeDAO() {
		return transactionNoticeDAO;
	}
	@Resource
	public void setTransactionNoticeDAO(TransactionNoticeDAO transactionNoticeDAO) {
		super.setBaseDAO(transactionNoticeDAO);
		this.transactionNoticeDAO = transactionNoticeDAO;
	}
	
	
}
