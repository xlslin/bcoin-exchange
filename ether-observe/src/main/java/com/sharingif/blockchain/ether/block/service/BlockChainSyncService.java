package com.sharingif.blockchain.ether.block.service;


import com.sharingif.blockchain.ether.block.model.entity.BlockChainSync;
import com.sharingif.cube.support.service.base.IBaseService;

import java.math.BigInteger;


public interface BlockChainSyncService extends IBaseService<BlockChainSync, java.lang.String> {

    /**
     * 添加区块信息
     * @param blockNumber
     */
    void addBlockChainInfo(BigInteger blockNumber);

    /**
     * 修改区块信息
     * @param blockNumber
     */
    void updateBlockChainInfo(BigInteger blockNumber);

    /**
     * 区块同步
     */
    void sync();

}
