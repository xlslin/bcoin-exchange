package com.sharingif.blockchain.bitcoin.block.dao.impl;


import com.sharingif.blockchain.bitcoin.app.dao.impl.BaseDAOImpl;
import com.sharingif.blockchain.bitcoin.block.dao.BlockChainDAO;
import com.sharingif.blockchain.bitcoin.block.model.entity.BlockChain;
import org.springframework.stereotype.Repository;


@Repository
public class BlockChainDAOImpl extends BaseDAOImpl<BlockChain, String> implements BlockChainDAO {
	
}
