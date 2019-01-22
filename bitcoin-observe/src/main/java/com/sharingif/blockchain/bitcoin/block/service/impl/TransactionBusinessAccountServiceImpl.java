package com.sharingif.blockchain.bitcoin.block.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sharingif.blockchain.bitcoin.block.model.entity.TransactionBusinessAccount;
import com.sharingif.blockchain.bitcoin.block.dao.TransactionBusinessAccountDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.bitcoin.block.service.TransactionBusinessAccountService;

@Service
public class TransactionBusinessAccountServiceImpl extends BaseServiceImpl<TransactionBusinessAccount, java.lang.String> implements TransactionBusinessAccountService {
	
	private TransactionBusinessAccountDAO transactionBusinessAccountDAO;

	public TransactionBusinessAccountDAO getTransactionBusinessAccountDAO() {
		return transactionBusinessAccountDAO;
	}
	@Resource
	public void setTransactionBusinessAccountDAO(TransactionBusinessAccountDAO transactionBusinessAccountDAO) {
		super.setBaseDAO(transactionBusinessAccountDAO);
		this.transactionBusinessAccountDAO = transactionBusinessAccountDAO;
	}
	
	
}
