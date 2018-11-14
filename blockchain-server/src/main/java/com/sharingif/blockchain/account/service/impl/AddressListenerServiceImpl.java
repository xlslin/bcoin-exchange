package com.sharingif.blockchain.account.service.impl;


import javax.annotation.Resource;

import com.sharingif.blockchain.crypto.service.SecretKeyService;
import org.springframework.stereotype.Service;

import com.sharingif.blockchain.account.model.entity.AddressListener;
import com.sharingif.blockchain.account.dao.AddressListenerDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.account.service.AddressListenerService;

@Service
public class AddressListenerServiceImpl extends BaseServiceImpl<AddressListener, java.lang.String> implements AddressListenerService {
	
	private AddressListenerDAO addressListenerDAO;
	private SecretKeyService secretKeyService;

	public AddressListenerDAO getAddressListenerDAO() {
		return addressListenerDAO;
	}
	@Resource
	public void setAddressListenerDAO(AddressListenerDAO addressListenerDAO) {
		super.setBaseDAO(addressListenerDAO);
		this.addressListenerDAO = addressListenerDAO;
	}
	@Resource
	public void setSecretKeyService(SecretKeyService secretKeyService) {
		this.secretKeyService = secretKeyService;
	}

	@Override
	public void add(String address) {

		String blockType = secretKeyService.getBlockType(address);

		AddressListener addressListener = new AddressListener();
		addressListener.setAddress(address);
		addressListener.setBlockType(blockType);

		addressListenerDAO.insert(addressListener);
	}
}
