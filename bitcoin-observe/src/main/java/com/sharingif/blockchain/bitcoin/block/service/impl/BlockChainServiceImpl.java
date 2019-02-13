package com.sharingif.blockchain.bitcoin.block.service.impl;


import com.sharingif.blockchain.bitcoin.block.dao.BlockChainDAO;
import com.sharingif.blockchain.bitcoin.block.model.entity.BlockChain;
import com.sharingif.blockchain.bitcoin.block.model.entity.BlockTransaction;
import com.sharingif.blockchain.bitcoin.block.service.BitCoinBlockService;
import com.sharingif.blockchain.bitcoin.block.service.BlockChainService;
import com.sharingif.blockchain.bitcoin.block.service.TransactionService;
import com.sharingif.cube.batch.core.JobConfig;
import com.sharingif.cube.batch.core.JobModel;
import com.sharingif.cube.batch.core.JobService;
import com.sharingif.cube.batch.core.handler.MultithreadDispatcherHandler;
import com.sharingif.cube.batch.core.request.JobRequest;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import org.bitcoincore.api.blockchain.entity.Block;
import org.bitcoincore.api.rawtransactions.entity.Transaction;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.IntStream;

@Service
public class BlockChainServiceImpl extends BaseServiceImpl<BlockChain, java.lang.String> implements BlockChainService, ApplicationContextAware {

	private Queue<Transaction> blockTransactionQueue = new ConcurrentLinkedQueue<Transaction>();
	
