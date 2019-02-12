package com.sharingif.blockchain.bitcoin.account.service.impl;


import javax.annotation.Resource;

import com.sharingif.blockchain.bitcoin.api.account.entity.AddressListenerAddReq;
import com.sharingif.blockchain.bitcoin.block.service.BitCoinBlockService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sharingif.blockchain.bitcoin.account.model.entity.AddressListener;
import com.sharingif.blockchain.bitcoin.account.dao.AddressListenerDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.bitcoin.account.service.AddressListenerService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AddressListenerServiceImpl extends BaseServiceImpl<AddressListener, java.lang.String> implements AddressListenerService, InitializingBean {

	private Map<String,String> addressMap = new HashMap<>();

	private AddressListenerDAO addressListenerDAO;
	private BitCoinBlockService bitCoinBlockService;
	private String btcWalletLabel;

	public AddressListenerDAO getAddressListenerDAO() {
		return addressListenerDAO;
	}
	@Resource
	public void setAddressListenerDAO(AddressListenerDAO addressListenerDAO) {
		super.setBaseDAO(addressListenerDAO);
		this.addressListenerDAO = addressListenerDAO;
	}
	@Value("${btc.wallet.label}")
	public void setBtcWalletLabel(String btcWalletLabel) {
		this.btcWalletLabel = btcWalletLabel;
	}

	protected void addAddressMap(String address) {
		addressMap.put(address, null);
	}

	@Override
	public void add(AddressListenerAddReq req) {
		String address = req.getAddress();

		bitCoinBlockService.importAddress(address, btcWalletLabel);

		AddressListener addressListener = new AddressListener();
		addressListener.setAddress(address);

		addressListenerDAO.insert(addressListener);

		addAddressMap(address);
	}

	@Override
	public boolean isWatch(String address) {
		if(addressMap.containsKey(address)) {
			return true;
		}

		return false;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		List<AddressListener> addressListenerList = addressListenerDAO.queryAll();
		for(AddressListener addressListener : addressListenerList) {
			addAddressMap(addressListener.getAddress());
		}
	}
	
	
}
