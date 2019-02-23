package com.sharingif.blockchain.bitcoin.block.service;

import org.omnilayer.api.rawtransactions.entity.Transaction;

import java.math.BigInteger;

public interface OmniBlockService {

    /**
     * 解码交易
     * @param rawTx
     * @return
     */
    Transaction decodeTransaction(String rawTx);

    /**
     * 查询usdt余额
     * @param address
     * @return
     */
    BigInteger getUsdtBalance(String address);

}
