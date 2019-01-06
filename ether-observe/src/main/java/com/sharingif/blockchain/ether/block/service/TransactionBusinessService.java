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
     * 修改状态为清算中
     * @param id
     * @return
     */
    int updateStatusToSettling(String id);

    /**
     * 修改状态为交易完成通知中
     * @param address
     * @param coinType
     * @param blockNumber
     * @return
     */
    int updateStatusToFinishNoticing(String address, String coinType, BigInteger blockNumber);

    /**
     * 根据地址、币种、大于区块号查询数据条数
     * @param address
     * @param coinType
     * @param blockNumber
     * @return
     */
    int getCountByAddressCoinTypeBlockNumber(String address, String coinType, BigInteger blockNumber);

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

    /**
     * 添加需要处理的交易账号
     */
    void addTransactionBusinessAccount();

}
