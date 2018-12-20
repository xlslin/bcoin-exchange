package com.sharingif.blockchain.ether.block.dao.impl;


import com.sharingif.blockchain.ether.app.dao.impl.BaseDAOImpl;
import com.sharingif.blockchain.ether.block.dao.ContractDAO;
import com.sharingif.blockchain.ether.block.model.entity.Contract;
import org.springframework.stereotype.Repository;


@Repository
public class ContractDAOImpl extends BaseDAOImpl<Contract, String> implements ContractDAO {
	
}
