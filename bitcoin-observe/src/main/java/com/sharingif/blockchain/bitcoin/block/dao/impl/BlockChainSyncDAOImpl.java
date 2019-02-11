package com.sharingif.blockchain.bitcoin.block.dao.impl;


import com.sharingif.blockchain.bitcoin.app.dao.impl.BaseDAOImpl;
import com.sharingif.blockchain.bitcoin.block.dao.BlockChainSyncDAO;
import com.sharingif.blockchain.bitcoin.block.model.entity.BlockChainSync;
import org.springframework.stereotype.Repository;


@Repository
public class BlockChainSyncDAOImpl extends BaseDAOImpl<BlockChainSync, String> implements BlockChainSyncDAO {

    @Override
    public int updateByType(BlockChainSync blockChainSync) {
        return update("updateByType", blockChainSync);
    }
}
