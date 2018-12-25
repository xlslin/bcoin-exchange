package com.sharingif.blockchain.ether.account.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sharingif.blockchain.ether.account.model.entity.AccountFrozenJnl;
import com.sharingif.blockchain.ether.account.dao.AccountFrozenJnlDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.ether.account.service.AccountFrozenJnlService;

@Service
public class AccountFrozenJnlServiceImpl extends BaseServiceImpl<AccountFrozenJnl, java.lang.String> implements AccountFrozenJnlService {
	
	private AccountFrozenJnlDAO accountFrozenJnlDAO;

	public AccountFrozenJnlDAO getAccountFrozenJnlDAO() {
		return accountFrozenJnlDAO;
	}
	@Resource
	public void setAccountFrozenJnlDAO(AccountFrozenJnlDAO accountFrozenJnlDAO) {
		super.setBaseDAO(accountFrozenJnlDAO);
		this.accountFrozenJnlDAO = accountFrozenJnlDAO;
	}
	
	
}
