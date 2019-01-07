package com.sharingif.blockchain.ether.withdrawal.dao.impl;


import com.sharingif.blockchain.ether.app.dao.impl.BaseDAOImpl;
import com.sharingif.blockchain.ether.withdrawal.dao.WithdrawalDAO;
import com.sharingif.blockchain.ether.withdrawal.model.entity.Withdrawal;
import org.springframework.stereotype.Repository;


@Repository
public class WithdrawalDAOImpl extends BaseDAOImpl<Withdrawal, String> implements WithdrawalDAO {

}
