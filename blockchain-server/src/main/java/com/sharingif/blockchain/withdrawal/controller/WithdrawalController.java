package com.sharingif.blockchain.withdrawal.controller;


import com.sharingif.blockchain.bitcoin.api.withdrawal.entity.ApplyWithdrawalBitCoinReq;
import com.sharingif.blockchain.bitcoin.api.withdrawal.entity.ApplyWithdrawalBitCoinRsp;
import com.sharingif.blockchain.ether.api.withdrawal.entity.WithdrawalEtherReq;
import com.sharingif.blockchain.ether.api.withdrawal.entity.WithdrawalEtherRsp;
import com.sharingif.blockchain.withdrawal.service.WithdrawalService;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;


@Controller
@RequestMapping(value="withdrawal")
public class WithdrawalController {
	
	private WithdrawalService withdrawalService;

	@Resource
	public void setWithdrawalService(WithdrawalService withdrawalService) {
		this.withdrawalService = withdrawalService;
	}

	@RequestMapping(value="ether", method= RequestMethod.POST)
	public WithdrawalEtherRsp ether(WithdrawalEtherReq req) {
		return withdrawalService.ether(req);
	}

	@RequestMapping(value="applyBitCoin", method= RequestMethod.POST)
	public ApplyWithdrawalBitCoinRsp applyBitCoin(ApplyWithdrawalBitCoinReq req) {
		return withdrawalService.applyBitCoin(req);
	}

}
