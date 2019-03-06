package com.sharingif.blockchain.bitcoin.withdrawal.controller;


import com.sharingif.blockchain.bitcoin.api.withdrawal.entity.ApplyWithdrawalBitCoinReq;
import com.sharingif.blockchain.bitcoin.api.withdrawal.entity.ApplyWithdrawalBitCoinRsp;
import com.sharingif.blockchain.bitcoin.block.model.entity.TransactionBusiness;
import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.Withdrawal;
import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.WithdrawalTransaction;
import com.sharingif.blockchain.bitcoin.withdrawal.service.WithdrawalService;
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

	@RequestMapping(value="apply", method= RequestMethod.POST)
	public ApplyWithdrawalBitCoinRsp apply(ApplyWithdrawalBitCoinReq req) {
		return withdrawalService.apply(req);
	}

	@RequestMapping(value="btc", method= RequestMethod.POST)
	public void btc(JobRequest<List<Withdrawal>> jobRequest) {
		withdrawalService.btc(jobRequest.getData());
	}

	@RequestMapping(value="usdt", method= RequestMethod.POST)
	public void usdt(JobRequest<List<Withdrawal>> jobRequest) {
		withdrawalService.usdt(jobRequest.getData());
	}

	@RequestMapping(value="readyInitNotice", method= RequestMethod.POST)
	public void readyInitNotice(JobRequest<List<WithdrawalTransaction>> jobRequest) {
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
