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

	@RequestMapping(value="readyValidateBolck", method= RequestMethod.POST)
	public void readyValidateBolck() {
		blockChainService.readyValidateBolck();
	}

	@RequestMapping(value="validateBolck", method= RequestMethod.POST)
	public void validateBolck(JobRequest<String> jobRequest) {
		blockChainService.validateBolck(jobRequest.getData());
	}

}
