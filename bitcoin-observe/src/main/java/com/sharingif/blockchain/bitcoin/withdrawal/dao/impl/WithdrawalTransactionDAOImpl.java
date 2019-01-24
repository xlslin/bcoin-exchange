package com.sharingif.blockchain.bitcoin.withdrawal.dao.impl;


import com.sharingif.blockchain.bitcoin.app.dao.impl.BaseDAOImpl;
import com.sharingif.blockchain.bitcoin.withdrawal.dao.WithdrawalTransactionDAO;
import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.WithdrawalTransaction;
import org.springframework.stereotype.Repository;


@Repository
public class WithdrawalTransactionDAOImpl extends BaseDAOImpl<WithdrawalTransaction, String> implements WithdrawalTransactionDAO {
	
}
