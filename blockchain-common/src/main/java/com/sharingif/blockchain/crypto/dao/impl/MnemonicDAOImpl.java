package com.sharingif.blockchain.crypto.dao.impl;


import org.springframework.stereotype.Repository;


import com.sharingif.blockchain.crypto.model.entity.Mnemonic;
import com.sharingif.blockchain.crypto.dao.MnemonicDAO;
import com.sharingif.blockchain.common.dao.impl.BaseDAOImpl;


@Repository
public class MnemonicDAOImpl extends BaseDAOImpl<Mnemonic,java.lang.String> implements MnemonicDAO {
	
}
