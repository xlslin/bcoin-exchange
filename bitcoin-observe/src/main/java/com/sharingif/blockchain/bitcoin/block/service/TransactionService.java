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
     * @param txIndex
     */
    void analysis(org.bitcoincore.api.rawtransactions.entity.Transaction transaction, BigInteger blockNumber, String blockHash, Date blockCreateTime, BigInteger txIndex);

    /**
     * 修改指定区块的交易为有效
     * @param blockNumber
     * @param blockHash
     * @param confirmBlockNumber
     */
    void updateTxStatusToBlockConfirmedValid(BigInteger blockNumber, String blockHash, int confirmBlockNumber);

    /**
     * 修改指定区块的交易为无效
     * @param blockNumber
     * @param blockHash
     * @param confirmBlockNumber
     */
    void updateTxStatusToBlockConfirmedInvalid(BigInteger blockNumber, String blockHash, int confirmBlockNumber);
	
}
