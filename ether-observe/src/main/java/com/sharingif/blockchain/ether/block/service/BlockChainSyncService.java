package com.sharingif.blockchain.ether.block.service;


import com.sharingif.blockchain.ether.block.model.entity.BlockChainSync;
import com.sharingif.cube.support.service.base.IBaseService;

import java.math.BigInteger;


public interface BlockChainSyncService extends IBaseService<BlockChainSync, java.lang.String> {

    /**
     * 获取同步类型
     * @return
     */
    BlockChainSync getSyncType();

    /**
     * 添加块信息
     * @param blockNumber
     */
    void add(BigInteger blockNumber);

    /**
     * 修改块信息
     * @param blockNumber
     */
    void update(BigInteger blockNumber);

}
