package com.sharingif.blockchain.ether.block.service;


import com.sharingif.blockchain.ether.block.model.entity.Transaction;
import com.sharingif.blockchain.ether.deposit.service.DepositService;
import com.sharingif.blockchain.ether.withdrawal.service.WithdrawalService;
import com.sharingif.cube.support.service.base.IBaseService;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.Date;


public interface TransactionService extends IBaseService<Transaction, java.lang.String> {

    /**
     * 返回交易业务服务
     * @return
     */
    TransactionBusinessService getTransactionBusinessService();

    /**
     * 区块交易同步
     * @param transaction
     * @param transactionReceipt
     * @param blockCreateTime
     */
    void analysis(org.web3j.protocol.core.methods.response.Transaction transaction, TransactionReceipt transactionReceipt, Date blockCreateTime);

    /**
     * 修改指定区块的交易为有效
     * @param blockNumber
     * @param blockHash
     * @param confirmBlockNumber
     */
    int updateStatusToBlockConfirmedValid(BigInteger blockNumber, String blockHash, int confirmBlockNumber);

    /**
     * 修改指定区块的交易为无效
     * @param blockNumber
     * @param blockHash
     * @param confirmBlockNumber
     */
    int updateStatusToBlockConfirmedInvalid(BigInteger blockNumber, String blockHash, int confirmBlockNumber);

}
