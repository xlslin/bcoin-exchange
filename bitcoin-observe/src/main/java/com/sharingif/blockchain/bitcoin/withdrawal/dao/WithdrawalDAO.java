package com.sharingif.blockchain.bitcoin.withdrawal.dao;


import com.sharingif.blockchain.bitcoin.app.dao.BaseDAO;
import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.Withdrawal;

import java.util.List;


public interface WithdrawalDAO extends BaseDAO<Withdrawal, String> {

    /***
     * 根据条件加锁查询
     * @param withdrawal
     * @return
     */
    List<Withdrawal> queryListForUpdate(Withdrawal withdrawal);

    /**
     * 根据交易hash修改
     * @param withdrawal
     * @return
     */
    int updateByTxHash(Withdrawal withdrawal);

}
