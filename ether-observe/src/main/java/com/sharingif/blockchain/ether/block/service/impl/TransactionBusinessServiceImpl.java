package com.sharingif.blockchain.ether.block.service.impl;


import com.sharingif.blockchain.ether.block.dao.TransactionBusinessDAO;
import com.sharingif.blockchain.ether.block.model.entity.BlockChain;
import com.sharingif.blockchain.ether.block.model.entity.Transaction;
import com.sharingif.blockchain.ether.block.model.entity.TransactionBusiness;
import com.sharingif.blockchain.ether.block.service.TransactionBusinessService;
import com.sharingif.blockchain.ether.deposit.service.DepositService;
import com.sharingif.blockchain.ether.withdrawal.service.WithdrawalService;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

@Service
public class TransactionBusinessServiceImpl extends BaseServiceImpl<TransactionBusiness, java.lang.String> implements TransactionBusinessService {
	
	private TransactionBusinessDAO transactionBusinessDAO;
	private DepositService depositService;
	private WithdrawalService withdrawalService;

	public TransactionBusinessDAO getTransactionBusinessDAO() {
		return transactionBusinessDAO;
	}
	@Resource
	public void setTransactionBusinessDAO(TransactionBusinessDAO transactionBusinessDAO) {
		super.setBaseDAO(transactionBusinessDAO);
		this.transactionBusinessDAO = transactionBusinessDAO;
	}
	@Resource
	public void setDepositService(DepositService depositService) {
		this.depositService = depositService;
	}
	@Resource
	public void setWithdrawalService(WithdrawalService withdrawalService) {
		this.withdrawalService = withdrawalService;
	}

	@Override
	public void addUntreated(TransactionBusiness transactionBusiness) {
		transactionBusiness.setStatus(TransactionBusiness.STATUS_UNTREATED);
		transactionBusiness.setTxStatus(BlockChain.STATUS_UNVERIFIED);

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
	public void updateTxStatusToBlockConfirmedValid(String transactionId) {
		TransactionBusiness queryTransactionBusiness = new TransactionBusiness();
		queryTransactionBusiness.setTransactionId(transactionId);
		queryTransactionBusiness.setTxStatus(BlockChain.STATUS_UNVERIFIED);

		List<TransactionBusiness> transactionBusinessList = transactionBusinessDAO.queryList(queryTransactionBusiness);
		if(transactionBusinessList == null || transactionBusinessList.isEmpty()) {
			return;
		}

		for(TransactionBusiness transactionBusiness : transactionBusinessList) {
			TransactionBusiness updateTransactionBusiness = new TransactionBusiness();
			updateTransactionBusiness.setId(transactionBusiness.getId());
			updateTransactionBusiness.setTxStatus(BlockChain.STATUS_VERIFY_VALID);
			transactionBusinessDAO.updateById(updateTransactionBusiness);

			if(TransactionBusiness.TYPE_DEPOSIT.equals(transactionBusiness.getType())) {
				continue;
			}

			if(TransactionBusiness.TYPE_WITHDRAWAL.equals(transactionBusiness.getType())) {
				withdrawalService.withdrawalSuccess(transactionBusiness);
				continue;
			}
		}
	}

	@Override
	public void updateTxStatusToBlockConfirmedInvalid(String transactionId) {
		TransactionBusiness queryTransactionBusiness = new TransactionBusiness();
		queryTransactionBusiness.setTransactionId(transactionId);
		queryTransactionBusiness.setTxStatus(BlockChain.STATUS_UNVERIFIED);

		List<TransactionBusiness> transactionBusinessList = transactionBusinessDAO.queryList(queryTransactionBusiness);
		if(transactionBusinessList == null || transactionBusinessList.isEmpty()) {
			return;
		}

		for(TransactionBusiness transactionBusiness : transactionBusinessList) {
			TransactionBusiness updateTransactionBusiness = new TransactionBusiness();
			updateTransactionBusiness.setId(transactionBusiness.getId());
			updateTransactionBusiness.setTxStatus(BlockChain.STATUS_VERIFY_VALID);
			transactionBusinessDAO.updateById(updateTransactionBusiness);

			if(TransactionBusiness.TYPE_DEPOSIT.equals(transactionBusiness.getType())) {
				depositService.depositReback(transactionBusiness);
				continue;
			}
			if(TransactionBusiness.TYPE_WITHDRAWAL.equals(transactionBusiness.getType())) {
				withdrawalService.withdrawalFailure(transactionBusiness);
				continue;
			}
		}

	}

}
