package com.sharingif.blockchain.ether.block.dao;


import com.sharingif.blockchain.ether.app.dao.BaseDAO;
import com.sharingif.blockchain.ether.block.model.entity.TransactionBusiness;

import java.math.BigInteger;


public interface TransactionBusinessDAO extends BaseDAO<TransactionBusiness, String> {

    /**
     * 根据地址、币种、小于等于区块号、交易状态不等于修改状态
     * @param status
     * @param address
     * @param coinType
     * @param blockNumber
     * @param txStatus
     * @return
     */
    int updateStatusByAddressCoinTypeBlockNumberTxStatus(String status, String address, String coinType, BigInteger blockNumber, String txStatus);

    /**
     * 根据地址、币种、大于区块号查询数据条数
     * @param address
     * @param coinType
     * @param blockNumber
     * @return
     */
    int queryCountByAddressCoinTypeBlockNumber(String address, String coinType, BigInteger blockNumber);

}
