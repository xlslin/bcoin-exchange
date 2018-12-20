package com.sharingif.blockchain.account.service.impl;


import javax.annotation.Resource;

import com.sharingif.blockchain.api.account.entity.AddressListenerAddReq;
import com.sharingif.blockchain.api.account.entity.AddressListenerIsWatchReq;
import com.sharingif.blockchain.api.account.entity.AddressListenerIsWatchRsp;
import com.sharingif.blockchain.crypto.service.SecretKeyService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.sharingif.blockchain.account.model.entity.AddressListener;
import com.sharingif.blockchain.account.dao.AddressListenerDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.account.service.AddressListenerService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AddressListenerServiceImpl extends BaseServiceImpl<AddressListener, java.lang.String> implements AddressListenerService, InitializingBean {
	
	private AddressListenerDAO addressListenerDAO;
	private SecretKeyService secretKeyService;
	private Map<String,Map<String,String>> addressMap = new HashMap<>();

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

	protected void add(String blockType, String address) {
		AddressListener addressListener = new AddressListener();
		addressListener.setBlockType(blockType);
		addressListener.setAddress(address);

		addressListenerDAO.insert(addressListener);

		addAddressMap(address, blockType);
	}

	@Override
	public void add(String address) {
		String blockType = secretKeyService.getBlockType(address);

		add(blockType, address);
	}

	@Override
	public void add(AddressListenerAddReq req) {
		add(req.getBlockType(), req.getAddress());
	}

	@Override
	public AddressListenerIsWatchRsp isWatch(AddressListenerIsWatchReq req) {
		AddressListenerIsWatchRsp rsp = new AddressListenerIsWatchRsp();
		rsp.setWatch(true);

		Map<String,String> addressListenerMap = addressMap.get(req.getBlockType());
		if(addressListenerMap == null) {
			rsp.setWatch(false);
		}

		if(addressListenerMap.get(req.getAddress()) == null) {
			rsp.setWatch(false);
		}

		return rsp;
	}

	protected void addAddressMap(String address, String blockType) {
		Map<String,String> addressListenerMap = addressMap.get(blockType);
		if(addressListenerMap == null){
			addressListenerMap = new HashMap<String, String>(200);
			addressListenerMap.put(address, address);
			addressMap.put(blockType, addressListenerMap);
		} else {
			addressListenerMap.put(address, address);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		List<AddressListener> addressListenerList = addressListenerDAO.queryAll();
		for(AddressListener addressListener : addressListenerList) {
			addAddressMap(addressListener.getAddress(), addressListener.getBlockType());
		}
	}
}
