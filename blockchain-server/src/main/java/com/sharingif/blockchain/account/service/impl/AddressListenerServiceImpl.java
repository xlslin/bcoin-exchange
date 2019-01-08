package com.sharingif.blockchain.account.service.impl;


import com.sharingif.blockchain.account.model.entity.BitCoin;
import com.sharingif.blockchain.account.service.AddressListenerService;
import com.sharingif.blockchain.api.account.entity.AddressListenerAddReq;
import com.sharingif.blockchain.ether.api.account.service.AddressListenerApiService;
import org.springframework.stereotype.Service;

@Service
public class AddressListenerServiceImpl implements AddressListenerService {

	private AddressListenerApiService addressListenerApiService;

	public void setAddressListenerApiService(AddressListenerApiService addressListenerApiService) {
		this.addressListenerApiService = addressListenerApiService;
	}

	@Override
	public void add(String blockType, String address) {
		if(BitCoin.BLOCK_TYPE_ETHER.equals(blockType)) {
			com.sharingif.blockchain.ether.api.account.entity.AddressListenerAddReq req = new com.sharingif.blockchain.ether.api.account.entity.AddressListenerAddReq();
			req.setAddress(address);

			addressListenerApiService.add(req);
		}

	}
}
