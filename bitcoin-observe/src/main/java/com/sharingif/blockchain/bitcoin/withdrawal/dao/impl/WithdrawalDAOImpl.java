package com.sharingif.blockchain.bitcoin.withdrawal.dao.impl;


import com.sharingif.blockchain.bitcoin.app.dao.impl.BaseDAOImpl;
import com.sharingif.blockchain.bitcoin.withdrawal.dao.WithdrawalDAO;
import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.Withdrawal;
import org.springframework.stereotype.Repository;


@Repository
public class WithdrawalDAOImpl extends BaseDAOImpl<Withdrawal, String> implements WithdrawalDAO {
	
}
