package com.sharingif.blockchain.bitcoin.block.dao.impl;


import com.sharingif.blockchain.bitcoin.app.dao.impl.BaseDAOImpl;
import com.sharingif.blockchain.bitcoin.block.dao.TransactionDAO;
import com.sharingif.blockchain.bitcoin.block.model.entity.Transaction;
import org.springframework.stereotype.Repository;


@Repository
public class TransactionDAOImpl extends BaseDAOImpl<Transaction, String> implements TransactionDAO {
	
}
