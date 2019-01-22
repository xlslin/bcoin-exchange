package com.sharingif.blockchain.bitcoin.block.dao.impl;


import com.sharingif.blockchain.bitcoin.app.dao.impl.BaseDAOImpl;
import com.sharingif.blockchain.bitcoin.block.dao.TransactionBusinessDAO;
import com.sharingif.blockchain.bitcoin.block.model.entity.TransactionBusiness;
import org.springframework.stereotype.Repository;


@Repository
public class TransactionBusinessDAOImpl extends BaseDAOImpl<TransactionBusiness, String> implements TransactionBusinessDAO {
	
}
