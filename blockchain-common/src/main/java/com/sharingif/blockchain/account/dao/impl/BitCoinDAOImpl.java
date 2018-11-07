package com.sharingif.blockchain.account.dao.impl;


import org.springframework.stereotype.Repository;


import com.sharingif.blockchain.account.model.entity.BitCoin;
import com.sharingif.blockchain.account.dao.BitCoinDAO;
import com.sharingif.blockchain.common.dao.impl.BaseDAOImpl;


@Repository
public class BitCoinDAOImpl extends BaseDAOImpl<BitCoin,java.lang.String> implements BitCoinDAO {
	
}
