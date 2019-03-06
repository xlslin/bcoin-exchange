package com.sharingif.blockchain.bitcoin.deposit.controller;


import com.sharingif.blockchain.bitcoin.block.model.entity.TransactionBusiness;
import com.sharingif.blockchain.bitcoin.deposit.service.DepositService;
import com.sharingif.cube.batch.core.request.JobRequest;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;


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
	public void readyInitNotice(JobRequest<List<TransactionBusiness>> jobRequest) {
		depositService.readyInitNotice(jobRequest.getData());
	}

	@RequestMapping(value="initNotice", method= RequestMethod.POST)
	public void initNotice(JobRequest<String> jobRequest) {
		depositService.initNotice(jobRequest.getData());
	}

	@RequestMapping(value="readyFinishNotice", method= RequestMethod.POST)
	public void readyFinishNotice(JobRequest<List<TransactionBusiness>> jobRequest) {
		depositService.readyFinishNotice(jobRequest.getData());
	}

	@RequestMapping(value="finishNotice", method= RequestMethod.POST)
	public void finishNotice(JobRequest<String> jobRequest) {
		depositService.finishNotice(jobRequest.getData());
	}

}
