package com.sharingif.blockchain.bitcoin.block.service;

import org.bitcoincore.api.blockchain.entity.Block;
import org.bitcoincore.api.rawtransactions.entity.SignRawTransaction;
import org.bitcoincore.api.rawtransactions.entity.Transaction;
import org.bitcoincore.api.wallet.entity.ListUnspentQueryOptions;
import org.bitcoincore.api.wallet.entity.Unspent;

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
     */
    Transaction getFullRawTransaction(String txid);

    /**
     * 导入观察地址
     * @param address
     * @param label
     */
    void importAddress(String address, String label);

    /**
     * 根据地址查询余额
     * @param address
     * @return
     */
    BigInteger getBalanceByAddress(String address);

    /**
     * 根据地址查询未花费的Spent
     * @param address
     * @return
     */
    List<Unspent> listUnspent(String address);

    /**
     * 签名交易
     * @param hexstring
     * @return
     */
    String signRawTransaction(String hexstring);

    /**
     * 发送签名交易
     * @param hexstring
     * @return
     */
    String sendRawTransaction(String hexstring);

}
