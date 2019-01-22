package com.sharingif.blockchain.bitcoin.withdrawal.service.impl;


import javax.annotation.Resource;

import com.sharingif.blockchain.bitcoin.account.model.entity.AccountJnl;
import com.sharingif.blockchain.bitcoin.account.service.AccountService;
import com.sharingif.blockchain.bitcoin.block.dao.TransactionBusinessDAO;
import com.sharingif.blockchain.bitcoin.block.model.entity.BlockChain;
import com.sharingif.blockchain.bitcoin.block.model.entity.TransactionBusiness;
import org.springframework.stereotype.Service;

import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.Withdrawal;
import com.sharingif.blockchain.bitcoin.withdrawal.dao.WithdrawalDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.bitcoin.withdrawal.service.WithdrawalService;

@Service
public class WithdrawalServiceImpl extends BaseServiceImpl<Withdrawal, java.lang.String> implements WithdrawalService {
	
	private WithdrawalDAO withdrawalDAO;
	private TransactionBusinessDAO transactionBusinessDAO;
	private AccountService accountService;

	public WithdrawalDAO getWithdrawalDAO() {
		return withdrawalDAO;
	}
	@Resource
	public void setWithdrawalDAO(WithdrawalDAO withdrawalDAO) {
		super.setBaseDAO(withdrawalDAO);
		this.withdrawalDAO = withdrawalDAO;
	}
	@Resource
	public void setTransactionBusinessDAO(TransactionBusinessDAO transactionBusinessDAO) {
		this.transactionBusinessDAO = transactionBusinessDAO;
	}
	@Resource
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	@Override
	public void addUntreated(TransactionBusiness transactionBusiness) {

		TransactionBusiness queryTransactionBusiness = new TransactionBusiness();
		queryTransactionBusiness.setBlockNumber(transactionBusiness.getBlockNumber());
		queryTransactionBusiness.setBlockHash(transactionBusiness.getBlockHash());
		queryTransactionBusiness.setTxHash(transactionBusiness.getTxHash());
		queryTransactionBusiness.setVioIndex(transactionBusiness.getVioIndex());

		queryTransactionBusiness = transactionBusinessDAO.query(queryTransactionBusiness);
		if(queryTransactionBusiness != null) {
			return;
		}

		transactionBusiness.setType(TransactionBusiness.TYPE_WITHDRAWAL);
		transactionBusiness.setStatus(TransactionBusiness.STATUS_UNTREATED);
		transactionBusiness.setSettleStatus(TransactionBusiness.SETTLE_STATUS_UNTREATED);
		transactionBusiness.setTxStatus(BlockChain.STATUS_UNVERIFIED);

		transactionBusinessDAO.insert(transactionBusiness);

		withdrawal(transactionBusiness);
	}

	public void withdrawal(TransactionBusiness transactionBusiness) {
		accountService.frozenBalance(
				transactionBusiness.getTxFrom()
				,transactionBusiness.getCoinType()
				,transactionBusiness.getAmount()
				,transactionBusiness.getTxFrom()
				,transactionBusiness.getTxTo()
				,AccountJnl.TYPE_WITHDRAWAL
				,transactionBusiness.getId()
				,transactionBusiness.getTxTime()
		);

	}
	
	
}
