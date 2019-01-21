package com.sharingif.blockchain.bitcoin.block.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sharingif.blockchain.bitcoin.block.model.entity.BlockChain;
import com.sharingif.blockchain.bitcoin.block.dao.BlockChainDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.bitcoin.block.service.BlockChainService;

@Service
public class BlockChainServiceImpl extends BaseServiceImpl<BlockChain, java.lang.String> implements BlockChainService {
	
	private BlockChainDAO blockChainDAO;

	public BlockChainDAO getBlockChainDAO() {
		return blockChainDAO;
	}
	@Resource
	public void setBlockChainDAO(BlockChainDAO blockChainDAO) {
		super.setBaseDAO(blockChainDAO);
		this.blockChainDAO = blockChainDAO;
	}
	
	
}
