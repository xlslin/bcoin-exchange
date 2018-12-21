package com.sharingif.blockchain.ether.block.dao.impl;


import com.sharingif.blockchain.ether.app.dao.impl.BaseDAOImpl;
import com.sharingif.blockchain.ether.block.dao.TransactionTempDAO;
import com.sharingif.blockchain.ether.block.model.entity.TransactionTemp;
import org.springframework.stereotype.Repository;


@Repository
public class TransactionTempDAOImpl extends BaseDAOImpl<TransactionTemp, String> implements TransactionTempDAO {
	
}
