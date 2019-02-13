package com.sharingif.blockchain.notice.service.impl;


import javax.annotation.Resource;

import com.sharingif.blockchain.api.notice.entity.TransactionNoticeAddReq;
import org.springframework.stereotype.Service;

import com.sharingif.blockchain.notice.model.entity.TransactionNotice;
import com.sharingif.blockchain.notice.dao.TransactionNoticeDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.notice.service.TransactionNoticeService;

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


	@Override
	public void add(TransactionNoticeAddReq req) {
		TransactionNotice transactionNotice = new TransactionNotice();
		transactionNotice.setBlockType(req.getBlockType());
		transactionNotice.setNoticeType(req.getNoticeType());
		transactionNotice.setNoticeAddress(req.getNoticeAddress());

		transactionNoticeDAO.insert(transactionNotice);
	}
}
