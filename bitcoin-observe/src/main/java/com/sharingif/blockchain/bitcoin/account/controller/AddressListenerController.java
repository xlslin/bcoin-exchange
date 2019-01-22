package com.sharingif.blockchain.bitcoin.account.controller;


import com.sharingif.blockchain.bitcoin.account.service.AddressListenerService;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;


@Controller
@RequestMapping(value="addressListener")
public class AddressListenerController {
	
	private AddressListenerService addressListenerService;

	public AddressListenerService getAddressListenerService() {
		return addressListenerService;
	}
	@Resource
	public void setAddressListenerService(AddressListenerService addressListenerService) {
		this.addressListenerService = addressListenerService;
	}
	
}
