package com.sharingif.blockchain.bitcoin.block.service;


import com.sharingif.blockchain.bitcoin.block.model.entity.TransactionBusinessAccount;
import com.sharingif.cube.support.service.base.IBaseService;

import java.util.List;


public interface TransactionBusinessAccountService extends IBaseService<TransactionBusinessAccount, java.lang.String> {

    /**
     * 添加交易业务账号
     * @param address
     * @param coinType
     * @return
     */
    boolean addTransactionBusinessAccount(String address, String coinType);

    /**
     * 清算账户
     * @param transactionBusinessAccountList
     */
    void settleAccounts(List<TransactionBusinessAccount> transactionBusinessAccountList);

}
