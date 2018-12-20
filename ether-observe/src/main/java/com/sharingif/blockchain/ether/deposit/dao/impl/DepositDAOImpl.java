package com.sharingif.blockchain.ether.deposit.dao.impl;


import com.sharingif.blockchain.ether.app.dao.impl.BaseDAOImpl;
import com.sharingif.blockchain.ether.deposit.dao.DepositDAO;
import com.sharingif.blockchain.ether.deposit.model.entity.Deposit;
import org.springframework.stereotype.Repository;


@Repository
public class DepositDAOImpl extends BaseDAOImpl<Deposit, String> implements DepositDAO {
	
}
