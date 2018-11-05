package com.sharingif.blockchain.crypto.mnemonic.dao.impl;


import org.springframework.stereotype.Repository;


import com.sharingif.blockchain.crypto.mnemonic.model.entity.Mnemonic;
import com.sharingif.blockchain.crypto.mnemonic.dao.MnemonicDAO;
import com.sharingif.blockchain.crypto.app.dao.impl.BaseDAOImpl;


@Repository
public class MnemonicDAOImpl extends BaseDAOImpl<Mnemonic,java.lang.String> implements MnemonicDAO {
	
}
