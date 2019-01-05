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
     * @param transactionId
     * @return
     */
    void updateTxStatusToBlockConfirmedValid(String transactionId);

    /**
     * 修改状态为无效
     * @param transactionId
     * @return
     */
    void updateTxStatusToBlockConfirmedInvalid(String transactionId);

}
