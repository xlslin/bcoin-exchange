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

	@RequestMapping(value="readyInitNotice", method= RequestMethod.POST)
	public void readyInitNotice() {
		withdrawalService.readyInitNotice();
	}

	@RequestMapping(value="initNotice", method= RequestMethod.POST)
	public void initNotice(JobRequest<String> jobRequest) {
		withdrawalService.initNotice(jobRequest.getData());
	}

	@RequestMapping(value="finishNotice", method= RequestMethod.POST)
	public void finishNotice() {
		withdrawalService.finishNotice();
	}

	@RequestMapping(value="ether", method= RequestMethod.POST)
	public WithdrawalEtherRsp ether(WithdrawalEtherReq req) {
		return withdrawalService.ether(req);
	}

	@RequestMapping(value="withdrawalEther", method= RequestMethod.POST)
	public void withdrawalEther() {
		withdrawalService.withdrawalEther();
	}

	@RequestMapping(value="readySuccessNotice", method= RequestMethod.POST)
	public void readySuccessNotice() {
		withdrawalService.readyWithdrawalSuccessNotice();
	}

	@RequestMapping(value="successNotice", method= RequestMethod.POST)
	public void successNotice(JobRequest<String> jobRequest) {
		withdrawalService.withdrawalSuccessNotice(jobRequest.getData());
	}

	@RequestMapping(value="readyFailureNotice", method= RequestMethod.POST)
	public void readyFailureNotice() {
		withdrawalService.readyWithdrawalFailureNotice();
	}

	@RequestMapping(value="failureNotice", method= RequestMethod.POST)
	public void failureNotice(JobRequest<String> jobRequest) {
		withdrawalService.withdrawalFailureNotice(jobRequest.getData());
	}

}
