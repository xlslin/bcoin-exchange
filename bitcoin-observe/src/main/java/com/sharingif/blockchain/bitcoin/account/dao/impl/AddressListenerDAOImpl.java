package com.sharingif.blockchain.bitcoin.account.dao.impl;


import com.sharingif.blockchain.bitcoin.account.dao.AddressListenerDAO;
import com.sharingif.blockchain.bitcoin.account.model.entity.AddressListener;
import com.sharingif.blockchain.bitcoin.app.dao.impl.BaseDAOImpl;
import org.springframework.stereotype.Repository;


@Repository
public class AddressListenerDAOImpl extends BaseDAOImpl<AddressListener, String> implements AddressListenerDAO {
	
}
