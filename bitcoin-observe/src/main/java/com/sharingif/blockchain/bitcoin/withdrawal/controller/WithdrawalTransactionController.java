package com.sharingif.blockchain.bitcoin.withdrawal.controller;


import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sharingif.blockchain.bitcoin.withdrawal.service.WithdrawalTransactionService;


@Controller
@RequestMapping(value="withdrawalTransaction")
public class WithdrawalTransactionController {
	
	private WithdrawalTransactionService withdrawalTransactionService;

	public WithdrawalTransactionService getWithdrawalTransactionService() {
		return withdrawalTransactionService;
	}
	@Resource
	public void setWithdrawalTransactionService(WithdrawalTransactionService withdrawalTransactionService) {
		this.withdrawalTransactionService = withdrawalTransactionService;
	}
	
}
