package com.sharingif.blockchain.ether.block.service;


import com.sharingif.blockchain.ether.block.model.entity.BlockChainSync;
import com.sharingif.cube.support.service.base.IBaseService;


public interface BlockChainSyncService extends IBaseService<BlockChainSync, java.lang.String> {

    /**
     * 获取同步类型
     * @return
     */
    BlockChainSync getSyncType();

    /**
     * 区块同步
     */
    void sync();

}
