package com.sharingif.blockchain.crypto.key.dao.impl;


import com.sharingif.blockchain.crypto.app.dao.impl.BaseDAOImpl;
import org.springframework.stereotype.Repository;


import com.sharingif.blockchain.crypto.key.model.entity.SecretKey;
import com.sharingif.blockchain.crypto.key.dao.SecretKeyDAO;


@Repository
public class SecretKeyDAOImpl extends BaseDAOImpl<SecretKey, String> implements SecretKeyDAO {
	
}
