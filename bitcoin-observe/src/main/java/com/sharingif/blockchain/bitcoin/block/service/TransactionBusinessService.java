package com.sharingif.blockchain.bitcoin.block.service;


import com.sharingif.blockchain.bitcoin.block.model.entity.TransactionBusiness;
import com.sharingif.cube.support.service.base.IBaseService;

import java.math.BigInteger;


public interface TransactionBusinessService extends IBaseService<TransactionBusiness, java.lang.String> {

    /**
     * 根据条件查询
     * @param blockNumber
     * @param blockHash
     * @param txHash
     * @param vioIndex
     * @param type
     * @return
     */
    TransactionBusiness getTransactionBusiness(BigInteger blockNumber, String blockHash, String txHash, BigInteger vioIndex, String type);

}
