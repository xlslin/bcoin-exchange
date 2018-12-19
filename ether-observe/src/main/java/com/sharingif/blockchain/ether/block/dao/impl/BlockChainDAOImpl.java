package com.sharingif.blockchain.ether.block.dao.impl;


import com.sharingif.blockchain.ether.app.dao.impl.BaseDAOImpl;
import org.springframework.stereotype.Repository;


import com.sharingif.blockchain.ether.block.model.entity.BlockChain;
import com.sharingif.blockchain.ether.block.dao.BlockChainDAO;


@Repository
public class BlockChainDAOImpl extends BaseDAOImpl<BlockChain, String> implements BlockChainDAO {
	
}
