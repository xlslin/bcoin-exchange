package com.sharingif.blockchain.ether.block.service;


import com.sharingif.blockchain.ether.block.model.entity.TransactionBusiness;
import com.sharingif.cube.support.service.base.IBaseService;

import java.math.BigInteger;


public interface TransactionBusinessService extends IBaseService<TransactionBusiness, java.lang.String> {

    /**
     * 添加未处理充值
     * @param transactionBusiness
     */
    void addUntreated(TransactionBusiness transactionBusiness);

    /**
     * 修改状态为初始化通知中
     * @param id
     * @return
     */
    int updateStatusToInitNotice(String id);

    /**
     * 修改状态为初始化通知成功
     * @param id
     * @return
     */
    int updateStatusToInitNoticed(String id);

    /**
     * 修改状态为有效
     * @param blockNumber
     * @param blockHash
     * @return
     */
    int updateTxStatusToValid(BigInteger blockNumber, String blockHash);

    /**
     * 修改状态为无效
     * @param blockNumber
     * @param blockHash
     * @return
     */
    int updateTxStatusToInvalid(BigInteger blockNumber, String blockHash);

    /**
     * 清算成功交易
     * @param blockNumber
     * @param blockHash
     */
    void settleTransactionSuccess(BigInteger blockNumber, String blockHash);

    /**
     * 清算失败交易
     * @param blockNumber
     * @param blockHash
     */
    void settleTransactionFailure(BigInteger blockNumber, String blockHash);

}
