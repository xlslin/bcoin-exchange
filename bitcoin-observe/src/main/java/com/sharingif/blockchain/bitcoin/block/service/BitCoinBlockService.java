package com.sharingif.blockchain.bitcoin.block.service;

import java.math.BigInteger;

public interface BitCoinBlockService {

    /**
     * 获取区块号
     * @return
     */
    BigInteger getBlockCount();

}
