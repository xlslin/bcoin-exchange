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
    void initDepositNotice(String id);

}
