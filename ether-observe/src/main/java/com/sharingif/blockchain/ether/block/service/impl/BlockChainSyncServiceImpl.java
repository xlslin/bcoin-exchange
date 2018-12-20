package com.sharingif.blockchain.ether.block.service.impl;


import com.sharingif.blockchain.ether.block.dao.BlockChainSyncDAO;
import com.sharingif.blockchain.ether.block.model.entity.BlockChainSync;
import com.sharingif.blockchain.ether.block.service.BlockChainService;
import com.sharingif.blockchain.ether.block.service.BlockChainSyncService;
import com.sharingif.blockchain.ether.block.service.EthereumBlockService;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.core.methods.response.EthBlock;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;


@Service
public class BlockChainSyncServiceImpl extends BaseServiceImpl<BlockChainSync, String> implements BlockChainSyncService {
	
	private BlockChainSyncDAO blockChainSyncDAO;
	private EthereumBlockService ethereumBlockService;
	private BlockChainService blockChainService;

	public BlockChainSyncDAO getBlockChainSyncDAO() {
		return blockChainSyncDAO;
	}
	@Resource
	public void setBlockChainSyncDAO(BlockChainSyncDAO blockChainSyncDAO) {
		super.setBaseDAO(blockChainSyncDAO);
		this.blockChainSyncDAO = blockChainSyncDAO;
	}
	@Resource
	public void setEthereumBlockService(EthereumBlockService ethereumBlockService) {
		this.ethereumBlockService = ethereumBlockService;
	}
	@Resource
	public void setBlockChainService(BlockChainService blockChainService) {
		this.blockChainService = blockChainService;
	}

	/**
	 * 添加区块信息
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	protected void addBlockChainInfo(BigInteger blockNumber) {
		BlockChainSync blockChainSync = new BlockChainSync();
		blockChainSync.setCurrentSyncBlockNumber(blockNumber);
		blockChainSyncDAO.insert(blockChainSync);

		EthBlock.Block block = ethereumBlockService.getBlock(blockNumber, false);

		blockChainService.initializeBlockChain(blockNumber, block.getHash(), block.getTimestamp());
	}

	/**
	 * 修改区块信息
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	protected void updateBlockChainInfo(BigInteger blockNumber) {
		List<BlockChainSync> blockChainSyncList = blockChainSyncDAO.queryAll();
		BlockChainSync queryBlockChainSync = blockChainSyncList.get(0);

		BlockChainSync updateBlockChainSync = new BlockChainSync();
		updateBlockChainSync.setId(queryBlockChainSync.getId());
		updateBlockChainSync.setCurrentSyncBlockNumber(blockNumber);
		blockChainSyncDAO.updateById(updateBlockChainSync);

		EthBlock.Block block = ethereumBlockService.getBlock(blockNumber, false);

		blockChainService.initializeBlockChain(blockNumber, block.getHash(), block.getTimestamp());
	}

	@Override
	public void sync() {
		// 查询数据库区块同步信息
		List<BlockChainSync> blockChainSyncList = blockChainSyncDAO.queryAll();

		// 查询区块链当前区块号
		BigInteger blockNumber = ethereumBlockService.getBlockNumber();

		// 如果数据库区块同步信息为空，插入当前区块链信息到BlockChainSync表、BlockChain表并返回
		if(blockChainSyncList == null || blockChainSyncList.isEmpty()) {
			addBlockChainInfo(blockNumber);

			return;
		}

		// 如果数据库区块同步信息不为空比较数据库区块号是否小于区块链当前区块号
		BlockChainSync blockChainSync = blockChainSyncList.get(0);
		// 如果数据库区块号不小于区块链当前区块号直接返回
		BigInteger currentSyncBlockNumber = blockChainSync.getCurrentSyncBlockNumber();
		if(currentSyncBlockNumber.compareTo(blockNumber) >= 0) {
			return;
		}

		// 如果数据库区块号小于区块链当前区块号，递增修改BlockChainSync，添加BlockChain表
		while (currentSyncBlockNumber.compareTo(blockNumber)< 0) {
			currentSyncBlockNumber = currentSyncBlockNumber.add(BigInteger.ONE);
			updateBlockChainInfo(currentSyncBlockNumber);
		}
	}


}
