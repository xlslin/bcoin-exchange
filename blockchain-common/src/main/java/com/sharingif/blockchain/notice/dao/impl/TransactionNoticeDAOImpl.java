package com.sharingif.blockchain.notice.dao.impl;


import org.springframework.stereotype.Repository;


import com.sharingif.blockchain.notice.model.entity.TransactionNotice;
import com.sharingif.blockchain.notice.dao.TransactionNoticeDAO;
import com.sharingif.blockchain.common.dao.impl.BaseDAOImpl;


@Repository
public class TransactionNoticeDAOImpl extends BaseDAOImpl<TransactionNotice,java.lang.String> implements TransactionNoticeDAO {
	
}
