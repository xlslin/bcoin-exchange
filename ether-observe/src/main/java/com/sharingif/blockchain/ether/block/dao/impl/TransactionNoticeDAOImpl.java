package com.sharingif.blockchain.ether.block.dao.impl;


import com.sharingif.blockchain.ether.app.dao.impl.BaseDAOImpl;
import com.sharingif.blockchain.ether.block.dao.TransactionNoticeDAO;
import com.sharingif.blockchain.ether.block.model.entity.TransactionNotice;
import org.springframework.stereotype.Repository;


@Repository
public class TransactionNoticeDAOImpl extends BaseDAOImpl<TransactionNotice, String> implements TransactionNoticeDAO {
	
}
