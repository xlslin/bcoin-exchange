package com.sharingif.blockchain.ether.withdrawal.service.impl;


import com.sharingif.blockchain.ether.withdrawal.dao.WithdrawalDAO;
import com.sharingif.blockchain.ether.withdrawal.model.entity.Withdrawal;
import com.sharingif.blockchain.ether.withdrawal.service.WithdrawalService;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WithdrawalServiceImpl extends BaseServiceImpl<Withdrawal, java.lang.String> implements WithdrawalService {
	
	private WithdrawalDAO withdrawalDAO;

	public WithdrawalDAO getWithdrawalDAO() {
		return withdrawalDAO;
	}
	@Resource
	public void setWithdrawalDAO(WithdrawalDAO withdrawalDAO) {
		super.setBaseDAO(withdrawalDAO);
		this.withdrawalDAO = withdrawalDAO;
	}

	@Override
	public void addUntreated(Withdrawal withdrawal) {
		withdrawal.setStatus(Withdrawal.STATUS_UNTREATED);
		withdrawalDAO.insert(withdrawal);
	}
}
