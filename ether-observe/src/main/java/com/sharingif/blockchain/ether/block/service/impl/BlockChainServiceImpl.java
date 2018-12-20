package com.sharingif.blockchain.ether.block.service.impl;


import com.sharingif.blockchain.ether.block.dao.BlockChainDAO;
import com.sharingif.blockchain.ether.block.model.entity.BlockChain;
import com.sharingif.blockchain.ether.block.service.BlockChainService;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

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

	@Override
	public void updateStatusToDataSync(String id) {
		BlockChain updateBlockChain = new BlockChain();
		updateBlockChain.setId(id);
		updateBlockChain.setStatus(BlockChain.STATUS_DATA_SYNC);

		blockChainDAO.updateById(updateBlockChain);
	}


	@Override
	@Transactional
	public List<BlockChain> syncDataJob() {
		BlockChain queryBlockChain = new BlockChain();
		queryBlockChain.setStatus(BlockChain.STATUS_INITIALIZE);
		PaginationCondition<BlockChain> blockChainPaginationCondition = new PaginationCondition<BlockChain>();
		blockChainPaginationCondition.setCondition(queryBlockChain);
		blockChainPaginationCondition.setQueryCount(false);
		blockChainPaginationCondition.setCurrentPage(1);
		blockChainPaginationCondition.setPageSize(10);

		PaginationRepertory<BlockChain> paginationRepertory = blockChainDAO.queryPagination(blockChainPaginationCondition);
		List<BlockChain> blockChainList = paginationRepertory.getPageItems();
		if(blockChainList == null && blockChainList.isEmpty()) {
			return null;
		}

		for(BlockChain BlockChain : blockChainList) {
			updateStatusToDataSync(BlockChain.getId());
		}

		return blockChainList;
	}
}
