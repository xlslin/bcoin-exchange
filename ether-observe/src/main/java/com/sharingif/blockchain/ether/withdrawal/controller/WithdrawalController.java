package com.sharingif.blockchain.ether.withdrawal.controller;


import com.sharingif.blockchain.ether.api.withdrawal.entity.WithdrawalEtherReq;
import com.sharingif.blockchain.ether.api.withdrawal.entity.WithdrawalEtherRsp;
import com.sharingif.blockchain.ether.block.model.entity.TransactionBusiness;
import com.sharingif.blockchain.ether.withdrawal.model.entity.Withdrawal;
import com.sharingif.blockchain.ether.withdrawal.service.WithdrawalService;
import com.sharingif.cube.batch.core.request.JobRequest;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;


@Controller
@RequestMapping(value="withdrawal")
public class WithdrawalController {
	
	private WithdrawalService withdrawalService;

	@Resource
	public void setWithdrawalService(WithdrawalService withdrawalService) {
		this.withdrawalService = withdrawalService;
	}

	@RequestMapping(value="readyInitNotice", method= RequestMethod.POST)
	public void readyInitNotice(JobRequest<List<Withdrawal>> jobRequest) {
		withdrawalService.readyInitNotice(jobRequest.getData());
	}

	@RequestMapping(value="initNotice", method= RequestMethod.POST)
	public void initNotice(JobRequest<String> jobRequest) {
		withdrawalService.initNotice(jobRequest.getData());
	}

	@RequestMapping(value="finishNotice", method= RequestMethod.POST)
	public void finishNotice(JobRequest<List<TransactionBusiness>> jobRequest) {
		withdrawalService.finishNotice(jobRequest.getData());
	}

	@RequestMapping(value="ether", method= RequestMethod.POST)
	public WithdrawalEtherRsp ether(WithdrawalEtherReq req) {
		return withdrawalService.ether(req);
	}

	@RequestMapping(value="withdrawalEther", method= RequestMethod.POST)
	public void withdrawalEther(JobRequest<List<Withdrawal>> jobRequest) {
		withdrawalService.withdrawalEther(jobRequest.getData());
	}

	@RequestMapping(value="readySuccessNotice", method= RequestMethod.POST)
	public void readySuccessNotice(JobRequest<List<Withdrawal>> jobRequest) {
		withdrawalService.readyWithdrawalSuccessNotice(jobRequest.getData());
	}

	@RequestMapping(value="successNotice", method= RequestMethod.POST)
	public void successNotice(JobRequest<String> jobRequest) {
		withdrawalService.withdrawalSuccessNotice(jobRequest.getData());
	}

	@RequestMapping(value="readyFailureNotice", method= RequestMethod.POST)
	public void readyFailureNotice(JobRequest<List<Withdrawal>> jobRequest) {
		withdrawalService.readyWithdrawalFailureNotice(jobRequest.getData());
	}

	@RequestMapping(value="failureNotice", method= RequestMethod.POST)
	public void failureNotice(JobRequest<String> jobRequest) {
		withdrawalService.withdrawalFailureNotice(jobRequest.getData());
	}

}
