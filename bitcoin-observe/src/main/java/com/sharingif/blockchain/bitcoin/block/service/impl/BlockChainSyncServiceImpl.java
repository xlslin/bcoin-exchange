package com.sharingif.blockchain.bitcoin.block.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sharingif.blockchain.bitcoin.block.model.entity.BlockChainSync;
import com.sharingif.blockchain.bitcoin.block.dao.BlockChainSyncDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.bitcoin.block.service.BlockChainSyncService;

@Service
public class BlockChainSyncServiceImpl extends BaseServiceImpl<BlockChainSync, java.lang.String> implements BlockChainSyncService {
	
	private BlockChainSyncDAO blockChainSyncDAO;

	public BlockChainSyncDAO getBlockChainSyncDAO() {
		return blockChainSyncDAO;
	}
	@Resource
	public void setBlockChainSyncDAO(BlockChainSyncDAO blockChainSyncDAO) {
		super.setBaseDAO(blockChainSyncDAO);
		this.blockChainSyncDAO = blockChainSyncDAO;
	}
	
	
}
