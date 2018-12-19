package com.sharingif.blockchain.ether.block.controller;


import com.sharingif.blockchain.ether.block.service.BlockChainSyncService;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;


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

	@RequestMapping(value="sync", method= RequestMethod.POST)
	public void sync() {
		blockChainSyncService.sync();
	}
	
}
