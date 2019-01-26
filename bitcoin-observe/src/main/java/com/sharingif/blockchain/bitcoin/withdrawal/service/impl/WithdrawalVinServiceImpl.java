package com.sharingif.blockchain.bitcoin.withdrawal.service.impl;


import javax.annotation.Resource;

import com.sharingif.blockchain.bitcoin.account.model.entity.Account;
import com.sharingif.blockchain.bitcoin.account.model.entity.AccountUnspent;
import org.bitcoincore.api.wallet.entity.Unspent;
import org.springframework.stereotype.Service;

import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.WithdrawalVin;
import com.sharingif.blockchain.bitcoin.withdrawal.dao.WithdrawalVinDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.bitcoin.withdrawal.service.WithdrawalVinService;

import java.util.List;

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


	@Override
	public void addWithdrawalVin(String txHash, List<AccountUnspent> accountUnspentList) {
		for(AccountUnspent accountUnspent : accountUnspentList) {
			Account account = accountUnspent.getAccount();
			List<Unspent> unspentList = accountUnspent.getUnspentList();

			for(Unspent unspent : unspentList) {
				WithdrawalVin vithdrawalVin = new WithdrawalVin();
				vithdrawalVin.setTxHash(txHash);
				vithdrawalVin.setAddress(account.getAddress());
				vithdrawalVin.setTxId(unspent.getTxId());
				vithdrawalVin.setVout(unspent.getvOut());
				vithdrawalVin.setAmount(unspent.getAmount().toBigInteger());

				withdrawalVinDAO.insert(vithdrawalVin);
			}

		}
	}
}
