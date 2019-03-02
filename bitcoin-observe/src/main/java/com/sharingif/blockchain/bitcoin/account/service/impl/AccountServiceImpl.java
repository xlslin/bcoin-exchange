package com.sharingif.blockchain.bitcoin.account.service.impl;


import com.sharingif.blockchain.bitcoin.account.dao.AccountDAO;
import com.sharingif.blockchain.bitcoin.account.model.entity.*;
import com.sharingif.blockchain.bitcoin.account.service.AccountFrozenJnlService;
import com.sharingif.blockchain.bitcoin.account.service.AccountJnlService;
import com.sharingif.blockchain.bitcoin.account.service.AccountService;
import com.sharingif.blockchain.bitcoin.app.constants.CoinType;
import com.sharingif.blockchain.bitcoin.app.constants.Constants;
import com.sharingif.blockchain.bitcoin.app.constants.ErrorConstants;
import com.sharingif.blockchain.bitcoin.app.exception.AccountException;
import com.sharingif.blockchain.bitcoin.block.service.BitCoinBlockService;
import com.sharingif.blockchain.bitcoin.block.service.OmniBlockService;
import com.sharingif.cube.core.exception.validation.ValidationCubeException;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import org.bitcoincore.api.wallet.entity.Unspent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AccountServiceImpl extends BaseServiceImpl<Account, java.lang.String> implements AccountService {
	
	private AccountDAO accountDAO;
	private AccountJnlService accountJnlService;
	private AccountFrozenJnlService accountFrozenJnlService;
	private BitCoinBlockService bitCoinBlockService;
	private OmniBlockService omniBlockService;

	public AccountDAO getAccountDAO() {
		return accountDAO;
	}
	@Resource
	public void setAccountDAO(AccountDAO accountDAO) {
		super.setBaseDAO(accountDAO);
		this.accountDAO = accountDAO;
	}
	@Resource
	public void setAccountJnlService(AccountJnlService accountJnlService) {
		this.accountJnlService = accountJnlService;
	}
	@Resource
	public void setAccountFrozenJnlService(AccountFrozenJnlService accountFrozenJnlService) {
		this.accountFrozenJnlService = accountFrozenJnlService;
	}
	@Resource
	public void setBitCoinBlockService(BitCoinBlockService bitCoinBlockService) {
		this.bitCoinBlockService = bitCoinBlockService;
	}
	@Resource
	public void setOmniBlockService(OmniBlockService omniBlockService) {
		this.omniBlockService = omniBlockService;
	}

	protected void initNormalAccount(String address, String coinType) {
		Account account = new Account();
		account.setAddress(address);
		account.setCoinType(coinType);

		Account queryAccount = accountDAO.query(account);
		if(queryAccount == null) {
			account.setTotalIn(BigInteger.ZERO);
			account.setTotalOut(BigInteger.ZERO);
			account.setBalance(BigInteger.ZERO);
			account.setFrozenAmount(BigInteger.ZERO);
			account.setStatus(Account.STATUS_NORMAL);

			accountDAO.insert(account);
		}
	}

	@Override
	public int lockAccount(String id) {
		Account account = new Account();
		account.setId(id);
		account.setStatus(Account.STATUS_LOCK);

		return accountDAO.updateById(account);
	}

	@Override
	public int unLockAccount(String address, String coinType) {
		Account account = getAccount(address, coinType);

		Account updateAccount = new Account();
		updateAccount.setId(account.getId());
		updateAccount.setAddress(address);
		updateAccount.setCoinType(coinType);
		updateAccount.setStatus(Account.STATUS_NORMAL);

		return accountDAO.updateById(updateAccount);
	}

	@Override
	public int exceptionAccount(String id) {
		Account account = new Account();
		account.setId(id);
		account.setStatus(Account.STATUS_EXCEPTION);

		return accountDAO.updateById(account);
	}

	@Override
	public Account getAccount(String address, String coinType) {
		Account account = new Account();
		account.setAddress(address);
		account.setCoinType(coinType);

		Account queryAccount = accountDAO.query(account);

		return queryAccount;
	}

	@Override
	public BigInteger getBalance(String address, String coinType) {
		Account queryAccount = getAccount(address, coinType);

		return queryAccount.getBalance();
	}

	@Override
	public void addBalance(String address, String coinType, BigInteger balance, String accountFrom, String accountTo, String type, String txId, Date transTime) {
		initNormalAccount(address, coinType);

		accountDAO.updateAddTotalInBalanceByAddressCoinType(address, coinType, balance);

		AccountJnl accountJnl = new AccountJnl();
		accountJnl.setAccountFrom(accountFrom);
		accountJnl.setAccountTo(accountTo);
		accountJnl.setCoinType(coinType);
		accountJnl.setBalance(balance);
		accountJnl.setType(type);
		accountJnl.setTxId(txId);
		accountJnl.setTransTime(transTime);

		accountJnlService.add(accountJnl);

	}

	@Override
	public void subtractBalance(String address, String coinType, BigInteger balance, String accountFrom, String accountTo, String type, String txId, Date transTime) {
		initNormalAccount(address, coinType);

		int num = accountDAO.updateSubTotalOutBalanceByAddressCoinType(address, coinType, balance);
		if(num == 0) {
			throw new ValidationCubeException(ErrorConstants.INSUFFICIENT_BALANCE);
		}

		AccountJnl accountJnl = new AccountJnl();
		accountJnl.setAccountFrom(accountFrom);
		accountJnl.setAccountTo(accountTo);
		accountJnl.setCoinType(coinType);
		accountJnl.setBalance(balance);
		accountJnl.setType(type);
		accountJnl.setTxId(txId);
		accountJnl.setTransTime(transTime);

		accountJnlService.add(accountJnl);

	}

	@Override
	public void frozenBalance(String address, String coinType, BigInteger balance, String accountFrom, String accountTo, String type, String txId, Date transTime) {
		initNormalAccount(address, coinType);

		int num = accountDAO.updateSubBalanceFrozenAmountByAddressCoinType(address, coinType, balance);
		if(num == 0) {
			throw new ValidationCubeException(ErrorConstants.INSUFFICIENT_BALANCE);
		}

		AccountJnl accountJnl = new AccountJnl();
		accountJnl.setAccountFrom(accountFrom);
		accountJnl.setAccountTo(accountTo);
		accountJnl.setCoinType(coinType);
		accountJnl.setBalance(balance);
		accountJnl.setType(type);
		accountJnl.setTxId(txId);
		accountJnl.setTransTime(transTime);

		accountJnlService.add(accountJnl);

		AccountFrozenJnl accountFrozenJnl = new AccountFrozenJnl();
		accountFrozenJnl.setAccountFrom(accountFrom);
		accountFrozenJnl.setAccountTo(accountTo);
		accountFrozenJnl.setCoinType(coinType);
		accountFrozenJnl.setBalance(balance);
		accountFrozenJnl.setTxId(txId);
		accountFrozenJnl.setTransTime(transTime);
		accountFrozenJnlService.addAccountFrozenJnl(accountFrozenJnl);
	}

	@Override
	public void unFrozenBalance(String address, String coinType, BigInteger balance, String accountFrom, String accountTo, String type, String txId, Date transTime) {
		initNormalAccount(address, coinType);

		int num = accountDAO.updateAddBalanceFrozenAmountByAddressCoinType(address, coinType, balance);
		if(num == 0) {
			throw new ValidationCubeException(ErrorConstants.INSUFFICIENT_FROZEN_BALANCE);
		}

		AccountJnl accountJnl = new AccountJnl();
		accountJnl.setAccountFrom(accountFrom);
		accountJnl.setAccountTo(accountTo);
		accountJnl.setCoinType(coinType);
		accountJnl.setBalance(balance);
		accountJnl.setType(type);
		accountJnl.setTxId(txId);
		accountJnl.setTransTime(transTime);

		accountJnlService.add(accountJnl);

		AccountFrozenJnl accountFrozenJnl = new AccountFrozenJnl();
		accountFrozenJnl.setAccountFrom(accountFrom);
		accountFrozenJnl.setAccountTo(accountTo);
		accountFrozenJnl.setCoinType(coinType);
		accountFrozenJnl.setBalance(balance);
		accountFrozenJnl.setTxId(txId);
		accountFrozenJnl.setTransTime(transTime);
		accountFrozenJnlService.addAccountUnFrozenJnl(accountFrozenJnl);
	}

	@Override
	public void subtractFrozenBalance(String address, String coinType, BigInteger balance, String accountFrom, String accountTo, String txId, Date transTime) {
		initNormalAccount(address, coinType);

		int num = accountDAO.updateSubFrozenAmountTotalOutByAddressCoinType(address, coinType, balance);
		if(num == 0) {
			throw new ValidationCubeException(ErrorConstants.INSUFFICIENT_FROZEN_BALANCE);
		}

		AccountFrozenJnl accountFrozenJnl = new AccountFrozenJnl();
		accountFrozenJnl.setAccountFrom(accountFrom);
		accountFrozenJnl.setAccountTo(accountTo);
		accountFrozenJnl.setCoinType(coinType);
		accountFrozenJnl.setBalance(balance);
		accountFrozenJnl.setTxId(txId);
		accountFrozenJnl.setTransTime(transTime);
		accountFrozenJnlService.addAccountUnFrozenJnl(accountFrozenJnl);

	}

	protected BigInteger getAccountListByBalance(BigInteger balance, BigInteger accounTotalBalance, List<AccountUnspent> accountUnspentList, List<Account> queryAccountList) {

		for(Account account : queryAccountList) {

			List<Unspent> unspentList = bitCoinBlockService.listUnspent(account.getAddress());
			if(unspentList == null || unspentList.isEmpty()) {
				continue;
			}

			// 验证区块余额与账户余额是否一致，如果不一致报错
			BigInteger blockBalance = BigInteger.ZERO;
			for(Unspent unspent : unspentList) {
				unspent.setAmount(unspent.getAmount().multiply(Constants.BTC_UNIT));
				blockBalance = blockBalance.add(unspent.getAmount().toBigInteger());
			}
			if(account.getBalance().compareTo(blockBalance) !=0) {
				logger.error("account exception, coin type:{}, account balance:{}, block balance:{}", account.getCoinType(), account.getBalance(), blockBalance);
				exceptionAccount(account.getId());
				throw new AccountException();
			}

			List<Unspent> accountUnspentUnspentList = new ArrayList<Unspent>();
			AccountUnspent accountUnspent = new AccountUnspent();
			accountUnspent.setAccount(account);
			accountUnspent.setUnspentList(accountUnspentUnspentList);

			accountUnspentList.add(accountUnspent);

			for(Unspent unspent : unspentList) {
				accounTotalBalance = accounTotalBalance.add(unspent.getAmount().toBigInteger());
				accountUnspentUnspentList.add(unspent);

				if(accounTotalBalance.compareTo(balance) > -1) {
					return accounTotalBalance;
				}
			}

		}

		return accounTotalBalance;
	}

	protected List<AccountUnspent> getAccountListByBalance(PaginationCondition<Account> paginationCondition, BigInteger balance, List<AccountUnspent> accountUnspentList, BigInteger accounTotalBalance) {
		PaginationRepertory<Account> accountPaginationRepertory = accountDAO.queryPaginationListByCoinTypeBalanceStatus(paginationCondition);
		List<Account> queryAccountList = accountPaginationRepertory.getPageItems();

		if(queryAccountList == null || queryAccountList.isEmpty()) {
			return null;
		}
		accounTotalBalance = getAccountListByBalance(balance, accounTotalBalance, accountUnspentList, queryAccountList);

		if(accounTotalBalance.compareTo(balance) > -1) {
			return accountUnspentList;
		} else {
			paginationCondition.setCurrentPage(paginationCondition.getCurrentPage()+1);
			return getAccountListByBalance(paginationCondition, balance, accountUnspentList, accounTotalBalance);
		}
	};

	@Override
	public List<AccountUnspent> getAccountListByBalance(BigInteger balance) {
		Account queryAccount = new Account();
		queryAccount.setCoinType(CoinType.BTC.name());
		queryAccount.setStatus(Account.STATUS_NORMAL);
		queryAccount.setBalance(BigInteger.ZERO);
		PaginationCondition<Account> paginationCondition = new PaginationCondition<Account>();
		paginationCondition.setCondition(queryAccount);
		paginationCondition.setQueryCount(false);
		paginationCondition.setCurrentPage(1);
		paginationCondition.setPageSize(20);

		return getAccountListByBalance(paginationCondition, balance, new ArrayList<AccountUnspent>(), BigInteger.ZERO);
	}

	@Override
	public AccountUnspent getUsdtAccountByBalance(BigInteger btcBalance, BigInteger usdtBalance) {
		SubAccount querySubAccount = new SubAccount();
		querySubAccount.setCoinType(CoinType.BTC.name());
		querySubAccount.setBalance(btcBalance);
		querySubAccount.setSubCoinType(CoinType.USDT.name());
		querySubAccount.setSubBalance(usdtBalance);
		querySubAccount.setStatus(Account.STATUS_NORMAL);
		PaginationCondition<SubAccount> paginationCondition = new PaginationCondition<SubAccount>();
		paginationCondition.setCondition(querySubAccount);
		paginationCondition.setQueryCount(false);
		paginationCondition.setCurrentPage(1);
		paginationCondition.setPageSize(1);
		PaginationRepertory<Account> accountPaginationRepertory = accountDAO.queryPaginationListByCoinTypeSubCoinTypeBalanceSubBalance(paginationCondition);

		List<Account> accountList = accountPaginationRepertory.getPageItems();
		if (accountList == null || accountList.isEmpty()) {
			return null;
		}

		Account account = accountList.get(0);
		account = getAccount(account.getAddress(), CoinType.USDT.name());

		// 验证区块余额与账户余额是否一致，如果不一致报错
		BigInteger blockUsdtBalance = omniBlockService.getUsdtBalance(account.getAddress());
		if(account.getBalance().compareTo(blockUsdtBalance) != 0) {
			logger.error("account exception, coin type:{}, account balance:{}, block balance:{}", account.getCoinType(), account.getBalance(), blockUsdtBalance);
			exceptionAccount(account.getId());
			throw new AccountException();
		}

		BigInteger btcAccountBalance = getBalance(account.getAddress(), CoinType.BTC.name());
		List<Unspent> unspentList = bitCoinBlockService.listUnspent(account.getAddress());
		BigInteger blockBtcBalance = BigInteger.ZERO;
		for(Unspent unspent : unspentList) {
			unspent.setAmount(unspent.getAmount().multiply(Constants.BTC_UNIT));
			blockBtcBalance = blockBtcBalance.add(unspent.getAmount().toBigInteger());
		}
		if(btcAccountBalance.compareTo(blockBtcBalance) !=0) {
			logger.error("account exception, coin type:{}, account balance:{}, block balance:{}",  CoinType.BTC.name(), account.getBalance(), blockBtcBalance);
			exceptionAccount(account.getId());
			throw new AccountException();
		}

		List<Unspent> accountUnspentUnspentList = new ArrayList<Unspent>();
		AccountUnspent accountUnspent = new AccountUnspent();
		accountUnspent.setAccount(account);
		accountUnspent.setUnspentList(accountUnspentUnspentList);

		BigInteger accounTotalBalance = BigInteger.ZERO;
		for(Unspent unspent : unspentList) {
			accounTotalBalance = accounTotalBalance.add(unspent.getAmount().toBigInteger());
			accountUnspentUnspentList.add(unspent);

			if(accounTotalBalance.compareTo(btcBalance) > -1) {
				return accountUnspent;
			}
		}

		return accountUnspent;
	}

}
