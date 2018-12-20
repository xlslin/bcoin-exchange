package com.sharingif.blockchain.ether.deposit.controller;


import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sharingif.blockchain.ether.deposit.service.DepositService;


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
