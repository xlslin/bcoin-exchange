package com.sharingif.blockchain.ether.account.controller;


import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sharingif.blockchain.ether.account.service.AddressListenerService;


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
