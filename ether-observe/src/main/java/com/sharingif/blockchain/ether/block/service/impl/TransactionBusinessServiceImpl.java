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
	public int updateTxStatusToValid(BigInteger blockNumber, String blockHash) {
		TransactionBusiness transactionBusiness = new TransactionBusiness();
		transactionBusiness.setBlockNumber(blockNumber);
		transactionBusiness.setBlockHash(blockHash);
		transactionBusiness.setTxStatus(BlockChain.STATUS_VERIFY_VALID);

		return transactionBusinessDAO.updateByBlockNumberBlockHash(transactionBusiness);
	}

	@Override
	public int updateTxStatusToInvalid(BigInteger blockNumber, String blockHash) {
		TransactionBusiness transactionBusiness = new TransactionBusiness();
		transactionBusiness.setBlockNumber(blockNumber);
		transactionBusiness.setBlockHash(blockHash);
		transactionBusiness.setTxStatus(BlockChain.STATUS_VERIFY_INVALID);

		return transactionBusinessDAO.updateByBlockNumberBlockHash(transactionBusiness);
	}

	@Override
	public int updateTxStatusToSettled(String id) {
		TransactionBusiness transactionBusiness = new TransactionBusiness();
		transactionBusiness.setId(id);
		transactionBusiness.setTxStatus(BlockChain.STATUS_SETTLED);

		return transactionBusinessDAO.updateById(transactionBusiness);
	}

	@Transactional
	@Override
	public void settleTransactionSuccess(BigInteger blockNumber, String blockHash) {
		TransactionBusiness queryTransactionBusiness = new TransactionBusiness();
		queryTransactionBusiness.setBlockNumber(blockNumber);
		queryTransactionBusiness.setBlockHash(blockHash);

		List<TransactionBusiness> transactionBusinessList = transactionBusinessDAO.queryList(queryTransactionBusiness);
		for(TransactionBusiness transactionBusiness : transactionBusinessList) {
			if(TransactionBusiness.TYPE_DEPOSIT.equals(transactionBusiness.getType())) {
				depositService.deposit(transactionBusiness);
				updateTxStatusToSettled(transactionBusiness.getId());
				continue;
			}
			if(TransactionBusiness.TYPE_WITHDRAWAL.equals(transactionBusiness.getType())) {
				withdrawalService.withdrawalSuccess(transactionBusiness);
				updateTxStatusToSettled(transactionBusiness.getId());
				continue;
			}
		}

	}

	@Override
	public void settleTransactionFailure(BigInteger blockNumber, String blockHash) {
		TransactionBusiness queryTransactionBusiness = new TransactionBusiness();
		queryTransactionBusiness.setBlockNumber(blockNumber);
		queryTransactionBusiness.setBlockHash(blockHash);

		List<TransactionBusiness> transactionBusinessList = transactionBusinessDAO.queryList(queryTransactionBusiness);

		for(TransactionBusiness transactionBusiness : transactionBusinessList) {
			if(TransactionBusiness.TYPE_WITHDRAWAL.equals(transactionBusiness.getType())) {
				withdrawalService.withdrawalFailure(transactionBusiness);
				updateTxStatusToSettled(transactionBusiness.getId());
				continue;
			}
		}

	}

}
