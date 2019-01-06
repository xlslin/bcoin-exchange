package com.sharingif.blockchain.ether.deposit.service;


import com.sharingif.blockchain.ether.block.model.entity.TransactionBusiness;

public interface DepositService {

    /**
     * 添加未处理充值
     * @param transactionBusiness
     */
    void addUntreated(TransactionBusiness transactionBusiness);

    /**
     * 准备充值通知
     */
    void readyInitNotice();

    /**
     * 初始化充值通知中
     * @param id
     */
    void initNotice(String id);

    /**
     * 充值
     * @param transactionBusiness
     */
    void deposit(TransactionBusiness transactionBusiness);

    /**
     * 充值冲正
     * @param transactionBusiness
     */
    void depositReback(TransactionBusiness transactionBusiness);

    /**
     * 准备充值完成通知
     * @return
     */
    void readyFinishNotice();

    /**
     * 充值完成通知
     * @param id
     * @return
     */
    void finishNotice(String id);

}
