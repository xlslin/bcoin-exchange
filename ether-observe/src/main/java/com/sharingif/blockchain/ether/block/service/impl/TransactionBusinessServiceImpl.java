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
	public void addUntreated(TransactionBusiness transactionBusiness) {
		transactionBusiness.setStatus(TransactionBusiness.STATUS_UNTREATED);

		transactionBusinessDAO.insert(transactionBusiness);
	}

	@Override
	public int updateStatusToInitNotice(String id) {
		TransactionBusiness transactionBusiness = new TransactionBusiness();
		transactionBusiness.setId(id);
		transactionBusiness.setStatus(TransactionBusiness.STATUS_INIT_NOTICE);

		return transactionBusinessDAO.updateById(transactionBusiness);
	}

	@Override
	public int updateStatusToInitNoticed(String id) {
		TransactionBusiness transactionBusiness = new TransactionBusiness();
		transactionBusiness.setId(id);
		transactionBusiness.setStatus(TransactionBusiness.STATUS_INIT_NOTICED);

		return transactionBusinessDAO.updateById(transactionBusiness);
	}

}
