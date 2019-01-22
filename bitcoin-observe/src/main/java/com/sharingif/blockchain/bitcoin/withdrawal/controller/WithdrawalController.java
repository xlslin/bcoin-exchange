package com.sharingif.blockchain.bitcoin.withdrawal.controller;


import com.sharingif.blockchain.bitcoin.withdrawal.service.WithdrawalService;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;


@Controller
@RequestMapping(value="withdrawal")
public class WithdrawalController {
	
	private WithdrawalService withdrawalService;

	public WithdrawalService getWithdrawalService() {
		return withdrawalService;
	}
	@Resource
	public void setWithdrawalService(WithdrawalService withdrawalService) {
		this.withdrawalService = withdrawalService;
	}
	
}
