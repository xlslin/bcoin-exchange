package com.sharingif.blockchain.ether.block.controller;


import com.sharingif.blockchain.ether.block.service.BlockChainService;
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

	@RequestMapping(value="syncData", method= RequestMethod.POST)
	public void syncData() {
		blockChainService.syncData();
	}

	@RequestMapping(value="validateBolck", method= RequestMethod.POST)
	public void validateBolck() {
		blockChainService.validateBolck();
	}

}
