package com.sharingif.blockchain.bitcoin.block.service;


import com.sharingif.blockchain.bitcoin.block.model.entity.BlockChain;
import com.sharingif.cube.support.service.base.IBaseService;

import java.math.BigInteger;


public interface BlockChainService extends IBaseService<BlockChain, java.lang.String> {

    /**
     * 添加未处理状态区块数据
     * @param blockNumber
     * @param blockHash
     * @param blockCreateTime
     */
    void addUntreatedStatus(BigInteger blockNumber, String blockHash, BigInteger blockCreateTime);

}