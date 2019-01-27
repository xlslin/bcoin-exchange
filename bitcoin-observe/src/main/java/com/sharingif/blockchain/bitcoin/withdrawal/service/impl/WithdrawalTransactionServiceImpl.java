package com.sharingif.blockchain.bitcoin.withdrawal.service.impl;


import javax.annotation.Resource;

import com.sharingif.blockchain.bitcoin.account.model.entity.AccountUnspent;
import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.Withdrawal;
import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.WithdrawalVin;
import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.WithdrawalVout;
import com.sharingif.blockchain.bitcoin.withdrawal.service.WithdrawalVinService;
import com.sharingif.blockchain.bitcoin.withdrawal.service.WithdrawalVoutService;
import com.sharingif.cube.batch.core.JobModel;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;
import org.springframework.stereotype.Service;

import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.WithdrawalTransaction;
import com.sharingif.blockchain.bitcoin.withdrawal.dao.WithdrawalTransactionDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.bitcoin.withdrawal.service.WithdrawalTransactionService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Date;
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
	@Override
	public WithdrawalVinService getWithdrawalVinService() {
		return withdrawalVinService;
	}
	@Resource
	public void setWithdrawalVinService(WithdrawalVinService withdrawalVinService) {
		this.withdrawalVinService = withdrawalVinService;
	}

	public WithdrawalVoutService getWithdrawalVoutService() {
		return withdrawalVoutService;
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
		withdrawalTransaction.setStatus(Withdrawal.STATUS_PROCESSING);

		withdrawalTransactionDAO.insert(withdrawalTransaction);

		withdrawalVinService.addWithdrawalVin(txHash, accountUnspentList);

		withdrawalVoutService.addWithdrawalVout(txHash, withdrawalList);
	}

	@Override
	public int updateStatusToInitNotice(String txHash) {
		WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction();
		withdrawalTransaction.setTxHash(txHash);
		withdrawalTransaction.setStatus(Withdrawal.STATUS_INIT_NOTICE);

		return withdrawalTransactionDAO.updateById(withdrawalTransaction);
	}

	@Override
	public int updateStatusToInitNoticed(String txHash) {
		WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction();
		withdrawalTransaction.setTxHash(txHash);
		withdrawalTransaction.setStatus(Withdrawal.STATUS_INIT_NOTICED);

		return withdrawalTransactionDAO.updateById(withdrawalTransaction);
	}

	@Override
	public int updateStatusToSuccess(String txHash) {
		WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction();
		withdrawalTransaction.setTxHash(txHash);
		withdrawalTransaction.setStatus(Withdrawal.STATUS_SUCCESS);

		return withdrawalTransactionDAO.updateById(withdrawalTransaction);
	}

}
