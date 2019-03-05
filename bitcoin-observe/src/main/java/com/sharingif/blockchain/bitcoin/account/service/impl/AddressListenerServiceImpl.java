package com.sharingif.blockchain.bitcoin.account.service.impl;


import com.sharingif.blockchain.bitcoin.account.dao.AddressListenerDAO;
import com.sharingif.blockchain.bitcoin.account.model.entity.AddressListener;
import com.sharingif.blockchain.bitcoin.account.service.AddressListenerService;
import com.sharingif.blockchain.bitcoin.api.account.entity.AddressListenerAddReq;
import com.sharingif.blockchain.bitcoin.block.service.BitCoinBlockService;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AddressListenerServiceImpl extends BaseServiceImpl<AddressListener, java.lang.String> implements AddressListenerService, InitializingBean {

	private Map<String,String> addressMap;

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
	@Resource
	public void setBitCoinBlockService(BitCoinBlockService bitCoinBlockService) {
		this.bitCoinBlockService = bitCoinBlockService;
	}
	@Value("${btc.wallet.label}")
	public void setBtcWalletLabel(String btcWalletLabel) {
		this.btcWalletLabel = btcWalletLabel;
	}

	protected void addAddressMap(String address) {
		addressMap.put(address, address);
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

		addressMap = new ConcurrentHashMap<String,String>(addressListenerList.size()+((int) (addressListenerList.size()*0.3)));

		for(AddressListener addressListener : addressListenerList) {
			addAddressMap(addressListener.getAddress());
		}
	}
	
	
}
