package com.sharingif.blockchain.ether.block.service.impl;


import javax.annotation.Resource;

import com.sharingif.blockchain.ether.job.model.entity.BatchJob;
import com.sharingif.blockchain.ether.job.service.BatchJobService;
import com.sharingif.cube.batch.core.JobConfig;
import com.sharingif.cube.batch.core.JobModel;
import com.sharingif.cube.batch.core.JobService;
import org.springframework.stereotype.Service;

import com.sharingif.blockchain.ether.block.model.entity.TransactionTemp;
import com.sharingif.blockchain.ether.block.dao.TransactionTempDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.ether.block.service.TransactionTempService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Service
public class TransactionTempServiceImpl extends BaseServiceImpl<TransactionTemp, java.lang.String> implements TransactionTempService {
	
	private TransactionTempDAO transactionTempDAO;
	private JobService jobService;
	private JobConfig blockChainSyncDataConfig;

	public TransactionTempDAO getTransactionTempDAO() {
		return transactionTempDAO;
	}
	@Resource
	public void setTransactionTempDAO(TransactionTempDAO transactionTempDAO) {
		super.setBaseDAO(transactionTempDAO);
		this.transactionTempDAO = transactionTempDAO;
	}
	@Resource
	public void setJobService(JobService jobService) {
		this.jobService = jobService;
	}
	@Resource
	public void setBlockChainSyncDataConfig(JobConfig blockChainSyncDataConfig) {
		this.blockChainSyncDataConfig = blockChainSyncDataConfig;
	}

	@Override
	public void addUnprocessedStatus(String blockChainId, BigInteger blockNumber, String blockHash, String txHash) {
		TransactionTemp queryTransactionTemp = new TransactionTemp();
		queryTransactionTemp.setBlockChainId(blockChainId);
		queryTransactionTemp.setBlockNumber(blockNumber);
		queryTransactionTemp.setBlockHash(blockHash);
		queryTransactionTemp.setTxHash(txHash);
		queryTransactionTemp = transactionTempDAO.query(queryTransactionTemp);
		if(queryTransactionTemp != null) {
			return;
		}

		TransactionTemp insertTransactionTemp = new TransactionTemp();
		insertTransactionTemp.setBlockChainId(blockChainId);
		insertTransactionTemp.setBlockNumber(blockNumber);
		insertTransactionTemp.setBlockHash(blockHash);
		insertTransactionTemp.setTxHash(txHash);
		insertTransactionTemp.setTxStatus(TransactionTemp.TX_STATUS_UNPROCESSED);
		transactionTempDAO.insert(insertTransactionTemp);
	}

	@Override
	public int updateStatusToProcessing(String id) {
		TransactionTemp transactionTemp = new TransactionTemp();
		transactionTemp.setId(id);
		transactionTemp.setTxStatus(TransactionTemp.TX_STATUS_PROCESSING);

		return transactionTempDAO.updateById(transactionTemp);
	}

	@Override
	public int updateStatusToProcessed(String id) {
		TransactionTemp transactionTemp = new TransactionTemp();
		transactionTemp.setId(id);
		transactionTemp.setTxStatus(TransactionTemp.TX_STATUS_PROCESSED);

		return transactionTempDAO.updateById(transactionTemp);
	}

	@Override
	public List<TransactionTemp> getUnprocessedStatusTransactionTemp(String blockChainId, BigInteger blockNumber, String blockHash) {
		TransactionTemp transactionTemp = new TransactionTemp();
		transactionTemp.setBlockChainId(blockChainId);
		transactionTemp.setBlockNumber(blockNumber);
		transactionTemp.setBlockHash(blockHash);
		transactionTemp.setTxStatus(TransactionTemp.TX_STATUS_UNPROCESSED);

		return transactionTempDAO.queryList(transactionTemp);
	}

	@Override
	public List<TransactionTemp> getProcessingStatusTransactionTemp(String blockChainId, BigInteger blockNumber, String blockHash) {
		TransactionTemp transactionTemp = new TransactionTemp();
		transactionTemp.setBlockChainId(blockChainId);
		transactionTemp.setBlockNumber(blockNumber);
		transactionTemp.setBlockHash(blockHash);
		transactionTemp.setTxStatus(TransactionTemp.TX_STATUS_PROCESSING);

		return transactionTempDAO.queryList(transactionTemp);
	}

	@Override
	public int deleteProcessedTransactionTemp(String blockChainId, BigInteger blockNumber, String blockHash) {
		TransactionTemp transactionTemp = new TransactionTemp();
		transactionTemp.setBlockChainId(blockChainId);
		transactionTemp.setBlockNumber(blockNumber);
		transactionTemp.setBlockHash(blockHash);
		transactionTemp.setTxStatus(TransactionTemp.TX_STATUS_PROCESSED);

		return transactionTempDAO.deleteByCondition(transactionTemp);
	}

	@Transactional
	@Override
	public void addTransactionJobAndUpdateStatusToProcessing(TransactionTemp transactionTemp) {
		JobModel jobModel = new JobModel();
		jobModel.setLookupPath(blockChainSyncDataConfig.getLookupPath());
		jobModel.setPlanExecuteTime(new Date());
		jobModel.setDataId(transactionTemp.getId());
		jobService.add(null, jobModel);

		updateStatusToProcessing(transactionTemp.getId());
	}
}
