package com.sharingif.blockchain.ether.block.service.impl;


import com.sharingif.blockchain.ether.block.dao.BlockChainDAO;
import com.sharingif.blockchain.ether.block.model.entity.BlockChain;
import com.sharingif.blockchain.ether.block.model.entity.TransactionTemp;
import com.sharingif.blockchain.ether.block.service.BlockChainService;
import com.sharingif.blockchain.ether.block.service.EthereumBlockService;
import com.sharingif.blockchain.ether.block.service.TransactionService;
import com.sharingif.cube.batch.core.request.JobRequest;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.core.methods.response.EthBlock;

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

	@Override
	public void initializeBlockChain(BigInteger blockNumber, String blockHash, BigInteger blockCreateTime) {
		BlockChain queryBlockChain = new BlockChain();
		queryBlockChain.setBlockNumber(blockNumber);
		queryBlockChain.setHash(blockHash);
		queryBlockChain = blockChainDAO.query(queryBlockChain);
		if(queryBlockChain != null) {
			return;
		}

		BlockChain insertBlockChain = new BlockChain();
		insertBlockChain.setBlockNumber(blockNumber);
		insertBlockChain.setVerifyBlockNumber(blockNumber);
		insertBlockChain.setHash(blockHash);
		insertBlockChain.setStatus(BlockChain.STATUS_INITIALIZE);
		insertBlockChain.setBlockCreateTime(new Date(blockCreateTime.multiply(new BigInteger("1000")).longValue()));

		blockChainDAO.insert(insertBlockChain);
	}

	@Override
	public boolean hasInitializeAndDataSync() {
		BlockChain queryBlockChain = new BlockChain();
		queryBlockChain.setStatus(BlockChain.STATUS_INITIALIZE);
		List<BlockChain> blockChainList = blockChainDAO.queryList(queryBlockChain);

		if(blockChainList != null && !blockChainList.isEmpty()) {
			return true;
		}

		queryBlockChain.setStatus(BlockChain.STATUS_DATA_SYNC);
		blockChainList = blockChainDAO.queryList(queryBlockChain);

		if(blockChainList != null && !blockChainList.isEmpty()) {
			return true;
		}

		return false;
	}

	@Override
	public void updateStatusToReadyDataSync(String id) {
		BlockChain updateBlockChain = new BlockChain();
		updateBlockChain.setId(id);
		updateBlockChain.setStatus(BlockChain.STATUS_READY_DATA_SYNC);

		blockChainDAO.updateById(updateBlockChain);
	}

	@Override
	public void updateStatusToDataSync(String id) {
		BlockChain updateBlockChain = new BlockChain();
		updateBlockChain.setId(id);
		updateBlockChain.setStatus(BlockChain.STATUS_DATA_SYNC);

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
	public void syncDataToTransactionTemp() {
		BlockChain queryBlockChain = new BlockChain();
		queryBlockChain.setStatus(BlockChain.STATUS_INITIALIZE);
		PaginationCondition<BlockChain> blockChainPaginationCondition = new PaginationCondition<BlockChain>();
		blockChainPaginationCondition.setCondition(queryBlockChain);
		blockChainPaginationCondition.setQueryCount(false);
		blockChainPaginationCondition.setCurrentPage(1);
		blockChainPaginationCondition.setPageSize(1);

		PaginationRepertory<BlockChain> paginationRepertory = blockChainDAO.queryPaginationListOrderByBlockNumberAsc(blockChainPaginationCondition);
		List<BlockChain> blockChainList = paginationRepertory.getPageItems();
		if(blockChainList == null || blockChainList.isEmpty()) {
			return;
		}

		BlockChain blockChain = blockChainList.get(0);
		EthBlock.Block block = ethereumBlockService.getBlock(blockChain.getBlockNumber(), false);
		// 如果数据库记录区块hash与当前区块hash不匹配直接修改BlockChain状态为"数据同步中"并返回
		if(!blockChain.getHash().equals(block.getHash())) {
			updateStatusToReadyDataSync(blockChain.getId());
			return;
		}


		List<EthBlock.TransactionResult> transactionResultList = block.getTransactions();
		for(EthBlock.TransactionResult<String> transactionResult : transactionResultList) {
			String txHash = transactionResult.get();
			transactionService.getTransactionTempService().addUnprocessedStatus(blockChain.getBlockNumber(), blockChain.getHash(), txHash);
		}
		updateStatusToReadyDataSync(blockChain.getId());

	}

	@Override
	public void addSyncDataJob() {
		BlockChain queryBlockChain = new BlockChain();
		queryBlockChain.setStatus(BlockChain.STATUS_READY_DATA_SYNC);
		PaginationCondition<BlockChain> blockChainPaginationCondition = new PaginationCondition<BlockChain>();
		blockChainPaginationCondition.setCondition(queryBlockChain);
		blockChainPaginationCondition.setQueryCount(false);
		blockChainPaginationCondition.setCurrentPage(1);
		blockChainPaginationCondition.setPageSize(1);

		PaginationRepertory<BlockChain> paginationRepertory = blockChainDAO.queryPaginationListOrderByBlockNumberAsc(blockChainPaginationCondition);
		List<BlockChain> blockChainList = paginationRepertory.getPageItems();
		if(blockChainList == null || blockChainList.isEmpty()) {
			return;
		}

		BlockChain blockChain = blockChainList.get(0);
		List<TransactionTemp> transactionTempList = transactionService.getTransactionTempService().getUnprocessedStatusTransactionTemp(blockChain.getBlockNumber(), blockChain.getHash());
		if(transactionTempList == null || transactionTempList.isEmpty()) {
			updateStatusToDataSync(blockChain.getId());

			return;
		}

		for(TransactionTemp transactionTemp : transactionTempList){
			transactionService.getTransactionTempService().addTransactionJobAndUpdateStatusToProcessing(transactionTemp);
		}

		updateStatusToDataSync(blockChain.getId());
	}

	@Override
	public void syncData(JobRequest jobRequest) {
		String transactionTempId = jobRequest.getDataId();
		transactionService.syncData(transactionTempId);
	}

	@Transactional
	protected void validateBolck(BlockChain blockChain, EthBlock.Block block, BigInteger blockNumber) {
		if(blockChain.getHash().equals(block.getHash())) {
			// 修改块、交易有效
			updateStatusToVerifyValid(blockChain.getId(), blockNumber);
			transactionService.updateStatusToBlockConfirmedValid(
					blockChain.getBlockNumber()
					, blockChain.getHash()
					, blockNumber.subtract(blockChain.getBlockNumber()).intValue()
			);
		} else {
			// 修改块、交易无效
			updateStatusToVerifyInvalid(blockChain.getId(), blockNumber);
			transactionService.updateStatusToBlockConfirmedInvalid(
					blockChain.getBlockNumber()
					, blockChain.getHash()
					, blockNumber.subtract(blockChain.getBlockNumber()).intValue()
			);
			initializeBlockChain(block.getNumber(), block.getHash(), block.getTimestamp());
		}
	}

	@Override
	public void validateBolck() {
		BigInteger blockNumber = ethereumBlockService.getBlockNumber();
		BlockChain queryBlockChain = new BlockChain();
		queryBlockChain.setBlockNumber(blockNumber.subtract(new BigInteger(ethValidBlockNumber)));
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

		for(BlockChain blockChain : blockChainList) {
			EthBlock.Block block = ethereumBlockService.getBlock(blockChain.getBlockNumber(), false);
			validateBolck(blockChain, block, blockNumber);
		}
	}

}
