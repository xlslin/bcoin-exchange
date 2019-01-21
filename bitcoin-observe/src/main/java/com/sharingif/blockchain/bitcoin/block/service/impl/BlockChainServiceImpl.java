package com.sharingif.blockchain.bitcoin.block.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sharingif.blockchain.bitcoin.block.model.entity.BlockChain;
import com.sharingif.blockchain.bitcoin.block.dao.BlockChainDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.bitcoin.block.service.BlockChainService;

import java.math.BigInteger;
import java.util.Date;

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
	public void addUntreatedStatus(BigInteger blockNumber, String blockHash, BigInteger blockCreateTime) {
		BlockChain insertBlockChain = new BlockChain();
		insertBlockChain.setBlockNumber(blockNumber);
		insertBlockChain.setVerifyBlockNumber(blockNumber);
		insertBlockChain.setHash(blockHash);
		insertBlockChain.setStatus(BlockChain.STATUS_UNTREATED);
		insertBlockChain.setBlockCreateTime(new Date(blockCreateTime.multiply(new BigInteger("1000")).longValue()));

		blockChainDAO.insert(insertBlockChain);
	}
}
