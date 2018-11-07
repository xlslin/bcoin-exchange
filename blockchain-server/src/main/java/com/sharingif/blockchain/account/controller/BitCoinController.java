package com.sharingif.blockchain.account.controller;


import com.sharingif.blockchain.account.service.BitCoinService;
import com.sharingif.blockchain.api.account.entity.BitCoinAddReq;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;


@Controller
@RequestMapping(value="bitCoin")
public class BitCoinController {
	
	private BitCoinService bitCoinService;

	public BitCoinService getBitCoinService() {
		return bitCoinService;
	}
	@Resource
	public void setBitCoinService(BitCoinService bitCoinService) {
		this.bitCoinService = bitCoinService;
	}


	/**
	 * 生成change ExtendedKey
	 * @return
	 */
	@RequestMapping(value="add", method= RequestMethod.POST)
	public void add(BitCoinAddReq req) {

		bitCoinService.add(req);
	}
}
