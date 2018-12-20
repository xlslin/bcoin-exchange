package com.sharingif.blockchain.ether.block.controller;


import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sharingif.blockchain.ether.block.service.ContractService;


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
