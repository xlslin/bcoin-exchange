package com.sharingif.blockchain.bitcoin.account.dao.impl;


import com.sharingif.blockchain.bitcoin.account.dao.AccountDAO;
import com.sharingif.blockchain.bitcoin.account.model.entity.Account;
import com.sharingif.blockchain.bitcoin.app.dao.impl.BaseDAOImpl;
import org.springframework.stereotype.Repository;


@Repository
public class AccountDAOImpl extends BaseDAOImpl<Account, String> implements AccountDAO {
	
}
