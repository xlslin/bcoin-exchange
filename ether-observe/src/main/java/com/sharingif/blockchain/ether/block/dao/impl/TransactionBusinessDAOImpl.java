package com.sharingif.blockchain.ether.block.dao.impl;


import com.sharingif.blockchain.ether.app.dao.impl.BaseDAOImpl;
import com.sharingif.blockchain.ether.block.dao.TransactionBusinessDAO;
import com.sharingif.blockchain.ether.block.model.entity.TransactionBusiness;
import org.springframework.stereotype.Repository;


@Repository
public class TransactionBusinessDAOImpl extends BaseDAOImpl<TransactionBusiness, String> implements TransactionBusinessDAO {

    @Override
    public int updateByBlockNumberBlockHash(TransactionBusiness transactionBusiness) {
        return update("updateByBlockNumberBlockHash", transactionBusiness);
    }
}
