package com.sharingif.blockchain.ether.withdrawal.controller;


import com.sharingif.blockchain.ether.withdrawal.service.WithdrawalService;
import com.sharingif.cube.batch.core.request.JobRequest;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;
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

	@RequestMapping(value="initWithdrawalNotice", method= RequestMethod.POST)
	public void initDepositNotice(JobRequest<String> jobRequest) {
		withdrawalService.initWithdrawalNotice(jobRequest.getData());
	}

}
