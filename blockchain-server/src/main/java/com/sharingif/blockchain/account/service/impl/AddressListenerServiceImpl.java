package com.sharingif.blockchain.account.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sharingif.blockchain.account.model.entity.AddressListener;
import com.sharingif.blockchain.account.dao.AddressListenerDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.account.service.AddressListenerService;

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


	@Override
	public void add(String address) {

		AddressListener addressListener = new AddressListener();
		addressListener.setAddress(address);

		addressListenerDAO.insert(addressListener);
	}
}
