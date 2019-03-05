package com.sharingif.blockchain.ether.account.service.impl;


import com.sharingif.blockchain.ether.account.dao.AddressListenerDAO;
import com.sharingif.blockchain.ether.account.model.entity.AddressListener;
import com.sharingif.blockchain.ether.account.service.AddressListenerService;
import com.sharingif.blockchain.ether.api.account.entity.AddressListenerAddReq;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AddressListenerServiceImpl extends BaseServiceImpl<AddressListener, java.lang.String> implements AddressListenerService, InitializingBean {

	private Map<String,String> addressMap;

	private AddressListenerDAO addressListenerDAO;

	public AddressListenerDAO getAddressListenerDAO() {
		return addressListenerDAO;
	}
	@Resource
	public void setAddressListenerDAO(AddressListenerDAO addressListenerDAO) {
		super.setBaseDAO(addressListenerDAO);
		this.addressListenerDAO = addressListenerDAO;
	}

	protected void addAddressMap(String address) {
		addressMap.put(address, address);
	}

	@Override
	public void add(AddressListenerAddReq req) {
		String address = req.getAddress().toLowerCase();

		AddressListener addressListener = new AddressListener();
		addressListener.setAddress(address);

		addressListenerDAO.insert(addressListener);

		addAddressMap(address);
	}

	@Override
	public boolean isWatch(String address) {
		if(addressMap.containsKey(address.toLowerCase())) {
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
