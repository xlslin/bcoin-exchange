package com.sharingif.blockchain.ether.block.service;


import com.sharingif.blockchain.ether.block.model.entity.BlockChain;
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
     * 修改状态为区块同步中
     * @param id
     */
    void updateBlockSynching(String id);

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
     * 修改状态为清算中
     * @param id
     */
    void updateStatusToSettling(String id);
    /**
     * 修改状态为已清算
     * @param id
     */
    void updateStatusToSettled(String id);

    /**
     * 准备块数据同步
     */
    void readySyncData();

    /**
     * 块数据同步中
     * @param blockChainId
     */
    void synchingData(String blockChainId);

    /**
     * 验证块是否有效
     */
    void validateBolck();

    /**
     * 准备清算成功块
     */
    void readySettleBolckSuccess();

    /**
     * 准备清算失败块
     */
    void readySettleBolckFailure();

    /**
     * 清算成功块
     * @param blockChainId
     */
    void settleBolckSuccess(String blockChainId);

    /**
     * 清算失败块
     * @param blockChainId
     */
    void settleBolckFailure(String blockChainId);
}
