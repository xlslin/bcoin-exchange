package com.sharingif.blockchain.ether.block.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sharingif.blockchain.ether.block.model.entity.BlockChain;
import com.sharingif.blockchain.ether.block.dao.BlockChainDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.ether.block.service.BlockChainService;

import java.math.BigInteger;

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


	@Override
	public void initializeBlockChain(BigInteger blockNumber, String blockHash) {
		BlockChain blockChain = new BlockChain();
		blockChain.setBlockNumber(blockNumber);
		blockChain.setVerifyBlockNumber(blockNumber);
		blockChain.setHash(blockHash);
		blockChain.setStatus(BlockChain.STATUS_INITIALIZE);

		blockChainDAO.insert(blockChain);
	}
}
