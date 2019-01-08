package com.sharingif.blockchain.ether.account.controller;


import com.sharingif.blockchain.ether.account.service.AddressListenerService;
import com.sharingif.blockchain.ether.api.account.entity.AddressListenerAddReq;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;
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

	/**
	 * 添加地址监听
	 * @return
	 */
	@RequestMapping(value="add", method= RequestMethod.POST)
	public void add(AddressListenerAddReq req) {
		addressListenerService.add(req);
	}
	
}
