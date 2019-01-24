package com.sharingif.blockchain.bitcoin.withdrawal.controller;


import com.sharingif.blockchain.bitcoin.withdrawal.service.WithdrawalTransactionService;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;


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
