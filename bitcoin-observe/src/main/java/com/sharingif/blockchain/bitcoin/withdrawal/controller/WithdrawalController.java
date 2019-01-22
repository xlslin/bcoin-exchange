package com.sharingif.blockchain.bitcoin.withdrawal.controller;


import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sharingif.blockchain.bitcoin.withdrawal.service.WithdrawalService;


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
