package com.sharingif.blockchain.bitcoin.block.service;

import org.omnilayer.api.rawtransactions.entity.Transaction;

import java.math.BigInteger;

public interface OmniBlockService {

    /**
     * 根据交易id查询交易
     * @param txId
     * @return
     */
    Transaction getTransaction(String txId);

    /**
     * 导入观察地址
     * @param address
     * @param label
     */
    void importAddress(String address, String label);

    /**
     * 查询usdt余额
     * @param address
     * @return
     */
    BigInteger getUsdtBalance(String address);

}