	private BlockChainDAO blockChainDAO;
	private BitCoinBlockService bitCoinBlockService;
	private MultithreadDispatcherHandler jobMultithreadDispatcherHandler;
	private JobConfig blockChainSynchingDataJobConfig;
	private JobConfig blockChainValidateBolckJobConfig;
	private TransactionService transactionService;
	private String btcValidBlockNumber;
	private JobService jobService;

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
	public void setBlockChainValidateBolckJobConfig(JobConfig blockChainValidateBolckJobConfig) {
		this.blockChainValidateBolckJobConfig = blockChainValidateBolckJobConfig;
	}
	@Resource
	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}
	@Value("${btc.valid.block.number}")
	public void setBtcValidBlockNumber(String btcValidBlockNumber) {
		this.btcValidBlockNumber = btcValidBlockNumber;
	}
	@Resource
	public void setJobService(JobService jobService) {
		this.jobService = jobService;
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

	@Override
	public void updateStatusToVerifying(String id) {
		BlockChain updateBlockChain = new BlockChain();
		updateBlockChain.setId(id);
		updateBlockChain.setStatus(BlockChain.STATUS_VERIFYING);

		blockChainDAO.updateById(updateBlockChain);
	}

	@Override
	public void updateStatusToVerifyValid(String id, BigInteger verifyBlockNumber) {
		BlockChain updateBlockChain = new BlockChain();
		updateBlockChain.setId(id);
		updateBlockChain.setVerifyBlockNumber(verifyBlockNumber);
		updateBlockChain.setStatus(BlockChain.STATUS_VERIFY_VALID);

		blockChainDAO.updateById(updateBlockChain);
	}

	@Override
	public void updateStatusToVerifyInvalid(String id, BigInteger verifyBlockNumber) {
		BlockChain updateBlockChain = new BlockChain();
		updateBlockChain.setId(id);
		updateBlockChain.setVerifyBlockNumber(verifyBlockNumber);
		updateBlockChain.setStatus(BlockChain.STATUS_VERIFY_INVALID);
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

		IntStream.range(0, transactionList.size()).forEach(index->{
			Transaction transaction = transactionList.get(index);

			blockTransactionQueue.add(transaction);

			BlockTransaction blockTransaction = new BlockTransaction();
			blockTransaction.setBlockChain(blockChain);
			blockTransaction.setTxIndex(new BigInteger(String.valueOf(index)));
			blockTransaction.setTransaction(transaction);
			JobRequest<BlockTransaction> jobRequest = new JobRequest<BlockTransaction>();
			jobRequest.setLookupPath(blockChainSynchingDataJobConfig.getLookupPath());
			jobRequest.setData(blockTransaction);

			jobMultithreadDispatcherHandler.doDispatch(jobRequest);
		});
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

		transactionService.analysis(transaction, blockChain.getBlockNumber(), blockChain.getHash(), blockChain.getBlockCreateTime(), blockTransaction.getTxIndex());

		synchingData(blockChain.getId(), transaction);

	}

	@Transactional
	protected void readyValidateBolck(BlockChain unverifiedBlockChain) {
		updateStatusToVerifying(unverifiedBlockChain.getId());

		JobModel jobModel = new JobModel();
		jobModel.setLookupPath(blockChainValidateBolckJobConfig.getLookupPath());
		jobModel.setDataId(unverifiedBlockChain.getId());
		jobModel.setPlanExecuteTime(unverifiedBlockChain.getBlockCreateTime());
		jobService.add(null, jobModel);
	}

	@Override
	public void readyValidateBolck() {
		BigInteger currentBlockNumber = bitCoinBlockService.getBlockNumber();

		BlockChain queryBlockChain = new BlockChain();
		queryBlockChain.setBlockNumber(currentBlockNumber.subtract(new BigInteger(btcValidBlockNumber)));
		queryBlockChain.setStatus(BlockChain.STATUS_UNVERIFIED);
		PaginationCondition<BlockChain> blockChainPaginationCondition = new PaginationCondition<BlockChain>();
		blockChainPaginationCondition.setCondition(queryBlockChain);
		blockChainPaginationCondition.setQueryCount(false);
		blockChainPaginationCondition.setCurrentPage(1);
		blockChainPaginationCondition.setPageSize(20);

		PaginationRepertory<BlockChain> paginationRepertory = blockChainDAO.queryPaginationListByBlockNumberStatus(blockChainPaginationCondition);
		List<BlockChain> blockChainList = paginationRepertory.getPageItems();

		if(blockChainList == null || blockChainList.isEmpty()) {
			return;
		}

		for(BlockChain unverifiedBlockChain : blockChainList) {
			readyValidateBolck(unverifiedBlockChain);
		}
	}

	@Transactional
	protected void updateStatusToVerifyInvalid(BlockChain unverifiedBlockChain, Block block, BigInteger currentBlockNumber) {
		// 修改块无效、交易无效、添加新的块同步数据
		updateStatusToVerifyInvalid(unverifiedBlockChain.getId(), currentBlockNumber);
		addUntreatedStatus(unverifiedBlockChain.getBlockNumber(), block.getHash(), BigInteger.valueOf(block.getTime()));
	}

	protected void validateBolck(BlockChain unverifiedBlockChain, Block block, BigInteger currentBlockNumber) {
		if(unverifiedBlockChain.getHash().equals(block.getHash())) {
			transactionService.updateTxStatusToBlockConfirmedValid(
					unverifiedBlockChain.getBlockNumber()
					, unverifiedBlockChain.getHash()
					, currentBlockNumber.subtract(unverifiedBlockChain.getBlockNumber()).intValue()
			);

			// 修改块有效、交易有效
			updateStatusToVerifyValid(unverifiedBlockChain.getId(), currentBlockNumber);
		} else {

			transactionService.updateTxStatusToBlockConfirmedInvalid(
					unverifiedBlockChain.getBlockNumber()
					, unverifiedBlockChain.getHash()
					, currentBlockNumber.subtract(unverifiedBlockChain.getBlockNumber()).intValue()
			);

			updateStatusToVerifyInvalid(unverifiedBlockChain, block, currentBlockNumber);
		}
	}

	@Override
	public void validateBolck(String blockChainId) {
		BlockChain blockChain = blockChainDAO.queryById(blockChainId);

		// job业务处理处理成功修改job状态失败导致交易重复调用
		if(!BlockChain.STATUS_VERIFYING.equals(blockChain.getStatus())) {
			return;
		}

		BlockChain unverifiedBlockChain = blockChainDAO.queryById(blockChainId);
		BigInteger currentBlockNumber = bitCoinBlockService.getBlockNumber();

		Block block = bitCoinBlockService.getBlock(unverifiedBlockChain.getBlockNumber());
		validateBolck(unverifiedBlockChain, block, currentBlockNumber);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		jobMultithreadDispatcherHandler = applicationContext.getBean(MultithreadDispatcherHandler.class);
	}
}
