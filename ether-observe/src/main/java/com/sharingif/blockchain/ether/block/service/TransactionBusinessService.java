package com.sharingif.blockchain.ether.block.service;


import com.sharingif.blockchain.ether.block.model.entity.TransactionBusiness;
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
