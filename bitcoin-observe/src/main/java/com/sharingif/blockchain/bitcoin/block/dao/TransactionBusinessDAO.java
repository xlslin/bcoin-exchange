package com.sharingif.blockchain.bitcoin.block.dao;


import com.sharingif.blockchain.bitcoin.app.dao.BaseDAO;
import com.sharingif.blockchain.bitcoin.block.model.entity.TransactionBusiness;

import java.math.BigInteger;


public interface TransactionBusinessDAO extends BaseDAO<TransactionBusiness, String> {

    /**
     * 根据地址、币种、小于等于区块号、结算状态等于修改结算状态
     * @param status
     * @param address
     * @param coinType
     * @param blockNumber
     * @param settleStatus
     * @return
     */
    int updateSettleStatusByAddressCoinTypeBlockNumberSettleStatus(String status, String address, String coinType, BigInteger blockNumber, String settleStatus);

    /**
     * 根据地址、币种、不等于结算状态查询数据条数
     * @param address
     * @param coinType
     * @param settleStatus
     * @return
     */
    int queryCountByAddressCoinTypeSettleStatus(String address, String coinType, String settleStatus);

    /**
     * 根据区块号、区块hash修改
     * @param transactionBusiness
     * @return
     */
    int updateByBlockNumberBlockHash(TransactionBusiness transactionBusiness);

    /**
     * 根据类型、状态查询条数
     * @param type
     * @param status
     * @return
     */
    int queryCountByTypeStatus(String type, String status);

}
