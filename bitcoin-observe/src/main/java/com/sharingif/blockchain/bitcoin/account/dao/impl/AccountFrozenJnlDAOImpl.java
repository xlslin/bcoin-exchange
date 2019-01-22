package com.sharingif.blockchain.bitcoin.account.dao.impl;


import com.sharingif.blockchain.bitcoin.account.dao.AccountFrozenJnlDAO;
import com.sharingif.blockchain.bitcoin.account.model.entity.AccountFrozenJnl;
import com.sharingif.blockchain.bitcoin.app.dao.impl.BaseDAOImpl;
import org.springframework.stereotype.Repository;


@Repository
public class AccountFrozenJnlDAOImpl extends BaseDAOImpl<AccountFrozenJnl, String> implements AccountFrozenJnlDAO {
	
}
