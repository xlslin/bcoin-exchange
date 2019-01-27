package com.sharingif.blockchain.bitcoin.withdrawal.service.impl;


import javax.annotation.Resource;

import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.Withdrawal;
import org.springframework.stereotype.Service;

import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.WithdrawalVout;
import com.sharingif.blockchain.bitcoin.withdrawal.dao.WithdrawalVoutDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.bitcoin.withdrawal.service.WithdrawalVoutService;

import java.util.List;

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


	@Override
	public void addWithdrawalVout(String txHash, List<Withdrawal> withdrawalList) {
		for(int i=0; i<withdrawalList.size();i++) {
			Withdrawal withdrawal = withdrawalList.get(i);

			WithdrawalVout withdrawalVout = new WithdrawalVout();
			withdrawalVout.setWithdrawalId(withdrawal.getId());
			withdrawalVout.setTxHash(txHash);
			withdrawalVout.setAddress(withdrawal.getTxTo());
			withdrawalVout.setVout(i);
			withdrawalVout.setAmount(withdrawal.getAmount());

			withdrawalVoutDAO.insert(withdrawalVout);
		}
	}

	@Override
	public List<WithdrawalVout> getByTxHash(String txHash) {
		WithdrawalVout withdrawalVout = new WithdrawalVout();
		withdrawalVout.setTxHash(txHash);

		return withdrawalVoutDAO.queryList(withdrawalVout);
	}
}
