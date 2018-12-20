package com.sharingif.blockchain.ether.block.controller;


import com.sharingif.blockchain.ether.block.service.ContractService;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;


@Controller
@RequestMapping(value="contract")
public class ContractController {
	
	private ContractService contractService;

	public ContractService getContractService() {
		return contractService;
	}
	@Resource
	public void setContractService(ContractService contractService) {
		this.contractService = contractService;
	}
	
}
