package com.sharingif.blockchain.bitcoin.account.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sharingif.blockchain.bitcoin.account.model.entity.Account;
import com.sharingif.blockchain.bitcoin.account.dao.AccountDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.bitcoin.account.service.AccountService;

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
	
	
}
