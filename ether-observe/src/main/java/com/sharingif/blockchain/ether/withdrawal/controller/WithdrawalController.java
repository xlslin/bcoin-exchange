package com.sharingif.blockchain.ether.withdrawal.controller;


import com.sharingif.blockchain.ether.api.withdrawal.entity.WithdrawalEtherReq;
import com.sharingif.blockchain.ether.api.withdrawal.entity.WithdrawalEtherRsp;
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

	@RequestMapping(value="readyFinishNotice", method= RequestMethod.POST)
	public void readyFinishNotice() {
		withdrawalService.readyFinishNotice();
	}

	@RequestMapping(value="finishNotice", method= RequestMethod.POST)
	public void finishNotice(JobRequest<String> jobRequest) {
		withdrawalService.finishNotice(jobRequest.getData());
	}

	@RequestMapping(value="ether", method= RequestMethod.POST)
	public WithdrawalEtherRsp ether(WithdrawalEtherReq req) {
		return withdrawalService.ether(req);
	}

}
