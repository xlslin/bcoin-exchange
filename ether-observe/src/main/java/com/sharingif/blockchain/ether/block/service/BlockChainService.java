package com.sharingif.blockchain.ether.block.service;


import com.sharingif.blockchain.ether.block.model.entity.BlockChain;
import com.sharingif.blockchain.ether.block.model.entity.BlockTransaction;
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

    /**
     * 修改状态为未验证
     * @param id
     */
    void updateStatusToUnverified(String id);

    /**
     * 修改状态为区块验证有效
     * @param id
     * @param verifyBlockNumber
     */
    void updateStatusToVerifyValid(String id, BigInteger verifyBlockNumber);

    /**
     * 修改状态为区块验证无效
     * @param id
     * @param verifyBlockNumber
     */
    void updateStatusToVerifyInvalid(String id, BigInteger verifyBlockNumber);

    /**
     * 块数据同步
     */
    void syncData();

    /**
     * 处理块数据同步完成
     * @param blockTransaction
     */
    void syncDataFinish(BlockTransaction blockTransaction);

    /**
     * 验证块是否有效
     */
    void validateBolck();
}
