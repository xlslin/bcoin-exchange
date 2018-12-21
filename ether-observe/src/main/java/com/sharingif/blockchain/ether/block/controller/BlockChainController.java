package com.sharingif.blockchain.ether.block.controller;


import com.sharingif.blockchain.ether.block.model.entity.BlockChain;
import com.sharingif.blockchain.ether.block.service.BlockChainService;
import com.sharingif.cube.batch.core.request.JobRequest;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;


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

	@RequestMapping(value="syncDataJob", method= RequestMethod.POST)
	public List<BlockChain> syncDataJob() {
		return blockChainService.syncDataJob();
	}

	@RequestMapping(value="syncData", method= RequestMethod.POST)
	public void syncData(JobRequest jobRequest) {
		blockChainService.syncData(jobRequest);
	}

	@RequestMapping(value="validateBolck", method= RequestMethod.POST)
	public void validateBolck() {
		blockChainService.validateBolck();
	}

}
