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
	public void add(BigInteger blockNumber) {
		Block<List<String>> block = bitCoinBlockService.getBlock(blockNumber);
		addBlockChainSyncBlockChain(blockNumber, block);
	}

	@Override
	public void update(BigInteger blockNumber) {
		Block<List<String>> block = bitCoinBlockService.getBlock(blockNumber);
		updateBlockChainSyncBlockChain(blockNumber, block);
	}
}
