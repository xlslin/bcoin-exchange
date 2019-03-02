package com.sharingif.blockchain.ether.account.service.impl;


import com.sharingif.blockchain.ether.account.dao.AccountDAO;
import com.sharingif.blockchain.ether.account.model.entity.Account;
import com.sharingif.blockchain.ether.account.model.entity.AccountFrozenJnl;
import com.sharingif.blockchain.ether.account.model.entity.AccountJnl;
import com.sharingif.blockchain.ether.account.model.entity.SubAccount;
import com.sharingif.blockchain.ether.account.service.AccountFrozenJnlService;
import com.sharingif.blockchain.ether.account.service.AccountJnlService;
import com.sharingif.blockchain.ether.account.service.AccountService;
import com.sharingif.blockchain.ether.app.autoconfigure.constants.CoinType;
import com.sharingif.blockchain.ether.app.constants.ErrorConstants;
import com.sharingif.blockchain.ether.app.exception.AccountException;
import com.sharingif.blockchain.ether.block.service.EthereumBlockService;
import com.sharingif.cube.core.exception.validation.ValidationCubeException;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Service
public class AccountServiceImpl extends BaseServiceImpl<Account, java.lang.String> implements AccountService {
	
	private AccountDAO accountDAO;
	private AccountJnlService accountJnlService;
	private AccountFrozenJnlService accountFrozenJnlService;
	private EthereumBlockService ethereumBlockService;

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
	public void setEthereumBlockService(EthereumBlockService ethereumBlockService) {
		this.ethereumBlockService = ethereumBlockService;
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
		Account account = new Account();
		account.setAddress(address);
		account.setCoinType(coinType);
		account.setStatus(Account.STATUS_NORMAL);

		return accountDAO.updateById(account);
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

	public Account getEthAccount(BigInteger balance, BigInteger fee) {
		Account queryAccount = new Account();
		queryAccount.setCoinType(CoinType.ETH.name());
		queryAccount.setBalance(balance.add(fee));
		queryAccount.setStatus(Account.STATUS_NORMAL);
		PaginationCondition<Account> paginationCondition = new PaginationCondition<Account>();
		paginationCondition.setCondition(queryAccount);
		paginationCondition.setQueryCount(false);
		paginationCondition.setCurrentPage(1);
		paginationCondition.setPageSize(1);
		PaginationRepertory<Account> accountPaginationRepertory = accountDAO.queryPaginationListByCoinTypeBalance(paginationCondition);
		List<Account> accountList = accountPaginationRepertory.getPageItems();
		if(accountList == null || accountList.isEmpty()) {
			return null;
		}

		Account account = accountList.get(0);
		BigInteger blockBalance = ethereumBlockService.getBalance(account.getAddress());
		if(blockBalance.compareTo(balance) != 0) {
			logger.error("account exception, coin type:{}, account balance:{}, block balance:{}", account.getCoinType(), account.getBalance(), blockBalance);
			exceptionAccount(account.getId());
			throw new AccountException();
		}

		return account;
	}

	public Account getEthContractAccount(String coinType, BigInteger balance, BigInteger fee, String contractAddress) {
		SubAccount querySubAccount = new SubAccount();
		querySubAccount.setCoinType(CoinType.ETH.name());
		querySubAccount.setBalance(fee);
		querySubAccount.setSubCoinType(coinType);
		querySubAccount.setSubBalance(balance);
		querySubAccount.setStatus(Account.STATUS_NORMAL);
		PaginationCondition<SubAccount> paginationCondition = new PaginationCondition<SubAccount>();
		paginationCondition.setCondition(querySubAccount);
		paginationCondition.setQueryCount(false);
		paginationCondition.setCurrentPage(1);
		paginationCondition.setPageSize(1);
		PaginationRepertory<Account> accountPaginationRepertory = accountDAO.queryPaginationListByCoinTypeSubCoinTypeBalanceSubBalance(paginationCondition);
		List<Account> accountList = accountPaginationRepertory.getPageItems();
		if(accountList == null || accountList.isEmpty()) {
			return null;
		}

		Account account = accountList.get(0);
		Account ethAccount = getAccount(account.getAddress(), CoinType.ETH.name());

		BigInteger blockEthBalance = ethereumBlockService.getBalance(account.getAddress());
		if(blockEthBalance.compareTo(ethAccount.getBalance()) != 0) {
			logger.error("account exception, coin type:{}, account balance:{}, block balance:{}", CoinType.ETH.name(), ethAccount.getBalance(), blockEthBalance);
			exceptionAccount(account.getId());
			throw new AccountException();
		}

		Account contractAccount = getAccount(account.getAddress(), coinType);
		BigInteger blockContractBalance = ethereumBlockService.getErc20ContractService().balanceOf(account.getAddress(), contractAddress);
		if (blockContractBalance.compareTo(contractAccount.getBalance()) != 0) {
			logger.error("account exception, coin type:{}, account balance:{}, block balance:{}", coinType, contractAccount.getBalance(), blockContractBalance);
			exceptionAccount(account.getId());
			throw new AccountException();
		}

		return contractAccount;
	}

	@Override
	public Account getAccount(String coinType, BigInteger balance, BigInteger fee, String contractAddress) {

		if(CoinType.ETH.name().equals(coinType)) {
			return getEthAccount(balance, fee);
		} else {
			return getEthContractAccount(coinType, balance, fee, contractAddress);
		}

	}
}
