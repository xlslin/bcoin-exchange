package com.sharingif.blockchain.ether.block.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sharingif.blockchain.ether.block.model.entity.TransactionBusinessAccount;
import com.sharingif.blockchain.ether.block.dao.TransactionBusinessAccountDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.ether.block.service.TransactionBusinessAccountService;

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


	@Override
	public boolean addTransactionBusinessAccount(String address, String coinType, String contractAddress) {
		TransactionBusinessAccount transactionBusinessAccount = transactionBusinessAccountDAO.queryByAddressCoinTypeForUpdate(address, coinType);

		if(transactionBusinessAccount != null) {
			return false;
		}

		transactionBusinessAccount = new TransactionBusinessAccount();
		transactionBusinessAccount.setAddress(address);
		transactionBusinessAccount.setCoinType(coinType);
		transactionBusinessAccount.setContractAddress(contractAddress);

		return true;
	}
}
