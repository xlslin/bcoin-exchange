package com.sharingif.blockchain.ether.account.dao.impl;


import com.sharingif.blockchain.ether.account.dao.AddressListenerDAO;
import com.sharingif.blockchain.ether.account.model.entity.AddressListener;
import com.sharingif.blockchain.ether.app.dao.impl.BaseDAOImpl;
import org.springframework.stereotype.Repository;


@Repository
public class AddressListenerDAOImpl extends BaseDAOImpl<AddressListener, String> implements AddressListenerDAO {
	
}
