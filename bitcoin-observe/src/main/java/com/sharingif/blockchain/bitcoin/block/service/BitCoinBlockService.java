package com.sharingif.blockchain.bitcoin.block.service;

import org.bitcoincore.api.blockchain.entity.GetBlockRsp;

import java.math.BigInteger;

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
    GetBlockRsp getBlock(String blockHash);

    /**
     * 根据区块号查询区块信息
     * @param blockNumber
     * @return
     */
    GetBlockRsp getBlock(BigInteger blockNumber);

}
