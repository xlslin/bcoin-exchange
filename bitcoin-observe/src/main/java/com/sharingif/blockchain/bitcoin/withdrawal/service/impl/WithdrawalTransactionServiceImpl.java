package com.sharingif.blockchain.bitcoin.withdrawal.service.impl;


import javax.annotation.Resource;

import com.sharingif.blockchain.bitcoin.account.model.entity.AccountUnspent;
import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.Withdrawal;
import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.WithdrawalVin;
import com.sharingif.blockchain.bitcoin.withdrawal.service.WithdrawalVinService;
import com.sharingif.blockchain.bitcoin.withdrawal.service.WithdrawalVoutService;
import org.springframework.stereotype.Service;

import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.WithdrawalTransaction;
import com.sharingif.blockchain.bitcoin.withdrawal.dao.WithdrawalTransactionDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.bitcoin.withdrawal.service.WithdrawalTransactionService;

import java.math.BigInteger;
import java.util.List;

@Service
public class WithdrawalTransactionServiceImpl extends BaseServiceImpl<WithdrawalTransaction, java.lang.String> implements WithdrawalTransactionService {
	
	private WithdrawalTransactionDAO withdrawalTransactionDAO;
	private WithdrawalVinService withdrawalVinService;
	private WithdrawalVoutService withdrawalVoutService;

	public WithdrawalTransactionDAO getWithdrawalTransactionDAO() {
		return withdrawalTransactionDAO;
	}
	@Resource
	public void setWithdrawalTransactionDAO(WithdrawalTransactionDAO withdrawalTransactionDAO) {
		super.setBaseDAO(withdrawalTransactionDAO);
		this.withdrawalTransactionDAO = withdrawalTransactionDAO;
	}
	@Resource
	public void setWithdrawalVinService(WithdrawalVinService withdrawalVinService) {
		this.withdrawalVinService = withdrawalVinService;
	}
	@Resource
	public void setWithdrawalVoutService(WithdrawalVoutService withdrawalVoutService) {
		this.withdrawalVoutService = withdrawalVoutService;
	}

	@Override
	public void addWithdrawalTransaction(String txHash, BigInteger fee, List<AccountUnspent> accountUnspentList, List<Withdrawal> withdrawalList) {
		WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction();
		withdrawalTransaction.setTxHash(txHash);
		withdrawalTransaction.setFee(fee);
		withdrawalTransaction.setStatus(WithdrawalTransaction.STATUS_UNTREATED);

		withdrawalTransactionDAO.insert(withdrawalTransaction);

		withdrawalVinService.addWithdrawalVin(txHash, accountUnspentList);

		withdrawalVoutService.addWithdrawalVout(txHash, withdrawalList);
	}

	@Override
	public WithdrawalTransaction getUntreated(String txHash) {
		WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction();
		withdrawalTransaction.setTxHash(txHash);
		withdrawalTransaction.setStatus(WithdrawalTransaction.STATUS_UNTREATED);

		return withdrawalTransactionDAO.query(withdrawalTransaction);
	}
}
