package com.sharingif.blockchain.ether.block.controller;


import com.sharingif.blockchain.ether.block.service.BlockChainService;
import com.sharingif.cube.batch.core.request.JobRequest;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;


@Controller
@RequestMapping(value="blockChain")
public class BlockChainController {
	
	private BlockChainService blockChainService;

	public BlockChainService getBlockChainService() {
		return blockChainService;
	}
	@Resource
	public void setBlockChainService(BlockChainService blockChainService) {
		this.blockChainService = blockChainService;
	}

	@RequestMapping(value="readySyncData", method= RequestMethod.POST)
	public void readySyncData() {
		blockChainService.readySyncData();
	}

	@RequestMapping(value="synchingData", method= RequestMethod.POST)
	public void synchingData(JobRequest<String> jobRequest) {
		blockChainService.synchingData(jobRequest.getData());
	}

	@RequestMapping(value="validateBolck", method= RequestMethod.POST)
	public void validateBolck() {
		blockChainService.validateBolck();
	}

	@RequestMapping(value="readySettleBolckSuccess", method= RequestMethod.POST)
	public void readySettleBolckSuccess() {
		blockChainService.readySettleBolckSuccess();
	}

	@RequestMapping(value="readySettleBolckFailure", method= RequestMethod.POST)
	public void readySettleBolckFailure() {
		blockChainService.readySettleBolckFailure();
	}

	@RequestMapping(value="settleBolckSuccess", method= RequestMethod.POST)
	public void settleBolckSuccess(JobRequest<String> jobRequest) {
		blockChainService.settleBolckSuccess(jobRequest.getData());
	}

	@RequestMapping(value="settleBolckFailure", method= RequestMethod.POST)
	public void settleBolckFailure(JobRequest<String> jobRequest) {
		blockChainService.settleBolckFailure(jobRequest.getData());
	}

}
