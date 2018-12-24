package com.sharingif.blockchain.ether.deposit.controller;


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

	@RequestMapping(value="readyDepositNotice", method= RequestMethod.POST)
	public void readyDepositNotice() {
		depositService.readyDepositNotice();
	}

	@RequestMapping(value="initDepositNotice", method= RequestMethod.POST)
	public void initDepositNotice(JobRequest<String> jobRequest) {
		depositService.initDepositNotice(jobRequest.getData());
	}
	
}
