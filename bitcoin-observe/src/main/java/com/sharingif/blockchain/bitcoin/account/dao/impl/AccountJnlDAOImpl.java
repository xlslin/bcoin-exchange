package com.sharingif.blockchain.bitcoin.account.dao.impl;


import com.sharingif.blockchain.bitcoin.account.dao.AccountJnlDAO;
import com.sharingif.blockchain.bitcoin.account.model.entity.AccountJnl;
import com.sharingif.blockchain.bitcoin.app.dao.impl.BaseDAOImpl;
import org.springframework.stereotype.Repository;


@Repository
public class AccountJnlDAOImpl extends BaseDAOImpl<AccountJnl, String> implements AccountJnlDAO {
	
}
