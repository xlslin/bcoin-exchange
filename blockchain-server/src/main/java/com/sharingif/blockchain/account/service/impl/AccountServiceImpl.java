package com.sharingif.blockchain.account.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sharingif.blockchain.account.model.entity.Account;
import com.sharingif.blockchain.account.dao.AccountDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.account.service.AccountService;

import java.math.BigInteger;

@Service
public class AccountServiceImpl extends BaseServiceImpl<Account, java.lang.String> implements AccountService {
	
	private AccountDAO accountDAO;

	public AccountDAO getAccountDAO() {
		return accountDAO;
	}
	@Resource
	public void setAccountDAO(AccountDAO accountDAO) {
		super.setBaseDAO(accountDAO);
		this.accountDAO = accountDAO;
	}

	@Override
	public void initAccount(String coinType, String address) {
		Account account = new Account();
		account.setAddress(address);
		account.setCoinType(coinType);
		account.setTotalIn(BigInteger.ZERO);
		account.setTotalOut(BigInteger.ZERO);
		account.setBalance(BigInteger.ZERO);
		account.setFrozenAmount(BigInteger.ZERO);
		account.setStatus(Account.STATUS_NORMAL);

		accountDAO.insert(account);
	}

}
