package com.sharingif.blockchain.bitcoin.block.service.impl;


import com.sharingif.blockchain.bitcoin.block.dao.BlockChainDAO;
import com.sharingif.blockchain.bitcoin.block.model.entity.BlockChain;
import com.sharingif.blockchain.bitcoin.block.model.entity.BlockTransaction;
import com.sharingif.blockchain.bitcoin.block.service.BitCoinBlockService;
import com.sharingif.blockchain.bitcoin.block.service.BlockChainService;
import com.sharingif.blockchain.bitcoin.block.service.TransactionService;
import com.sharingif.cube.batch.core.JobConfig;
import com.sharingif.cube.batch.core.handler.MultithreadDispatcherHandler;
import com.sharingif.cube.batch.core.request.JobRequest;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import org.bitcoincore.api.blockchain.entity.Block;
import org.bitcoincore.api.rawtransactions.entity.Transaction;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class BlockChainServiceImpl extends BaseServiceImpl<BlockChain, java.lang.String> implements BlockChainService, ApplicationContextAware {

	private Queue<Transaction> blockTransactionQueue = new ConcurrentLinkedQueue<Transaction>();
	
	private BlockChainDAO blockChainDAO;
	private BitCoinBlockService bitCoinBlockService;
	private MultithreadDispatcherHandler jobMultithreadDispatcherHandler;
	private JobConfig blockChainSynchingDataJobConfig;
	private TransactionService transactionService;

	public BlockChainDAO getBlockChainDAO() {
		return blockChainDAO;
	}
	@Resource
	public void setBlockChainDAO(BlockChainDAO blockChainDAO) {
		super.setBaseDAO(blockChainDAO);
		this.blockChainDAO = blockChainDAO;
	}
	@Resource
	public void setBitCoinBlockService(BitCoinBlockService bitCoinBlockService) {
		this.bitCoinBlockService = bitCoinBlockService;
	}
	@Resource
	public void setBlockChainSynchingDataJobConfig(JobConfig blockChainSynchingDataJobConfig) {
		this.blockChainSynchingDataJobConfig = blockChainSynchingDataJobConfig;
	}
	@Resource
	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@Override
	public void addUntreatedStatus(BigInteger blockNumber, String blockHash, BigInteger blockCreateTime) {
		BlockChain insertBlockChain = new BlockChain();
		insertBlockChain.setBlockNumber(blockNumber);
		insertBlockChain.setVerifyBlockNumber(blockNumber);
		insertBlockChain.setHash(blockHash);
		insertBlockChain.setStatus(BlockChain.STATUS_UNTREATED);
		insertBlockChain.setBlockCreateTime(new Date(blockCreateTime.multiply(new BigInteger("1000")).longValue()));

		blockChainDAO.insert(insertBlockChain);
	}

	@Override
	public void updateStatusToUnverified(String id) {
		BlockChain updateBlockChain = new BlockChain();
		updateBlockChain.setId(id);
		updateBlockChain.setStatus(BlockChain.STATUS_UNVERIFIED);

		blockChainDAO.updateById(updateBlockChain);
	}

	protected void readySyncData(BlockChain blockChain) {
		// 如果同步块时发现块hash不匹配，跳过数据同步，状态改为"未验证"
		String blockHash = bitCoinBlockService.getBlockHash(blockChain.getBlockNumber());
		if(!blockChain.getHash().equals(blockHash)) {
			updateStatusToUnverified(blockChain.getId());
			return;
		}

		// 块中没有交易数据状态改为"未验证"
		Block<List<Transaction>> block = bitCoinBlockService.getBlockFullTransaction(blockHash);
		List<Transaction> transactionList = block.getTx();
		if(transactionList == null || transactionList.isEmpty()) {
			updateStatusToUnverified(blockChain.getId());
			return;
		}

		for(Transaction transaction : transactionList) {
			blockTransactionQueue.add(transaction);

			BlockTransaction blockTransaction = new BlockTransaction();
			blockTransaction.setBlockChain(blockChain);
			blockTransaction.setTransaction(transaction);
			JobRequest<BlockTransaction> jobRequest = new JobRequest<BlockTransaction>();
			jobRequest.setLookupPath(blockChainSynchingDataJobConfig.getLookupPath());
			jobRequest.setData(blockTransaction);

			jobMultithreadDispatcherHandler.doDispatch(jobRequest);
		}
	}

	@Override
	public void readySyncData() {
		if(blockTransactionQueue.size() !=0) {
			return;
		}

		BlockChain queryBlockChain = new BlockChain();
		queryBlockChain.setStatus(BlockChain.STATUS_UNTREATED);
		PaginationCondition<BlockChain> blockChainPaginationCondition = new PaginationCondition<BlockChain>();
		blockChainPaginationCondition.setCondition(queryBlockChain);
		blockChainPaginationCondition.setQueryCount(false);
		blockChainPaginationCondition.setCurrentPage(1);
		blockChainPaginationCondition.setPageSize(20);

		PaginationRepertory<BlockChain> paginationRepertory = blockChainDAO.queryPaginationListOrderByBlockNumberAsc(blockChainPaginationCondition);
		List<BlockChain> blockChainList = paginationRepertory.getPageItems();
		if(blockChainList == null || blockChainList.isEmpty()) {
			return;
		}

		for(BlockChain blockChain : blockChainList) {
			readySyncData(blockChain);
		}
	}

	protected synchronized void synchingData(String blockChainId, Transaction transaction) {
		if(blockTransactionQueue.size() == 1) {
			updateStatusToUnverified(blockChainId);
		}
		blockTransactionQueue.remove(transaction);
	}

	@Override
	public void synchingData(BlockTransaction blockTransaction) {
		BlockChain blockChain = blockTransaction.getBlockChain();
		Transaction transaction = blockTransaction.getTransaction();

		transactionService.analysis(transaction, blockChain.getBlockNumber(), blockChain.getHash(), blockChain.getBlockCreateTime());

		synchingData(blockChain.getId(), transaction);

	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		jobMultithreadDispatcherHandler = applicationContext.getBean(MultithreadDispatcherHandler.class);
	}
}
