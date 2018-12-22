package com.sharingif.blockchain.ether.block.service.impl;


import javax.annotation.Resource;

import com.sharingif.blockchain.ether.block.service.Erc20ContractService;
import org.springframework.stereotype.Service;

import com.sharingif.blockchain.ether.block.model.entity.Contract;
import com.sharingif.blockchain.ether.block.dao.ContractDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.ether.block.service.ContractService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

@Service
public class ContractServiceImpl extends BaseServiceImpl<Contract, java.lang.String> implements ContractService {
	
	private ContractDAO contractDAO;
	private Erc20ContractService erc20ContractService;

	public ContractDAO getContractDAO() {
		return contractDAO;
	}
	@Resource
	public void setContractDAO(ContractDAO contractDAO) {
		super.setBaseDAO(contractDAO);
		this.contractDAO = contractDAO;
	}
	@Resource
	public void setErc20ContractService(Erc20ContractService erc20ContractService) {
		this.erc20ContractService = erc20ContractService;
	}

	@Override
	public Contract getByIdForUpdate(String id) {
		return contractDAO.queryByIdForUpdate(id);
	}

	protected void addContract(Contract insertContract) {
		Contract queryContract = contractDAO.queryById(insertContract.getContractAddress());
		if(queryContract != null) {
			return;
		}
		contractDAO.insert(insertContract);
	}

	@Override
	public Contract getContractAndAdd(String contractAddress) {
		Contract queryContract = contractDAO.queryById(contractAddress);

		String name = null;
		String symbol = null;
		BigInteger decimals = null;
		BigInteger totalSupply = null;
		try {
			name = erc20ContractService.name(contractAddress);
		} catch (Exception e) {
			logger.error("get name error, contractAddress:{}", contractAddress);
		}
		try {
			symbol = erc20ContractService.symbol(contractAddress);
		} catch (Exception e) {
			logger.error("get symbol error, contractAddress:{}", contractAddress);
		}
		try {
			decimals = erc20ContractService.decimals(contractAddress);
		} catch (Exception e) {
			logger.error("get decimals error, contractAddress:{}", contractAddress);
		}
		try {
			totalSupply = erc20ContractService.totalSupply(contractAddress);
			if(totalSupply.toString().length()>65) {
				totalSupply = null;
			}
		} catch (Exception e) {
			logger.error("get totalSupply error, contractAddress:{}", contractAddress);
		}

		if(queryContract == null) {
			Contract insertContract = new Contract();
			insertContract.setContractAddress(contractAddress);
			insertContract.setName(name);
			insertContract.setSymbol(symbol);
			insertContract.setDecimals(decimals);
			insertContract.setTotalsupply(totalSupply);

			addContract(insertContract);
		}

		queryContract = contractDAO.queryById(contractAddress);

		return queryContract;
	}
}
