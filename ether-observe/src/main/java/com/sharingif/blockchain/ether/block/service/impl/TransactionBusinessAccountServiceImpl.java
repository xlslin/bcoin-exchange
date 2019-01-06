package com.sharingif.blockchain.ether.block.service.impl;


import javax.annotation.Resource;

import com.sharingif.blockchain.ether.account.service.AccountService;
import com.sharingif.blockchain.ether.app.autoconfigure.constants.CoinType;
import com.sharingif.blockchain.ether.block.service.EthereumBlockService;
import com.sharingif.blockchain.ether.block.service.TransactionBusinessService;
import org.springframework.stereotype.Service;

import com.sharingif.blockchain.ether.block.model.entity.TransactionBusinessAccount;
import com.sharingif.blockchain.ether.block.dao.TransactionBusinessAccountDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.ether.block.service.TransactionBusinessAccountService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Service
public class TransactionBusinessAccountServiceImpl extends BaseServiceImpl<TransactionBusinessAccount, java.lang.String> implements TransactionBusinessAccountService {
	
	private TransactionBusinessAccountDAO transactionBusinessAccountDAO;
	private TransactionBusinessService transactionBusinessService;
	private EthereumBlockService ethereumBlockService;
	private AccountService accountService;

	public TransactionBusinessAccountDAO getTransactionBusinessAccountDAO() {
		return transactionBusinessAccountDAO;
	}
	@Resource
	public void setTransactionBusinessAccountDAO(TransactionBusinessAccountDAO transactionBusinessAccountDAO) {
		super.setBaseDAO(transactionBusinessAccountDAO);
		this.transactionBusinessAccountDAO = transactionBusinessAccountDAO;
	}
	@Resource
	public void setTransactionBusinessService(TransactionBusinessService transactionBusinessService) {
		this.transactionBusinessService = transactionBusinessService;
	}
	@Resource
	public void setEthereumBlockService(EthereumBlockService ethereumBlockService) {
		this.ethereumBlockService = ethereumBlockService;
	}
	@Resource
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	@Override
	public boolean addTransactionBusinessAccount(String address, String coinType, String contractAddress) {
		TransactionBusinessAccount transactionBusinessAccount = transactionBusinessAccountDAO.queryByAddressCoinTypeForUpdate(address, coinType);

		if(transactionBusinessAccount != null) {
			return false;
		}

		transactionBusinessAccount = new TransactionBusinessAccount();
		transactionBusinessAccount.setAddress(address);
		transactionBusinessAccount.setCoinType(coinType);
		transactionBusinessAccount.setContractAddress(contractAddress);

		return true;
	}

	@Override
	public void settleAccounts() {
		List<TransactionBusinessAccount> transactionBusinessAccountList = transactionBusinessAccountDAO.queryAll();
		if(transactionBusinessAccountList == null || transactionBusinessAccountList.isEmpty()) {
			return;
		}

		for(TransactionBusinessAccount transactionBusinessAccount : transactionBusinessAccountList) {
			BigInteger blockNumber = ethereumBlockService.getBlockNumber();

			BigInteger blockBalance = null;
			BigInteger accountBalance = null;

			if(CoinType.ETH.name().equals(transactionBusinessAccount.getCoinType())) {
				blockBalance = ethereumBlockService.getBalance(transactionBusinessAccount.getAddress());
			} else {
				blockBalance = ethereumBlockService.getErc20ContractService().balanceOf(transactionBusinessAccount.getAddress(), transactionBusinessAccount.getContractAddress());
			}

			accountBalance = accountService.getBalance(transactionBusinessAccount.getAddress(),transactionBusinessAccount.getCoinType());

			if(blockBalance.compareTo(accountBalance) != 0) {
				continue;
			}

			boolean deleteTransactionBusinessAccount = transactionBusinessService.getCountByAddressCoinTypeBlockNumber(
					transactionBusinessAccount.getAddress()
					,transactionBusinessAccount.getCoinType()
					,blockNumber
			) == 0;

			transactionBusinessService.updateStatusToFinishNoticing(transactionBusinessAccount.getAddress(), transactionBusinessAccount.getCoinType(), blockNumber);

			if(deleteTransactionBusinessAccount) {
				transactionBusinessAccountDAO.deleteById(transactionBusinessAccount.getId());
			}
		}

	}
}
