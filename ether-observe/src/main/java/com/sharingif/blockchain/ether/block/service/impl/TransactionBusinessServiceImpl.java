package com.sharingif.blockchain.ether.block.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sharingif.blockchain.ether.block.model.entity.TransactionBusiness;
import com.sharingif.blockchain.ether.block.dao.TransactionBusinessDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.ether.block.service.TransactionBusinessService;

@Service
public class TransactionBusinessServiceImpl extends BaseServiceImpl<TransactionBusiness, java.lang.String> implements TransactionBusinessService {
	
	private TransactionBusinessDAO transactionBusinessDAO;

	public TransactionBusinessDAO getTransactionBusinessDAO() {
		return transactionBusinessDAO;
	}
	@Resource
	public void setTransactionBusinessDAO(TransactionBusinessDAO transactionBusinessDAO) {
		super.setBaseDAO(transactionBusinessDAO);
		this.transactionBusinessDAO = transactionBusinessDAO;
	}


	@Override
	public void addUntreatedDeposit(TransactionBusiness transactionBusiness) {
		transactionBusiness.setStatus(TransactionBusiness.STATUS_UNTREATED);
		transactionBusiness.setType(TransactionBusiness.TYPE_DEPOSIT);

		transactionBusinessDAO.insert(transactionBusiness);
	}

	@Override
	public void addUntreatedWithdrawal(TransactionBusiness transactionBusiness) {
		transactionBusiness.setStatus(TransactionBusiness.STATUS_UNTREATED);
		transactionBusiness.setType(TransactionBusiness.TYPE_WITHDRAWAL);

		transactionBusinessDAO.insert(transactionBusiness);
	}
}
