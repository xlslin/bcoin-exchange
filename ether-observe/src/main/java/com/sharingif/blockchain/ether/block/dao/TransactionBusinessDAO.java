package com.sharingif.blockchain.ether.block.dao;


import com.sharingif.blockchain.ether.app.dao.BaseDAO;
import com.sharingif.blockchain.ether.block.model.entity.TransactionBusiness;


public interface TransactionBusinessDAO extends BaseDAO<TransactionBusiness, String> {

    /**
     * 根据区块数、区块hash修改区块信息
     * @param transactionBusiness
     * @return
     */
    int updateByBlockNumberBlockHash(TransactionBusiness transactionBusiness);

}
