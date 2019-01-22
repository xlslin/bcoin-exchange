package com.sharingif.blockchain.bitcoin.deposit.service;


import com.sharingif.blockchain.bitcoin.block.model.entity.TransactionBusiness;

public interface DepositService {

    /**
     * 添加未处理充值
     * @param transactionBusiness
     */
    void addUntreated(TransactionBusiness transactionBusiness);


}
