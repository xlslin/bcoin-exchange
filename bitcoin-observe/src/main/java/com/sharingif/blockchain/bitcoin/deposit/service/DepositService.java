package com.sharingif.blockchain.bitcoin.deposit.service;


import com.sharingif.blockchain.bitcoin.block.model.entity.TransactionBusiness;

public interface DepositService {

    /**
     * 添加未处理充值
     * @param transactionBusiness
     */
    void addUntreated(TransactionBusiness transactionBusiness);

    /**
     * 充值确认,交易收据状态无效不处理,充值有效不处理，充值无效减去余额
     * @param transactionBusiness
     */
    void depositConfirmed(TransactionBusiness transactionBusiness);

}
