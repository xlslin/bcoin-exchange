package com.sharingif.blockchain.bitcoin.withdrawal.dao.impl;


import com.sharingif.blockchain.bitcoin.app.dao.impl.BaseDAOImpl;
import com.sharingif.blockchain.bitcoin.withdrawal.dao.WithdrawalVoutDAO;
import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.WithdrawalVout;
import org.springframework.stereotype.Repository;


@Repository
public class WithdrawalVoutDAOImpl extends BaseDAOImpl<WithdrawalVout, String> implements WithdrawalVoutDAO {
	
}
