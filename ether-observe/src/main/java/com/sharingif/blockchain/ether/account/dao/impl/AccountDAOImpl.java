package com.sharingif.blockchain.ether.account.dao.impl;


import com.sharingif.blockchain.ether.account.dao.AccountDAO;
import com.sharingif.blockchain.ether.account.model.entity.Account;
import com.sharingif.blockchain.ether.app.dao.impl.BaseDAOImpl;
import org.springframework.stereotype.Repository;


@Repository
public class AccountDAOImpl extends BaseDAOImpl<Account, String> implements AccountDAO {
	
}
