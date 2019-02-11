package com.sharingif.blockchain.bitcoin.withdrawal.dao;


import com.sharingif.blockchain.bitcoin.app.dao.BaseDAO;
import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.WithdrawalVin;


public interface WithdrawalVinDAO extends BaseDAO<WithdrawalVin, String> {

    /**
     * 根据hash查询条数
     * @param txHash
     * @return
     */
    int queryCountByTxHash(String txHash);

}
