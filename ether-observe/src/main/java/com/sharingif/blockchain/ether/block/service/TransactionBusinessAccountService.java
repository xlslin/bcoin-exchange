package com.sharingif.blockchain.ether.block.service;


import com.sharingif.blockchain.ether.block.model.entity.TransactionBusinessAccount;
import com.sharingif.cube.support.service.base.IBaseService;


public interface TransactionBusinessAccountService extends IBaseService<TransactionBusinessAccount, java.lang.String> {

    /**
     * 添加交易业务账号
     * @param address
     * @param coinType
     * @param contractAddress
     * @return
     */
    boolean addTransactionBusinessAccount(String address, String coinType, String contractAddress);

}
