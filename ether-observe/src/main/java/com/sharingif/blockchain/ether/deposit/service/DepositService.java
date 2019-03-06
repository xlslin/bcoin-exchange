package com.sharingif.blockchain.ether.deposit.service;


import com.sharingif.blockchain.ether.block.model.entity.TransactionBusiness;

import java.util.List;

public interface DepositService {

    /**
     * 添加未处理充值
     * @param transactionBusiness
     */
    void addUntreated(TransactionBusiness transactionBusiness);

    /**
     * 准备充值通知
     * @param transactionBusinessList
     */
    void readyInitNotice(List<TransactionBusiness> transactionBusinessList);

    /**
     * 初始化充值通知中
     * @param id
     */
    void initNotice(String id);

    /**
     * 充值，交易收据状态无效不处理
     * @param transactionBusiness
     */
    void deposit(TransactionBusiness transactionBusiness);

    /**
     * 充值确认,交易收据状态无效不处理,充值有效不处理，充值无效减去余额
     * @param transactionBusiness
     * @param txStatus
     */
    void depositConfirmed(TransactionBusiness transactionBusiness, String txStatus);

    /**
     * 准备充值完成通知
     * @param transactionBusinessList
     */
    void readyFinishNotice(List<TransactionBusiness> transactionBusinessList);

    /**
     * 充值完成通知
     * @param id
     * @return
     */
    void finishNotice(String id);

}
