package com.sharingif.blockchain.bitcoin.block.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sharingif.blockchain.bitcoin.block.model.entity.TransactionBusinessAccount;
import com.sharingif.blockchain.bitcoin.block.dao.TransactionBusinessAccountDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.bitcoin.block.service.TransactionBusinessAccountService;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional
	@Override
	public boolean addTransactionBusinessAccount(String address, String coinType) {
		TransactionBusinessAccount queryTransactionBusinessAccount = new TransactionBusinessAccount();
		queryTransactionBusinessAccount.setAddress(address);
		queryTransactionBusinessAccount.setCoinType(coinType);
		queryTransactionBusinessAccount = transactionBusinessAccountDAO.query(queryTransactionBusinessAccount);

		if(queryTransactionBusinessAccount != null) {
			return false;
		}

		TransactionBusinessAccount transactionBusinessAccount = new TransactionBusinessAccount();
		transactionBusinessAccount.setAddress(address);
		transactionBusinessAccount.setCoinType(coinType);

		transactionBusinessAccountDAO.insert(transactionBusinessAccount);
		return true;
	}

}
