package com.sharingif.blockchain.bitcoin.account.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sharingif.blockchain.bitcoin.account.model.entity.AccountJnl;
import com.sharingif.blockchain.bitcoin.account.dao.AccountJnlDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.bitcoin.account.service.AccountJnlService;

@Service
public class AccountJnlServiceImpl extends BaseServiceImpl<AccountJnl, java.lang.String> implements AccountJnlService {
	
	private AccountJnlDAO accountJnlDAO;

	public AccountJnlDAO getAccountJnlDAO() {
		return accountJnlDAO;
	}
	@Resource
	public void setAccountJnlDAO(AccountJnlDAO accountJnlDAO) {
		super.setBaseDAO(accountJnlDAO);
		this.accountJnlDAO = accountJnlDAO;
	}

	
}
