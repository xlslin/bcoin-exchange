package com.sharingif.blockchain.bitcoin.withdrawal.dao.impl;


import com.sharingif.blockchain.bitcoin.app.dao.impl.BaseDAOImpl;
import com.sharingif.blockchain.bitcoin.withdrawal.dao.WithdrawalVinDAO;
import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.WithdrawalVin;
import org.springframework.stereotype.Repository;


@Repository
public class WithdrawalVinDAOImpl extends BaseDAOImpl<WithdrawalVin, String> implements WithdrawalVinDAO {

    @Override
    public int queryCountByTxHash(String txHash) {
        return query("queryCountByTxHash", txHash, Integer.TYPE);
    }
}
