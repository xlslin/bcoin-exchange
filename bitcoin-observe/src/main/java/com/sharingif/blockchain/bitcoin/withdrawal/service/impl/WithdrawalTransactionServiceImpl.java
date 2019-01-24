package com.sharingif.blockchain.bitcoin.withdrawal.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.WithdrawalTransaction;
import com.sharingif.blockchain.bitcoin.withdrawal.dao.WithdrawalTransactionDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.bitcoin.withdrawal.service.WithdrawalTransactionService;

@Service
public class WithdrawalTransactionServiceImpl extends BaseServiceImpl<WithdrawalTransaction, java.lang.String> implements WithdrawalTransactionService {
	
	private WithdrawalTransactionDAO withdrawalTransactionDAO;

	public WithdrawalTransactionDAO getWithdrawalTransactionDAO() {
		return withdrawalTransactionDAO;
	}
	@Resource
	public void setWithdrawalTransactionDAO(WithdrawalTransactionDAO withdrawalTransactionDAO) {
		super.setBaseDAO(withdrawalTransactionDAO);
		this.withdrawalTransactionDAO = withdrawalTransactionDAO;
	}
	
	
}
