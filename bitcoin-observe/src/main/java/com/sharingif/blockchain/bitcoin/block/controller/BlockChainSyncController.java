package com.sharingif.blockchain.bitcoin.block.controller;


import com.sharingif.blockchain.bitcoin.block.service.BlockChainSyncService;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
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
	
}
