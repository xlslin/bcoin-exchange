package com.sharingif.blockchain.bitcoin.block.service;


import com.sharingif.blockchain.bitcoin.block.model.entity.TransactionBusiness;
import com.sharingif.cube.support.service.base.IBaseService;

import java.math.BigInteger;
import java.util.List;


public interface TransactionBusinessService extends IBaseService<TransactionBusiness, java.lang.String> {

    /**
     * 修改结算状态为清算中
     * @param id
     * @return
     */
    int updateSettleStatusToSettling(String id);

    /**
     * 修改状态为清算完成
     * @param address
     * @param coinType
     * @param blockNumber
     * @return
     */
    int updateSettleStatusToSettled(String address, String coinType, BigInteger blockNumber);

    /**
     * 根据地址、币种、查询未结算数据条数
     * @param address
     * @param coinType
     * @return
     */
    int getUnsettledCountByAddressCoinTypeSettleStatus(String address, String coinType);

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
     * 获取准备清算数据
     * @return
     */
    List<TransactionBusiness> getSettleStatusReady();

    /**
     * 清算
     * @param transactionBusinessList
     */
    void settle(List<TransactionBusiness> transactionBusinessList);

}
