package com.sharingif.blockchain.ether.block.controller;


import com.sharingif.blockchain.ether.block.service.BlockChainService;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
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
	
}
