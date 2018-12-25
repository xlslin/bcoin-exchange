package com.sharingif.blockchain.ether.block.service.impl;


import com.sharingif.blockchain.ether.block.dao.TransactionBusinessDAO;
import com.sharingif.blockchain.ether.block.model.entity.Transaction;
import com.sharingif.blockchain.ether.block.model.entity.TransactionBusiness;
import com.sharingif.blockchain.ether.block.service.TransactionBusinessService;
import com.sharingif.blockchain.ether.block.service.TransactionService;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TransactionBusinessServiceImpl extends BaseServiceImpl<TransactionBusiness, java.lang.String> implements TransactionBusinessService {
	
	private TransactionBusinessDAO transactionBusinessDAO;
	private TransactionService transactionService;

	public TransactionBusinessDAO getTransactionBusinessDAO() {
		return transactionBusinessDAO;
	}
	@Resource
	public void setTransactionBusinessDAO(TransactionBusinessDAO transactionBusinessDAO) {
		super.setBaseDAO(transactionBusinessDAO);
		this.transactionBusinessDAO = transactionBusinessDAO;
	}
	@Resource
	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
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

	@Override
	public int updateStatusToValid(String id) {
		TransactionBusiness transactionBusiness = new TransactionBusiness();
		transactionBusiness.setId(id);
		transactionBusiness.setStatus(TransactionBusiness.STATUS_VALID);

		return transactionBusinessDAO.updateById(transactionBusiness);
	}

	@Override
	public int updateStatusToInvalid(String id) {
		TransactionBusiness transactionBusiness = new TransactionBusiness();
		transactionBusiness.setId(id);
		transactionBusiness.setStatus(TransactionBusiness.STATUS_INVALID);

		return transactionBusinessDAO.updateById(transactionBusiness);
	}

	@Override
	public void validateTransaction() {
		TransactionBusiness queryTransactionBusiness = new TransactionBusiness();
		queryTransactionBusiness.setStatus(TransactionBusiness.STATUS_INIT_NOTICED);
		PaginationCondition<TransactionBusiness> paginationCondition = new PaginationCondition<TransactionBusiness>();
		paginationCondition.setCondition(queryTransactionBusiness);
		paginationCondition.setQueryCount(false);
		paginationCondition.setCurrentPage(1);
		paginationCondition.setPageSize(20);

		PaginationRepertory<TransactionBusiness> transactionBusinessPaginationRepertory = transactionBusinessDAO.queryPagination(paginationCondition);
		List<TransactionBusiness> transactionBusinessList = transactionBusinessPaginationRepertory.getPageItems();
		if(transactionBusinessList == null || transactionBusinessList.isEmpty()) {
			return;
		}

		for(TransactionBusiness transactionBusiness : transactionBusinessList) {
			Transaction transaction = transactionService.getById(transactionBusiness.getTransactionId());
			if(transaction.isUntreated()) {
				continue;
			}

			if(transaction.isBlockConfirmedValid()) {
				updateStatusToValid(transactionBusiness.getId());
			} else {
				updateStatusToInvalid(transactionBusiness.getId());
			}
		}
	}

}
