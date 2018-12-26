package com.sharingif.blockchain.ether.block.service.impl;


import com.sharingif.blockchain.ether.block.dao.BlockChainDAO;
import com.sharingif.blockchain.ether.block.model.entity.BlockChain;
import com.sharingif.blockchain.ether.block.service.BlockChainService;
import com.sharingif.blockchain.ether.block.service.EthereumBlockService;
import com.sharingif.blockchain.ether.block.service.TransactionService;
import com.sharingif.cube.batch.core.JobConfig;
import com.sharingif.cube.batch.core.JobModel;
import com.sharingif.cube.batch.core.JobService;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Service
public class BlockChainServiceImpl extends BaseServiceImpl<BlockChain, java.lang.String> implements BlockChainService {
	
	private BlockChainDAO blockChainDAO;
	private TransactionService transactionService;
	private EthereumBlockService ethereumBlockService;
	private String ethValidBlockNumber;
	private JobConfig blockChainSynchingDataJobConfig;
	private JobConfig blockChainSettleBolckSuccessDataJobConfig;
	private JobConfig blockChainSettleBolckFailureDataJobConfig;
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
	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}
	@Resource
	public void setEthereumBlockService(EthereumBlockService ethereumBlockService) {
		this.ethereumBlockService = ethereumBlockService;
	}
	@Value("${eth.valid.block.number}")
	public void setEthValidBlockNumber(String ethValidBlockNumber) {
		this.ethValidBlockNumber = ethValidBlockNumber;
	}
	@Resource
	public void setBlockChainSynchingDataJobConfig(JobConfig blockChainSynchingDataJobConfig) {
		this.blockChainSynchingDataJobConfig = blockChainSynchingDataJobConfig;
	}
	@Resource
	public void setBlockChainSettleBolckSuccessDataJobConfig(JobConfig blockChainSettleBolckSuccessDataJobConfig) {
		this.blockChainSettleBolckSuccessDataJobConfig = blockChainSettleBolckSuccessDataJobConfig;
	}
	@Resource
	public void setBlockChainSettleBolckFailureDataJobConfig(JobConfig blockChainSettleBolckFailureDataJobConfig) {
		this.blockChainSettleBolckFailureDataJobConfig = blockChainSettleBolckFailureDataJobConfig;
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
	public void updateBlockSynching(String id) {
		BlockChain updateBlockChain = new BlockChain();
		updateBlockChain.setId(id);
		updateBlockChain.setStatus(BlockChain.STATUS_BLOCK_SYNCHING);

		blockChainDAO.updateById(updateBlockChain);
	}

	@Override
	public void updateStatusToUnverified(String id) {
		BlockChain updateBlockChain = new BlockChain();
		updateBlockChain.setId(id);
		updateBlockChain.setStatus(BlockChain.STATUS_UNVERIFIED);

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

	@Override
	public void updateStatusToSettling(String id) {
		BlockChain updateBlockChain = new BlockChain();
		updateBlockChain.setId(id);
		updateBlockChain.setStatus(BlockChain.STATUS_SETTLING);
		blockChainDAO.updateById(updateBlockChain);
	}

	@Override
	public void updateStatusToSettled(String id) {
		BlockChain updateBlockChain = new BlockChain();
		updateBlockChain.setId(id);
		updateBlockChain.setStatus(BlockChain.STATUS_SETTLED);
		blockChainDAO.updateById(updateBlockChain);
	}

	@Transactional
	protected void readySyncData(BlockChain blockChain) {
		updateStatusToSettling(blockChain.getId());

		JobModel jobModel = new JobModel();
		jobModel.setLookupPath(blockChainSynchingDataJobConfig.getLookupPath());
		jobModel.setDataId(blockChain.getId());
		jobModel.setPlanExecuteTime(blockChain.getBlockCreateTime());
		jobService.add(null, jobModel);
	}

	@Override
	public void readySyncData() {

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

	@Override
	public void synchingData(String blockChainId) {
		BlockChain blockChain = blockChainDAO.queryById(blockChainId);

		// job业务处理处理成功修改job状态失败导致交易重复调用
		if(!BlockChain.STATUS_BLOCK_SYNCHING.equals(blockChain.getStatus())) {
			return;
		}

		EthBlock.Block block = ethereumBlockService.getBlock(blockChain.getBlockNumber(), true);
		if(!blockChain.getHash().equals(block.getHash())) {
			updateStatusToUnverified(blockChain.getId());
			return;
		}

		List<EthBlock.TransactionResult> transactionResultList = block.getTransactions();

		if(transactionResultList == null || transactionResultList.isEmpty()) {
			updateStatusToUnverified(blockChain.getId());
		}

		for(EthBlock.TransactionResult<EthBlock.TransactionObject> transactionResult : transactionResultList) {
			org.web3j.protocol.core.methods.response.Transaction transaction = transactionResult.get().get();
			TransactionReceipt transactionReceipt = ethereumBlockService.getTransactionReceipt(transaction.getHash());
			transactionService.analysis(transaction, transactionReceipt, blockChain.getBlockCreateTime());
		}

		updateStatusToUnverified(blockChain.getId());
	}

	@Transactional
	protected void validateBolck(BlockChain unverifiedBlockChain, EthBlock.Block block, BigInteger currentBlockNumber) {
		if(unverifiedBlockChain.getHash().equals(block.getHash())) {
			// 修改块有效、交易有效
			updateStatusToVerifyValid(unverifiedBlockChain.getId(), currentBlockNumber);

			transactionService.updateStatusToBlockConfirmedValid(
					unverifiedBlockChain.getBlockNumber()
					, unverifiedBlockChain.getHash()
					, currentBlockNumber.subtract(unverifiedBlockChain.getBlockNumber()).intValue()
			);
		} else {
			// 修改块无效、交易无效、添加新的块同步数据

			updateStatusToVerifyInvalid(unverifiedBlockChain.getId(), currentBlockNumber);

			transactionService.updateStatusToBlockConfirmedInvalid(
					unverifiedBlockChain.getBlockNumber()
					, unverifiedBlockChain.getHash()
					, currentBlockNumber.subtract(unverifiedBlockChain.getBlockNumber()).intValue()
			);

			addUntreatedStatus(block.getNumber(), block.getHash(), block.getTimestamp());
		}
	}

	@Override
	public void validateBolck() {
		BigInteger currentBlockNumber = ethereumBlockService.getBlockNumber();

		BlockChain queryBlockChain = new BlockChain();
		queryBlockChain.setBlockNumber(currentBlockNumber.subtract(new BigInteger(ethValidBlockNumber)));
		queryBlockChain.setStatus(BlockChain.STATUS_UNVERIFIED);
		PaginationCondition<BlockChain> blockChainPaginationCondition = new PaginationCondition<BlockChain>();
		blockChainPaginationCondition.setCondition(queryBlockChain);
		blockChainPaginationCondition.setQueryCount(false);
		blockChainPaginationCondition.setCurrentPage(1);
		blockChainPaginationCondition.setPageSize(20);

		PaginationRepertory<BlockChain> paginationRepertory = blockChainDAO.queryPaginationListByBlockNumberStatus(blockChainPaginationCondition);
		List<BlockChain> blockChainList = paginationRepertory.getPageItems();

		if(blockChainList == null && blockChainList.isEmpty()) {
			return;
		}

		for(BlockChain unverifiedBlockChain : blockChainList) {
			EthBlock.Block block = ethereumBlockService.getBlock(unverifiedBlockChain.getBlockNumber(), false);
			validateBolck(unverifiedBlockChain, block, currentBlockNumber);
		}
	}

	@Transactional
	protected void readySettle(BlockChain verifyValidBlockChain, JobConfig jobConfig) {
		updateBlockSynching(verifyValidBlockChain.getId());

		JobModel jobModel = new JobModel();
		jobModel.setLookupPath(jobConfig.getLookupPath());
		jobModel.setDataId(verifyValidBlockChain.getId());
		jobModel.setPlanExecuteTime(verifyValidBlockChain.getBlockCreateTime());
		jobService.add(null, jobModel);
	}

	protected void readySettleBolck(String status, JobConfig jobConfig) {
		BlockChain queryBlockChain = new BlockChain();
		queryBlockChain.setStatus(status);
		PaginationCondition<BlockChain> blockChainPaginationCondition = new PaginationCondition<BlockChain>();
		blockChainPaginationCondition.setCondition(queryBlockChain);
		blockChainPaginationCondition.setQueryCount(false);
		blockChainPaginationCondition.setCurrentPage(1);
		blockChainPaginationCondition.setPageSize(20);

		PaginationRepertory<BlockChain> paginationRepertory = blockChainDAO.queryPaginationListByBlockNumberStatus(blockChainPaginationCondition);
		List<BlockChain> blockChainList = paginationRepertory.getPageItems();

		if(blockChainList == null && blockChainList.isEmpty()) {
			return;
		}

		for(BlockChain verifyValidBlockChain : blockChainList) {
			readySettle(verifyValidBlockChain, jobConfig);
		}
	}

	@Override
	public void readySettleBolckSuccess() {
		readySettleBolck(BlockChain.STATUS_VERIFY_VALID, blockChainSettleBolckSuccessDataJobConfig);
	}

	@Override
	public void readySettleBolckFailure() {
		readySettleBolck(BlockChain.STATUS_VERIFY_INVALID, blockChainSettleBolckFailureDataJobConfig);
	}

	@Override
	public void settleBolckSuccess(String blockChainId) {
		BlockChain settlingBlockChain = blockChainDAO.queryById(blockChainId);
		if(BlockChain.STATUS_SETTLING.equals(settlingBlockChain.getStatus())) {
			return;
		}

		transactionService.getTransactionBusinessService().settleTransactionSuccess(settlingBlockChain.getBlockNumber(), settlingBlockChain.getHash());

		updateStatusToSettled(blockChainId);
	}

	@Override
	public void settleBolckFailure(String blockChainId) {
		BlockChain settlingBlockChain = blockChainDAO.queryById(blockChainId);
		if(BlockChain.STATUS_SETTLING.equals(settlingBlockChain.getStatus())) {
			return;
		}

		transactionService.getTransactionBusinessService().settleTransactionFailure(settlingBlockChain.getBlockNumber(), settlingBlockChain.getHash());

		updateStatusToSettled(blockChainId);
	}

}
