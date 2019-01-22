package com.sharingif.blockchain.bitcoin.deposit.controller;


import com.sharingif.blockchain.bitcoin.deposit.service.DepositService;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;


@Controller
@RequestMapping(value="deposit")
public class DepositController {
	
	private DepositService depositService;

	public DepositService getDepositService() {
		return depositService;
	}
	@Resource
	public void setDepositService(DepositService depositService) {
		this.depositService = depositService;
	}
	
}
