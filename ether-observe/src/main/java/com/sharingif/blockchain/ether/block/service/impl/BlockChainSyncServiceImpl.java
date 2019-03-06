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
	protected void addBlockChainSyncBlockChain(EthBlock.Block block) {
		BlockChainSync syncBlockChainSync = new BlockChainSync();
		syncBlockChainSync.setType(BlockChainSync.TYPE_SYNC);
		syncBlockChainSync.setBlockNumber(block.getNumber());
		blockChainSyncDAO.insert(syncBlockChainSync);

		blockChainService.addUntreatedStatus(block.getNumber(), block.getHash(), block.getTimestamp());
	}

	/**
	 * 修改区块信息
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	protected void updateBlockChainSyncBlockChain(EthBlock.Block block) {
		BlockChainSync updateBlockChainSync = new BlockChainSync();
		updateBlockChainSync.setBlockNumber(block.getNumber());
		updateBlockChainSync.setType(BlockChainSync.TYPE_SYNC);
		blockChainSyncDAO.updateByType(updateBlockChainSync);

		blockChainService.addUntreatedStatus(block.getNumber(), block.getHash(), block.getTimestamp());
	}

	@Override
	public BlockChainSync getSyncType() {
		BlockChainSync blockChainSync = new BlockChainSync();
		blockChainSync.setType(BlockChainSync.TYPE_SYNC);

		return blockChainSyncDAO.query(blockChainSync);
	}

	@Override
	public void add(BigInteger blockNumber) {
		EthBlock.Block block = ethereumBlockService.getBlock(blockNumber, false);
		addBlockChainSyncBlockChain(block);
	}

	@Override
	public void update(BigInteger blockNumber) {
		EthBlock.Block block = ethereumBlockService.getBlock(blockNumber, false);
		updateBlockChainSyncBlockChain(block);
	}

}
