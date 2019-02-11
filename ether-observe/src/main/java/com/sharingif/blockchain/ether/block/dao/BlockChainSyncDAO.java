package com.sharingif.blockchain.ether.block.dao;


import com.sharingif.blockchain.ether.app.dao.BaseDAO;
import com.sharingif.blockchain.ether.block.model.entity.BlockChainSync;

public interface BlockChainSyncDAO extends BaseDAO<BlockChainSync, String> {

    /**
     * 根据类型修改信息
     * @param blockChainSync
     * @return
     */
    int updateByType(BlockChainSync blockChainSync);

}
