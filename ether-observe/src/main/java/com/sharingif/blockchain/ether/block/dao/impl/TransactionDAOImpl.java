package com.sharingif.blockchain.ether.block.dao.impl;


import com.sharingif.blockchain.ether.app.dao.impl.BaseDAOImpl;
import com.sharingif.blockchain.ether.block.dao.TransactionDAO;
import com.sharingif.blockchain.ether.block.model.entity.Transaction;
import org.springframework.stereotype.Repository;


@Repository
public class TransactionDAOImpl extends BaseDAOImpl<Transaction, String> implements TransactionDAO {

    @Override
    public int updateByBlockNumberBlockHash(Transaction transaction) {
        return update("updateByBlockNumberBlockHash", transaction);
    }
}
