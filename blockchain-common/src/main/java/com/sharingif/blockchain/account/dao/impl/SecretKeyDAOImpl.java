package com.sharingif.blockchain.account.dao.impl;


import com.sharingif.blockchain.account.dao.SecretKeyDAO;
import com.sharingif.blockchain.common.dao.impl.BaseDAOImpl;
import com.sharingif.blockchain.crypto.model.entity.SecretKey;
import org.springframework.stereotype.Repository;


@Repository
public class SecretKeyDAOImpl extends BaseDAOImpl<SecretKey,java.lang.String> implements SecretKeyDAO {
	
}
