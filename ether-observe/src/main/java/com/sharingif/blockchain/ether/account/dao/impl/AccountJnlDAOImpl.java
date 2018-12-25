package com.sharingif.blockchain.ether.account.dao.impl;


import com.sharingif.blockchain.ether.account.dao.AccountJnlDAO;
import com.sharingif.blockchain.ether.account.model.entity.AccountJnl;
import com.sharingif.blockchain.ether.app.dao.impl.BaseDAOImpl;
import org.springframework.stereotype.Repository;


@Repository
public class AccountJnlDAOImpl extends BaseDAOImpl<AccountJnl, String> implements AccountJnlDAO {
	
}
