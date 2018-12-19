package com.sharingif.blockchain.ether.block.dao.impl;


import com.sharingif.blockchain.ether.app.dao.impl.BaseDAOImpl;
import org.springframework.stereotype.Repository;


import com.sharingif.blockchain.ether.block.model.entity.BlockChainSync;
import com.sharingif.blockchain.ether.block.dao.BlockChainSyncDAO;


@Repository
public class BlockChainSyncDAOImpl extends BaseDAOImpl<BlockChainSync, String> implements BlockChainSyncDAO {
	
}
