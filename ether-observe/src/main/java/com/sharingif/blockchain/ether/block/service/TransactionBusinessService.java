package com.sharingif.blockchain.ether.block.service;


import com.sharingif.blockchain.ether.block.model.entity.TransactionBusiness;
import com.sharingif.cube.support.service.base.IBaseService;


public interface TransactionBusinessService extends IBaseService<TransactionBusiness, java.lang.String> {

    /**
     * 添加未处理充值
     * @param transactionBusiness
     */
    void addUntreatedDeposit(TransactionBusiness transactionBusiness);

    /**
     * 添加未处理提现
     * @param transactionBusiness
     */
    void addUntreatedWithdrawal(TransactionBusiness transactionBusiness);

}
