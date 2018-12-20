package com.sharingif.blockchain.ether.block.service;


import com.sharingif.blockchain.ether.block.model.entity.BlockChain;
import com.sharingif.cube.batch.core.request.JobRequest;
import com.sharingif.cube.support.service.base.IBaseService;

import java.math.BigInteger;
import java.util.List;


public interface BlockChainService extends IBaseService<BlockChain, java.lang.String> {

    /**
     * 添加初始化区块数据
     * @param blockNumber
     * @param blockHash
     * @param blockCreateTime
     */
    void initializeBlockChain(BigInteger blockNumber, String blockHash, BigInteger blockCreateTime);

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
     * 准备块数据同步job
     */
    List<BlockChain> syncDataJob();

    /**
     * 块数据同步
     * @param jobRequest
     */
    void syncData(JobRequest jobRequest);
}
