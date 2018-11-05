package com.sharingif.blockchain.crypto.key.dao.impl;


import com.sharingif.blockchain.crypto.app.dao.impl.BaseDAOImpl;
import org.springframework.stereotype.Repository;


import com.sharingif.blockchain.crypto.key.model.entity.ExtendedKey;
import com.sharingif.blockchain.crypto.key.dao.ExtendedKeyDAO;


@Repository
public class ExtendedKeyDAOImpl extends BaseDAOImpl<ExtendedKey, String> implements ExtendedKeyDAO {
	
}
