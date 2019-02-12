package com.sharingif.blockchain.bitcoin.block.dao.impl;


import com.sharingif.blockchain.bitcoin.app.dao.impl.BaseDAOImpl;
import com.sharingif.blockchain.bitcoin.block.dao.TransactionBusinessDAO;
import com.sharingif.blockchain.bitcoin.block.model.entity.TransactionBusiness;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Map;


@Repository
public class TransactionBusinessDAOImpl extends BaseDAOImpl<TransactionBusiness, String> implements TransactionBusinessDAO {

    @Override
    public int updateSettleStatusByAddressCoinTypeBlockNumberSettleStatus(String status, String address, String coinType, BigInteger blockNumber, String settleStatus) {
        TransactionBusiness transactionBusiness = new TransactionBusiness();
        transactionBusiness.setStatus(status);

        transactionBusiness.setTxFrom(address);
        transactionBusiness.setTxTo(address);
        transactionBusiness.setCoinType(coinType);
        transactionBusiness.setBlockNumber(blockNumber);
        transactionBusiness.setSettleStatus(settleStatus);

        return update("updateSettleStatusByAddressCoinTypeBlockNumberSettleStatus", transactionBusiness);
    }

    @Override
    public int queryCountByAddressCoinTypeSettleStatus(String address, String coinType, String settleStatus) {
        TransactionBusiness transactionBusiness = new TransactionBusiness();

        transactionBusiness.setTxFrom(address);
        transactionBusiness.setTxTo(address);
        transactionBusiness.setCoinType(coinType);
        transactionBusiness.setSettleStatus(settleStatus);

        return query("queryCountByAddressCoinTypeSettleStatus", transactionBusiness, Integer.TYPE);
    }

    @Override
    public int updateByBlockNumberBlockHash(TransactionBusiness transactionBusiness) {
        return update("updateByBlockNumberBlockHash", transactionBusiness);
    }

    @Override
    public int queryCountByTypeStatus(String type, String status) {
        TransactionBusiness transactionBusiness = new TransactionBusiness();
        transactionBusiness.setType(type);
        transactionBusiness.setStatus(status);

        return query("queryCountByTypeStatus", transactionBusiness, Integer.TYPE);
    }

}
