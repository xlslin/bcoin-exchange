package com.sharingif.blockchain.ether.withdrawal.service;


import com.sharingif.blockchain.ether.block.model.entity.TransactionBusiness;

public interface WithdrawalService {

    /**
     * 添加未处理提现
     * @param transactionBusiness
     */
    void addUntreated(TransactionBusiness transactionBusiness);

    /**
     * 初始化提现通知
     * @param id
     */
    void initWithdrawalNotice(String id);

    /**
     * 取现处理中
     * @param transactionBusiness
     */
    void processingWithdrawal(TransactionBusiness transactionBusiness);

    /**
     * 取现成功
     * @param transactionBusiness
     */
    void withdrawalSuccess(TransactionBusiness transactionBusiness);

    /**
     * 取现失败
     * @param transactionBusiness
     */
    void withdrawalFailure(TransactionBusiness transactionBusiness);

}
