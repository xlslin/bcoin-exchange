package com.sharingif.blockchain.bitcoin.block.service;


import com.sharingif.blockchain.bitcoin.block.model.entity.Transaction;
import com.sharingif.cube.support.service.base.IBaseService;

import java.math.BigInteger;
import java.util.Date;


public interface TransactionService extends IBaseService<Transaction, java.lang.String> {

    /**
     * 区块交易同步
     * @param transaction
     * @param blockNumber
     * @param blockHash
     * @param blockCreateTime
     */
    void analysis(org.bitcoincore.api.rawtransactions.entity.Transaction transaction, BigInteger blockNumber, String blockHash, Date blockCreateTime);
	
}
