package com.sharingif.blockchain.bitcoin.block.service;

import org.bitcoincore.api.blockchain.entity.Block;
import org.bitcoincore.api.rawtransactions.entity.Transaction;

import java.math.BigInteger;
import java.util.List;

public interface BitCoinBlockService {

    /**
     * 获取区块号
     * @return
     */
    BigInteger getBlockNumber();

    /**
     * 根据区块号查询区块hash
     * @param blockNumber
     * @return
     */
    String getBlockHash(BigInteger blockNumber);

    /**
     * 根据区块哈希查询区块信息
     * @param blockHash
     * @return
     */
    Block<List<String>> getBlock(String blockHash);

    /**
     * 根据区块号查询区块信息
     * @param blockNumber
     * @return
     */
    Block<List<String>> getBlock(BigInteger blockNumber);

    /**
     * 根据哈希查询区块完整信息
     * @param blockHash
     * @return
     */
    Block<List<Transaction>> getBlockFullTransaction(String blockHash);

    /**
     * 返回交易完整信息
     * @param txid
     * @param blockhash
     */
    Transaction getFullRawTransaction(String txid, String blockhash);

}
