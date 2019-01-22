package com.sharingif.blockchain.bitcoin.block.dao.impl;


import com.sharingif.blockchain.bitcoin.app.dao.impl.BaseDAOImpl;
import com.sharingif.blockchain.bitcoin.block.dao.TransactionBusinessAccountDAO;
import com.sharingif.blockchain.bitcoin.block.model.entity.TransactionBusinessAccount;
import org.springframework.stereotype.Repository;


@Repository
public class TransactionBusinessAccountDAOImpl extends BaseDAOImpl<TransactionBusinessAccount, String> implements TransactionBusinessAccountDAO {
	
}
