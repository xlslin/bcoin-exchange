package com.sharingif.blockchain.ether.account.service.impl;


import com.sharingif.blockchain.ether.account.dao.AccountDAO;
import com.sharingif.blockchain.ether.account.model.entity.Account;
import com.sharingif.blockchain.ether.account.model.entity.AccountFrozenJnl;
import com.sharingif.blockchain.ether.account.model.entity.AccountJnl;
import com.sharingif.blockchain.ether.account.service.AccountFrozenJnlService;
import com.sharingif.blockchain.ether.account.service.AccountJnlService;
import com.sharingif.blockchain.ether.account.service.AccountService;
import com.sharingif.blockchain.ether.app.autoconfigure.constants.CoinType;
import com.sharingif.blockchain.ether.app.constants.ErrorConstants;
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
	public BigInteger getBalance(String address, String coinType) {
		Account account = new Account();
		account.setAddress(address);
		account.setCoinType(coinType);

		Account queryAccount = accountDAO.query(account);

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
		PaginationCondition<Account> paginationCondition = new PaginationCondition<Account>();
		paginationCondition.setCondition(queryAccount);
		paginationCondition.setQueryCount(false);
		paginationCondition.setCurrentPage(1);
		paginationCondition.setPageSize(20);
		PaginationRepertory<Account> accountPaginationRepertory = accountDAO.queryPaginationListByCoinTypeBalance(paginationCondition);
		List<Account> accountList = accountPaginationRepertory.getPageItems();
		if(accountList == null || accountList.isEmpty()) {
			return null;
		}

		for(Account account : accountList) {
			BigInteger blockBalance = ethereumBlockService.getBalance(account.getAddress());
			if(blockBalance.compareTo(balance) >= 0) {
				return account;
			}
		}

		return null;
	}

	public Account getEthContractAccount(String coinType, BigInteger balance, BigInteger fee, String contractAddress, int currentPage) {
		Account queryAccount = new Account();
		queryAccount.setCoinType(coinType);
		queryAccount.setBalance(balance);
		PaginationCondition<Account> paginationCondition = new PaginationCondition<Account>();
		paginationCondition.setCondition(queryAccount);
		paginationCondition.setQueryCount(false);
		paginationCondition.setCurrentPage(currentPage);
		paginationCondition.setPageSize(20);
		PaginationRepertory<Account> accountPaginationRepertory = accountDAO.queryPaginationListByCoinTypeBalance(paginationCondition);
		List<Account> accountList = accountPaginationRepertory.getPageItems();
		if(accountList == null || accountList.isEmpty()) {
			return null;
		}

		for(Account account : accountList) {
			Account queryBalanceAccount = new Account();
			queryBalanceAccount.setAddress(account.getAddress());
			queryBalanceAccount.setCoinType(CoinType.ETH.name());
			Account balanceAccount = accountDAO.query(queryBalanceAccount);
			if(balanceAccount.getBalance().compareTo(fee) < 0) {
				continue;
			}

			BigInteger blockEthBalance = ethereumBlockService.getBalance(account.getAddress());
			if(blockEthBalance.compareTo(fee) < 0 ) {
				continue;
			}
			BigInteger blockContractBalance = ethereumBlockService.getErc20ContractService().balanceOf(account.getAddress(), contractAddress);
			if (blockContractBalance.compareTo(balance) < 0) {
				continue;
			}

			return account;
		}

		return getEthContractAccount(coinType, balance, fee, contractAddress,currentPage+1);
	}

	@Override
	public Account getAccount(String coinType, BigInteger balance, BigInteger fee, String contractAddress) {

		if(CoinType.ETH.name().equals(coinType)) {
			return getEthAccount(balance, fee);
		} else {
			return getEthContractAccount(coinType, balance, fee, contractAddress,1);
		}

	}
}
