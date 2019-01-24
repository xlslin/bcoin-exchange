package com.sharingif.blockchain.bitcoin.withdrawal.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.WithdrawalVin;
import com.sharingif.blockchain.bitcoin.withdrawal.dao.WithdrawalVinDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.bitcoin.withdrawal.service.WithdrawalVinService;

@Service
public class WithdrawalVinServiceImpl extends BaseServiceImpl<WithdrawalVin, java.lang.String> implements WithdrawalVinService {
	
	private WithdrawalVinDAO withdrawalVinDAO;

	public WithdrawalVinDAO getWithdrawalVinDAO() {
		return withdrawalVinDAO;
	}
	@Resource
	public void setWithdrawalVinDAO(WithdrawalVinDAO withdrawalVinDAO) {
		super.setBaseDAO(withdrawalVinDAO);
		this.withdrawalVinDAO = withdrawalVinDAO;
	}
	
	
}
