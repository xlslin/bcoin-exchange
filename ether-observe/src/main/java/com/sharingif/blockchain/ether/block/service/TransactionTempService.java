package com.sharingif.blockchain.ether.block.service;


import com.sharingif.blockchain.ether.block.model.entity.TransactionTemp;
import com.sharingif.cube.support.service.base.IBaseService;

import java.math.BigInteger;
import java.util.List;


public interface TransactionTempService extends IBaseService<TransactionTemp, java.lang.String> {

    /**
     * 添加处理中临时交易
     * @param blockChainId
     * @param blockNumber
     * @param blockHash
     * @param txHash
     */
    void addUnprocessedStatus(String blockChainId, BigInteger blockNumber, String blockHash, String txHash);

    /**
     * 修改状态为处理中
     * @param id
     * @return
     */
    int updateStatusToProcessing(String id);

    /**
     * 修改状态为已处理
     * @param id
     * @return
     */
    int updateStatusToProcessed(String id);

    /**
     * 根据区块号、区块hash查询未处理状态
     * @param blockNumber
     * @param blockHash
     * @return
     */
    List<TransactionTemp> getUnprocessedStatusTransactionTemp(BigInteger blockNumber, String blockHash);

    /**
     * 根据区块号、区块hash查询处理中的交易
     * @param blockNumber
     * @param blockHash
     * @return
     */
    List<TransactionTemp> getProcessingStatusTransactionTemp(BigInteger blockNumber, String blockHash);

    /**
     * 添加TransactionJob、修改状态为已处理
     */
    void addTransactionJobAndUpdateStatusToProcessing(TransactionTemp transactionTemp);


}
