package com.sharingif.blockchain.bitcoin.block.service;


import com.sharingif.blockchain.bitcoin.block.model.entity.TransactionBusiness;
import com.sharingif.cube.support.service.base.IBaseService;

import java.math.BigInteger;


public interface TransactionBusinessService extends IBaseService<TransactionBusiness, java.lang.String> {

    /**
     * 修改结算状态为清算中
     * @param id
     * @return
     */
    int updateSettleStatusToSettling(String id);

    /**
     * 根据条件查询
     * @param blockNumber
     * @param blockHash
     * @param txHash
     * @param vioIndex
     * @param type
     * @return
     */
    TransactionBusiness getTransactionBusiness(BigInteger blockNumber, String blockHash, String txHash, BigInteger vioIndex, String type);

    /**
     * 修改状态为有效
     * @param blockNumber
     * @param blockHash
     * @return
     */
    void updateTxStatusToValidSettleStatusToReady(BigInteger blockNumber, String blockHash);

    /**
     * 修改状态为无效
     * @param blockNumber
     * @param blockHash
     * @return
     */
    void updateTxStatusToInvalidSettleStatusToReady(BigInteger blockNumber, String blockHash);

    /**
     * 清算
     */
    void settle();

}
