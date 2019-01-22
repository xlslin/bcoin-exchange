package com.sharingif.blockchain.account.service.impl;


import com.sharingif.blockchain.account.model.entity.BitCoin;
import com.sharingif.blockchain.account.service.AddressListenerService;
import com.sharingif.blockchain.ether.api.account.service.AddressListenerApiService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AddressListenerServiceImpl implements AddressListenerService {

	private com.sharingif.blockchain.ether.api.account.service.AddressListenerApiService etherAddressListenerApiService;
	private com.sharingif.blockchain.bitcoin.api.account.service.AddressListenerApiService bitCoinAddressListenerApiService;

	@Resource
	public void setEtherAddressListenerApiService(AddressListenerApiService etherAddressListenerApiService) {
		this.etherAddressListenerApiService = etherAddressListenerApiService;
	}
	@Resource
	public void setBitCoinAddressListenerApiService(com.sharingif.blockchain.bitcoin.api.account.service.AddressListenerApiService bitCoinAddressListenerApiService) {
		this.bitCoinAddressListenerApiService = bitCoinAddressListenerApiService;
	}

	@Override
	public void add(String blockType, String address) {
		if(BitCoin.BLOCK_TYPE_ETHER.equals(blockType)) {
			com.sharingif.blockchain.bitcoin.api.account.entity.AddressListenerAddReq req = new com.sharingif.blockchain.bitcoin.api.account.entity.AddressListenerAddReq();
			req.setAddress(address);

			bitCoinAddressListenerApiService.add(req);
		}

		if(BitCoin.BLOCK_TYPE_ETHER.equals(blockType)) {
			com.sharingif.blockchain.ether.api.account.entity.AddressListenerAddReq req = new com.sharingif.blockchain.ether.api.account.entity.AddressListenerAddReq();
			req.setAddress(address);

			etherAddressListenerApiService.add(req);
		}

	}
}
