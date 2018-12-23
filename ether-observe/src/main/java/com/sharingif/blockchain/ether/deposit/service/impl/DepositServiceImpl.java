package com.sharingif.blockchain.ether.deposit.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sharingif.blockchain.ether.deposit.model.entity.Deposit;
import com.sharingif.blockchain.ether.deposit.dao.DepositDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.ether.deposit.service.DepositService;

@Service
public class DepositServiceImpl extends BaseServiceImpl<Deposit, java.lang.String> implements DepositService {
	
	private DepositDAO depositDAO;

	public DepositDAO getDepositDAO() {
		return depositDAO;
	}
	@Resource
	public void setDepositDAO(DepositDAO depositDAO) {
		super.setBaseDAO(depositDAO);
		this.depositDAO = depositDAO;
	}


	@Override
	public void addUntreated(Deposit deposit) {
		deposit.setStatus(Deposit.STATUS_UNTREATED);
		add(deposit);
	}
}
