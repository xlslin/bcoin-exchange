package com.sharingif.blockchain.bitcoin.block.dao;


import com.sharingif.blockchain.bitcoin.app.dao.BaseDAO;
import com.sharingif.blockchain.bitcoin.block.model.entity.Transaction;


public interface TransactionDAO extends BaseDAO<Transaction, String> {

    /**
     * 根据区块号、区块hash修改
     * @param transaction
     * @return
     */
    int updateByBlockNumberBlockHash(Transaction transaction);

}
