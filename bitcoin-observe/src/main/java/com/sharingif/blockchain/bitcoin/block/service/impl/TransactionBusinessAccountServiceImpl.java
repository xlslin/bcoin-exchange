package com.sharingif.blockchain.bitcoin.block.service.impl;


import javax.annotation.Resource;

import com.sharingif.blockchain.bitcoin.account.service.AccountService;
import com.sharingif.blockchain.bitcoin.app.constants.CoinType;
import com.sharingif.blockchain.bitcoin.block.service.BitCoinBlockService;
import com.sharingif.blockchain.bitcoin.block.service.OmniBlockService;
import com.sharingif.blockchain.bitcoin.block.service.TransactionBusinessService;
import org.springframework.stereotype.Service;

import com.sharingif.blockchain.bitcoin.block.model.entity.TransactionBusinessAccount;
import com.sharingif.blockchain.bitcoin.block.dao.TransactionBusinessAccountDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.bitcoin.block.service.TransactionBusinessAccountService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Service
public class TransactionBusinessAccountServiceImpl extends BaseServiceImpl<TransactionBusinessAccount, java.lang.String> implements TransactionBusinessAccountService {
	
	private TransactionBusinessAccountDAO transactionBusinessAccountDAO;
	private BitCoinBlockService bitCoinBlockService;
	private OmniBlockService omniBlockService;
	private AccountService accountService;
	private TransactionBusinessService transactionBusinessService;

	public TransactionBusinessAccountDAO getTransactionBusinessAccountDAO() {
		return transactionBusinessAccountDAO;
	}
	@Resource
	public void setTransactionBusinessAccountDAO(TransactionBusinessAccountDAO transactionBusinessAccountDAO) {
		super.setBaseDAO(transactionBusinessAccountDAO);
		this.transactionBusinessAccountDAO = transactionBusinessAccountDAO;
	}
	@Resource
	public void setBitCoinBlockService(BitCoinBlockService bitCoinBlockService) {
		this.bitCoinBlockService = bitCoinBlockService;
	}
	@Resource
	public void setOmniBlockService(OmniBlockService omniBlockService) {
		this.omniBlockService = omniBlockService;
	}
	@Resource
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	@Resource
	public void setTransactionBusinessService(TransactionBusinessService transactionBusinessService) {
		this.transactionBusinessService = transactionBusinessService;
	}

	@Override
	public boolean addTransactionBusinessAccount(String address, String coinType) {
		TransactionBusinessAccount queryTransactionBusinessAccount = new TransactionBusinessAccount();
		queryTransactionBusinessAccount.setAddress(address);
		queryTransactionBusinessAccount.setCoinType(coinType);
		queryTransactionBusinessAccount = transactionBusinessAccountDAO.query(queryTransactionBusinessAccount);

		if(queryTransactionBusinessAccount != null) {
			return false;
		}

		TransactionBusinessAccount transactionBusinessAccount = new TransactionBusinessAccount();
		transactionBusinessAccount.setAddress(address);
		transactionBusinessAccount.setCoinType(coinType);

		transactionBusinessAccountDAO.insert(transactionBusinessAccount);
		return true;
	}

	@Override
	public void settleAccounts(List<TransactionBusinessAccount> transactionBusinessAccountList) {
		transactionBusinessAccountList.forEach(transactionBusinessAccount -> {

			BigInteger blockNumber = bitCoinBlockService.getBlockNumber();

			BigInteger accountBalance = accountService.getBalance(transactionBusinessAccount.getAddress(),transactionBusinessAccount.getCoinType());
			BigInteger blockBalance = null;

			if(CoinType.BTC.name().equals(transactionBusinessAccount.getCoinType())) {
				blockBalance = bitCoinBlockService.getBalanceByAddress(transactionBusinessAccount.getAddress());
			} else if(CoinType.USDT.name().equals(transactionBusinessAccount.getCoinType())) {
				blockBalance = omniBlockService.getUsdtBalance(transactionBusinessAccount.getAddress());
			}

			if(blockBalance.compareTo(accountBalance) != 0) {
				return;
			}


			transactionBusinessService.updateSettleStatusToSettled(transactionBusinessAccount.getAddress(), transactionBusinessAccount.getCoinType(), blockNumber);

			boolean deleteTransactionBusinessAccount = transactionBusinessService.getUnsettledCountByAddressCoinTypeSettleStatus(
					transactionBusinessAccount.getAddress()
					,transactionBusinessAccount.getCoinType()
			) == 0;

			if(deleteTransactionBusinessAccount) {
				transactionBusinessAccountDAO.deleteById(transactionBusinessAccount.getId());
			}

		});
	}

}
