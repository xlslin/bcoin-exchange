package com.sharingif.blockchain.bitcoin.block.service.impl;


import javax.annotation.Resource;

import com.sharingif.blockchain.bitcoin.block.service.BitCoinBlockService;
import com.sharingif.blockchain.bitcoin.block.service.BlockChainService;
import org.bitcoincore.api.blockchain.entity.Block;
import org.springframework.stereotype.Service;

import com.sharingif.blockchain.bitcoin.block.model.entity.BlockChainSync;
import com.sharingif.blockchain.bitcoin.block.dao.BlockChainSyncDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.bitcoin.block.service.BlockChainSyncService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Service
public class BlockChainSyncServiceImpl extends BaseServiceImpl<BlockChainSync, java.lang.String> implements BlockChainSyncService {
	
	private BlockChainSyncDAO blockChainSyncDAO;
	private BitCoinBlockService bitCoinBlockService;
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
	public void setBitCoinBlockService(BitCoinBlockService bitCoinBlockService) {
		this.bitCoinBlockService = bitCoinBlockService;
	}
	@Resource
	public void setBlockChainService(BlockChainService blockChainService) {
		this.blockChainService = blockChainService;
	}

	@Override
	public BlockChainSync getSyncType() {
		BlockChainSync blockChainSync = new BlockChainSync();
		blockChainSync.setType(BlockChainSync.TYPE_SYNC);

		return blockChainSyncDAO.query(blockChainSync);
	}

	/**
	 * 添加区块信息
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	protected void addBlockChainSyncBlockChain(BigInteger blockNumber, Block block) {
		BlockChainSync syncBlockChainSync = new BlockChainSync();
		syncBlockChainSync.setType(BlockChainSync.TYPE_SYNC);
		syncBlockChainSync.setBlockNumber(blockNumber);
		blockChainSyncDAO.insert(syncBlockChainSync);

		blockChainService.addUntreatedStatus(blockNumber, block.getHash(), BigInteger.valueOf(block.getTime()));
	}

	/**
	 * 修改区块信息
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	protected void updateBlockChainSyncBlockChain(BigInteger blockNumber, Block block) {

		BlockChainSync updateBlockChainSync = new BlockChainSync();
		updateBlockChainSync.setType(BlockChainSync.TYPE_SYNC);
		updateBlockChainSync.setBlockNumber(blockNumber);
		blockChainSyncDAO.updateByType(updateBlockChainSync);

		blockChainService.addUntreatedStatus(blockNumber, block.getHash(), BigInteger.valueOf(block.getTime()));
	}

	@Override
	public void sync() {
		// 查询数据库区块同步信息
		BlockChainSync blockChainSync = getSyncType();

		// 查询区块链当前区块号
		BigInteger blockNumber = bitCoinBlockService.getBlockNumber();

		// 如果数据库区块同步信息为空，插入当前区块链信息到BlockChainSync表、BlockChain表并返回
		if(blockChainSync == null) {
			Block<List<String>> block = bitCoinBlockService.getBlock(blockNumber);
			addBlockChainSyncBlockChain(blockNumber, block);

			return;
		}

		// 如果数据库区块同步信息不为空比较数据库区块号是否小于区块链当前区块号
		// 如果数据库区块号不小于区块链当前区块号直接返回
		BigInteger currentSyncBlockNumber = blockChainSync.getBlockNumber();
		if(currentSyncBlockNumber.compareTo(blockNumber) >= 0) {
			return;
		}

		// 如果数据库区块号小于区块链当前区块号，递增修改BlockChainSync，添加BlockChain表
		while (currentSyncBlockNumber.compareTo(blockNumber)< 0) {
			currentSyncBlockNumber = currentSyncBlockNumber.add(BigInteger.ONE);
			Block<List<String>> block = bitCoinBlockService.getBlock(currentSyncBlockNumber);
			updateBlockChainSyncBlockChain(currentSyncBlockNumber, block);
		}

	}
}
