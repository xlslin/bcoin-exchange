package com.sharingif.blockchain.bitcoin.withdrawal.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.WithdrawalVout;
import com.sharingif.blockchain.bitcoin.withdrawal.dao.WithdrawalVoutDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.bitcoin.withdrawal.service.WithdrawalVoutService;

@Service
public class WithdrawalVoutServiceImpl extends BaseServiceImpl<WithdrawalVout, java.lang.String> implements WithdrawalVoutService {
	
	private WithdrawalVoutDAO withdrawalVoutDAO;

	public WithdrawalVoutDAO getWithdrawalVoutDAO() {
		return withdrawalVoutDAO;
	}
	@Resource
	public void setWithdrawalVoutDAO(WithdrawalVoutDAO withdrawalVoutDAO) {
		super.setBaseDAO(withdrawalVoutDAO);
		this.withdrawalVoutDAO = withdrawalVoutDAO;
	}
	
	
}
