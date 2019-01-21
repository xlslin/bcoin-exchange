package com.sharingif.blockchain.bitcoin.block.service.impl;


import javax.annotation.Resource;

import com.sharingif.blockchain.bitcoin.block.service.BitCoinBlockService;
import org.bitcoincore.api.blockchain.service.BlockChainApiService;
import org.springframework.stereotype.Service;

import com.sharingif.blockchain.bitcoin.block.model.entity.BlockChainSync;
import com.sharingif.blockchain.bitcoin.block.dao.BlockChainSyncDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.bitcoin.block.service.BlockChainSyncService;

import java.math.BigInteger;

@Service
public class BlockChainSyncServiceImpl extends BaseServiceImpl<BlockChainSync, java.lang.String> implements BlockChainSyncService {
	
	private BlockChainSyncDAO blockChainSyncDAO;
	private BitCoinBlockService bitCoinBlockService;

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

	@Override
	public BlockChainSync getSyncType() {
		BlockChainSync blockChainSync = new BlockChainSync();
		blockChainSync.setType(BlockChainSync.TYPE_SYNC);

		return blockChainSyncDAO.query(blockChainSync);
	}

	@Override
	public void sync() {
		// 查询数据库区块同步信息
		BlockChainSync blockChainSync = getSyncType();

		// 查询区块链当前区块号
		BigInteger blockNumber = bitCoinBlockService.getBlockCount();


	}
}
