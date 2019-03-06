package com.sharingif.blockchain.bitcoin.block.controller;


import com.sharingif.blockchain.bitcoin.block.service.BlockChainSyncService;
import com.sharingif.cube.batch.core.request.JobRequest;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.math.BigInteger;


@Controller
@RequestMapping(value="blockChainSync")
public class BlockChainSyncController {
	
	private BlockChainSyncService blockChainSyncService;

	public BlockChainSyncService getBlockChainSyncService() {
		return blockChainSyncService;
	}
	@Resource
	public void setBlockChainSyncService(BlockChainSyncService blockChainSyncService) {
		this.blockChainSyncService = blockChainSyncService;
	}

	@RequestMapping(value="add", method= RequestMethod.POST)
	public void add(JobRequest<BigInteger> jobRequest) {
		blockChainSyncService.add(jobRequest.getData());
	}

	@RequestMapping(value="update", method= RequestMethod.POST)
	public void update(JobRequest<BigInteger> jobRequest) {
		blockChainSyncService.update(jobRequest.getData());
	}

}
