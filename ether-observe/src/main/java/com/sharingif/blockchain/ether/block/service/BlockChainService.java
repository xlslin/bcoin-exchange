package com.sharingif.blockchain.ether.block.service;


import com.sharingif.blockchain.ether.block.model.entity.BlockChain;
import com.sharingif.cube.batch.core.request.JobRequest;
import com.sharingif.cube.support.service.base.IBaseService;

import java.math.BigInteger;


public interface BlockChainService extends IBaseService<BlockChain, java.lang.String> {

    /**
     * 添加初始化区块数据
     * @param blockNumber
     * @param blockHash
     * @param blockCreateTime
     */
    void initializeBlockChain(BigInteger blockNumber, String blockHash, BigInteger blockCreateTime);

    /**
     * 是否有初始化、数据同步中的数据
     * @return
     */
    boolean hasInitializeAndDataSync();

    /**
     * 修改状态为准备数据同步
     * @param id
     */
    void updateStatusToReadyDataSync(String id);

    /**
     * 修改状态为数据同步
     * @param id
     */
    void updateStatusToDataSync(String id);

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
     * 同步块数据到交易临时表
     */
    void syncDataToTransactionTemp();

    /**
     * 添加数据同步job
     */
    void addSyncDataJob();

    /**
     * 块数据同步
     * @param jobRequest
     */
    void syncData(JobRequest jobRequest);

    /**
     * 验证块是否有效
     */
    void validateBolck();
}
