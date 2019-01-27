package com.sharingif.blockchain.bitcoin.withdrawal.dao.impl;


import com.sharingif.blockchain.bitcoin.app.dao.impl.BaseDAOImpl;
import com.sharingif.blockchain.bitcoin.withdrawal.dao.WithdrawalDAO;
import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.Withdrawal;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class WithdrawalDAOImpl extends BaseDAOImpl<Withdrawal, String> implements WithdrawalDAO {

    @Override
    public List<Withdrawal> queryListForUpdate(Withdrawal withdrawal) {
        return selectList("queryListForUpdate", withdrawal);
    }
}
