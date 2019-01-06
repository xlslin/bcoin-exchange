package com.sharingif.blockchain.ether.block.dao;


import com.sharingif.blockchain.ether.app.dao.BaseDAO;
import com.sharingif.blockchain.ether.block.model.entity.TransactionBusinessAccount;


public interface TransactionBusinessAccountDAO extends BaseDAO<TransactionBusinessAccount, String> {

    /**
     * 根据地址、币种加锁查询
     * @param address
     * @param coinType
     * @return
     */
    TransactionBusinessAccount queryByAddressCoinTypeForUpdate(String address, String coinType);

}
