package com.sharingif.blockchain.ether.block.dao.impl;


import org.springframework.stereotype.Repository;


import com.sharingif.blockchain.ether.block.model.entity.TransactionNotice;
import com.sharingif.blockchain.ether.block.dao.TransactionNoticeDAO;
import com.sharingif.blockchain.ether.common.dao.impl.BaseDAOImpl;


@Repository
public class TransactionNoticeDAOImpl extends BaseDAOImpl<TransactionNotice,java.lang.String> implements TransactionNoticeDAO {
	
}
