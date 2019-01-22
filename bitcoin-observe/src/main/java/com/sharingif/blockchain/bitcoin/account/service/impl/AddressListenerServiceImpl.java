package com.sharingif.blockchain.bitcoin.account.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sharingif.blockchain.bitcoin.account.model.entity.AddressListener;
import com.sharingif.blockchain.bitcoin.account.dao.AddressListenerDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.bitcoin.account.service.AddressListenerService;

@Service
public class AddressListenerServiceImpl extends BaseServiceImpl<AddressListener, java.lang.String> implements AddressListenerService {
	
	private AddressListenerDAO addressListenerDAO;

	public AddressListenerDAO getAddressListenerDAO() {
		return addressListenerDAO;
	}
	@Resource
	public void setAddressListenerDAO(AddressListenerDAO addressListenerDAO) {
		super.setBaseDAO(addressListenerDAO);
		this.addressListenerDAO = addressListenerDAO;
	}
	
	
}
