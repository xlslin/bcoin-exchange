package com.sharingif.blockchain.ether.block.dao.impl;


import com.sharingif.blockchain.ether.app.dao.impl.BaseDAOImpl;
import com.sharingif.blockchain.ether.block.dao.TransactionBusinessDAO;
import com.sharingif.blockchain.ether.block.model.entity.TransactionBusiness;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;


@Repository
public class TransactionBusinessDAOImpl extends BaseDAOImpl<TransactionBusiness, String> implements TransactionBusinessDAO {


    @Override
    public int updateStatusByAddressCoinTypeBlockNumberTxStatus(String status, String address, String coinType, BigInteger blockNumber, String txStatus) {
        TransactionBusiness transactionBusiness = new TransactionBusiness();
        transactionBusiness.setStatus(status);

        transactionBusiness.setTxFrom(address);
        transactionBusiness.setTxTo(address);
        transactionBusiness.setCoinType(coinType);
        transactionBusiness.setBlockNumber(blockNumber);
        transactionBusiness.setTxStatus(txStatus);

        return update("updateStatusByAddressCoinTypeBlockNumberTxStatus", transactionBusiness);
    }

    @Override
    public int queryCountByAddressCoinTypeBlockNumber(String address, String coinType, BigInteger blockNumber) {
        TransactionBusiness transactionBusiness = new TransactionBusiness();

        transactionBusiness.setTxFrom(address);
        transactionBusiness.setTxTo(address);
        transactionBusiness.setCoinType(coinType);
        transactionBusiness.setBlockNumber(blockNumber);

        return query("queryCountByAddressCoinTypeBlockNumber", transactionBusiness, Integer.TYPE);
    }
}
