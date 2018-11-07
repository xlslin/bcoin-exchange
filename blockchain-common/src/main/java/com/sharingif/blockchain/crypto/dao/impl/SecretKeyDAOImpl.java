package com.sharingif.blockchain.crypto.dao.impl;


import com.sharingif.blockchain.common.dao.impl.BaseDAOImpl;
import com.sharingif.blockchain.crypto.dao.SecretKeyDAO;
import com.sharingif.blockchain.crypto.model.entity.SecretKey;
import org.springframework.stereotype.Repository;


@Repository
public class SecretKeyDAOImpl extends BaseDAOImpl<SecretKey,java.lang.String> implements SecretKeyDAO {
	
}
