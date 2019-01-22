package com.sharingif.blockchain.bitcoin.deposit.controller;


import com.sharingif.blockchain.ether.deposit.service.DepositService;
import com.sharingif.cube.batch.core.request.JobRequest;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;


@Controller
@RequestMapping(value="deposit")
public class DepositController {
	
	private DepositService depositService;

	public DepositService getDepositService() {
		return depositService;
	}
	@Resource
	public void setDepositService(DepositService depositService) {
		this.depositService = depositService;
	}

	@RequestMapping(value="readyInitNotice", method= RequestMethod.POST)
	public void readyInitNotice() {
		depositService.readyInitNotice();
	}

	@RequestMapping(value="initNotice", method= RequestMethod.POST)
	public void initNotice(JobRequest<String> jobRequest) {
		depositService.initNotice(jobRequest.getData());
	}

	@RequestMapping(value="readyFinishNotice", method= RequestMethod.POST)
	public void readyFinishNotice() {
		depositService.readyFinishNotice();
	}

	@RequestMapping(value="finishNotice", method= RequestMethod.POST)
	public void finishNotice(JobRequest<String> jobRequest) {
		depositService.finishNotice(jobRequest.getData());
	}
	
}
