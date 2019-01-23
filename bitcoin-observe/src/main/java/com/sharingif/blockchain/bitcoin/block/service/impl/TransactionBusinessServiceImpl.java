package com.sharingif.blockchain.bitcoin.block.service.impl;


import javax.annotation.Resource;

import com.sharingif.blockchain.bitcoin.block.model.entity.BlockChain;
import org.springframework.stereotype.Service;

import com.sharingif.blockchain.bitcoin.block.model.entity.TransactionBusiness;
import com.sharingif.blockchain.bitcoin.block.dao.TransactionBusinessDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.bitcoin.block.service.TransactionBusinessService;

import java.math.BigInteger;

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
	public void updateTxStatusToValidSettleStatusToReady(BigInteger blockNumber, String blockHash) {
		TransactionBusiness transactionBusiness = new TransactionBusiness();
		transactionBusiness.setBlockNumber(blockNumber);
		transactionBusiness.setBlockHash(blockHash);

		transactionBusiness.setTxStatus(BlockChain.STATUS_VERIFY_VALID);
		transactionBusiness.setSettleStatus(TransactionBusiness.SETTLE_STATUS_READY);

		transactionBusinessDAO.updateByBlockNumberBlockHash(transactionBusiness);
	}

	@Override
	public void updateTxStatusToInvalidSettleStatusToReady(BigInteger blockNumber, String blockHash) {
		TransactionBusiness transactionBusiness = new TransactionBusiness();
		transactionBusiness.setBlockNumber(blockNumber);
		transactionBusiness.setBlockHash(blockHash);

		transactionBusiness.setTxStatus(BlockChain.STATUS_VERIFY_INVALID);
		transactionBusiness.setSettleStatus(TransactionBusiness.SETTLE_STATUS_READY);

		transactionBusinessDAO.updateByBlockNumberBlockHash(transactionBusiness);

	}


	@Override
	public TransactionBusiness getTransactionBusiness(BigInteger blockNumber, String blockHash, String txHash, BigInteger vioIndex, String type) {
		TransactionBusiness queryTransactionBusiness = new TransactionBusiness();
		queryTransactionBusiness.setBlockNumber(blockNumber);
		queryTransactionBusiness.setBlockHash(blockHash);
		queryTransactionBusiness.setTxHash(txHash);
		queryTransactionBusiness.setVioIndex(vioIndex);
		queryTransactionBusiness.setType(type);

		return transactionBusinessDAO.query(queryTransactionBusiness);
	}
}
