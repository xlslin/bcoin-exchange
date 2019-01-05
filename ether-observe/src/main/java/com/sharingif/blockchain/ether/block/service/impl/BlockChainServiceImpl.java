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
	private JobConfig blockChainValidateBolckJobConfig;
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
	public void setBlockChainValidateBolckJobConfig(JobConfig blockChainValidateBolckJobConfig) {
		this.blockChainValidateBolckJobConfig = blockChainValidateBolckJobConfig;
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

	@Transactional
	protected void readySyncData(BlockChain blockChain) {
		updateBlockSynching(blockChain.getId());

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

		// 如果同步块时发现块hash不匹配，跳过数据同步，状态改为"未验证"
		EthBlock.Block block = ethereumBlockService.getBlock(blockChain.getBlockNumber(), true);
		if(!blockChain.getHash().equals(block.getHash())) {
			updateStatusToUnverified(blockChain.getId());
			return;
		}

		List<EthBlock.TransactionResult> transactionResultList = block.getTransactions();

		// 块中没有交易数据状态改为"未验证"
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

		if(blockChainList == null || blockChainList.isEmpty()) {
			return;
		}

		for(BlockChain unverifiedBlockChain : blockChainList) {
			readyValidateBolck(unverifiedBlockChain);
		}
	}

	@Transactional
	protected void updateStatusToVerifyInvalid(BlockChain unverifiedBlockChain, EthBlock.Block block, BigInteger currentBlockNumber) {
		// 修改块无效、交易无效、添加新的块同步数据
		updateStatusToVerifyInvalid(unverifiedBlockChain.getId(), currentBlockNumber);
		addUntreatedStatus(block.getNumber(), block.getHash(), block.getTimestamp());
	}

	protected void validateBolck(BlockChain unverifiedBlockChain, EthBlock.Block block, BigInteger currentBlockNumber) {
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
		BigInteger currentBlockNumber = ethereumBlockService.getBlockNumber();

		EthBlock.Block block = ethereumBlockService.getBlock(unverifiedBlockChain.getBlockNumber(), false);
		validateBolck(unverifiedBlockChain, block, currentBlockNumber);
	}

}
