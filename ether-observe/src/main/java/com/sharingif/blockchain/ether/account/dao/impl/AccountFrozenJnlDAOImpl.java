package com.sharingif.blockchain.ether.account.dao.impl;


import com.sharingif.blockchain.ether.account.dao.AccountFrozenJnlDAO;
import com.sharingif.blockchain.ether.account.model.entity.AccountFrozenJnl;
import com.sharingif.blockchain.ether.app.dao.impl.BaseDAOImpl;
import org.springframework.stereotype.Repository;


@Repository
public class AccountFrozenJnlDAOImpl extends BaseDAOImpl<AccountFrozenJnl, String> implements AccountFrozenJnlDAO {
	
}
