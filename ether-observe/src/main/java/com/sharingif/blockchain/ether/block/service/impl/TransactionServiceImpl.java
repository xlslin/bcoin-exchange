package com.sharingif.blockchain.ether.block.service.impl;


import com.sharingif.blockchain.api.account.entity.AddressListenerIsWatchReq;
import com.sharingif.blockchain.api.account.entity.AddressListenerIsWatchRsp;
import com.sharingif.blockchain.api.account.service.AddressListenerApiService;
import com.sharingif.blockchain.ether.app.autoconfigure.constants.CoinType;
import com.sharingif.blockchain.ether.block.dao.TransactionDAO;
import com.sharingif.blockchain.ether.block.model.entity.BlockChain;
import com.sharingif.blockchain.ether.block.model.entity.Contract;
import com.sharingif.blockchain.ether.block.model.entity.Transaction;
import com.sharingif.blockchain.ether.block.model.entity.TransactionBusiness;
import com.sharingif.blockchain.ether.block.service.*;
import com.sharingif.blockchain.ether.deposit.service.DepositService;
import com.sharingif.blockchain.ether.withdrawal.service.WithdrawalService;
import com.sharingif.cube.core.exception.UnknownCubeException;
import com.sharingif.cube.core.util.StringUtils;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Service
public class TransactionServiceImpl extends BaseServiceImpl<Transaction, java.lang.String> implements TransactionService {
	
	private TransactionDAO transactionDAO;
	private DepositService depositService;
	private WithdrawalService withdrawalService;
	private EthereumBlockService ethereumBlockService;
	private ContractService contractService;
	private AddressListenerApiService addressListenerApiService;
	private TransactionBusinessService transactionBusinessService;

	public TransactionDAO getTransactionDAO() {
		return transactionDAO;
	}
	@Resource
	public void setTransactionDAO(TransactionDAO transactionDAO) {
		super.setBaseDAO(transactionDAO);
		this.transactionDAO = transactionDAO;
	}
	@Resource
	public void setDepositService(DepositService depositService) {
		this.depositService = depositService;
	}
	@Resource
	public void setWithdrawalService(WithdrawalService withdrawalService) {
		this.withdrawalService = withdrawalService;
	}
	@Resource
	public void setEthereumBlockService(EthereumBlockService ethereumBlockService) {
		this.ethereumBlockService = ethereumBlockService;
	}
	@Resource
	public void setContractService(ContractService contractService) {
		this.contractService = contractService;
	}
	@Resource
	public void setAddressListenerApiService(AddressListenerApiService addressListenerApiService) {
		this.addressListenerApiService = addressListenerApiService;
	}
	@Resource
	public void setTransactionBusinessService(TransactionBusinessService transactionBusinessService) {
		this.transactionBusinessService = transactionBusinessService;
	}

	public void addUntreatedTransaction(Transaction transaction) {
		transaction.setTxStatus(BlockChain.STATUS_UNVERIFIED);
		add(transaction);
	}

	protected boolean isWatch(String address) {
		AddressListenerIsWatchReq req = new AddressListenerIsWatchReq();
		req.setBlockType("Ether");
		req.setAddress(address);

		AddressListenerIsWatchRsp rsp = addressListenerApiService.isWatch(req);

		return rsp.isWatch();
	}


	protected Boolean isDuplicationData(String blockHash, BigInteger txIndex, String txHash, BigInteger blockNumber, String from, String to) {
		Transaction transaction = new Transaction();
		transaction.setBlockHash(blockHash);
		transaction.setTxIndex(txIndex);
		transaction.setTxHash(txHash);
		transaction.setBlockNumber(blockNumber);
		transaction.setTxFrom(from);
		transaction.setTxTo(to);

		transaction = transactionDAO.query(transaction);

		if(transaction == null) {
			return false;
		}

		return true;
	}

	@Transactional
	protected void persistenceTransaction(Transaction transaction, boolean isWatchFrom, boolean isWatchTo) {
		addUntreatedTransaction(transaction);

		TransactionBusiness transactionBusiness = new TransactionBusiness();
		transactionBusiness.setBlockNumber(transaction.getBlockNumber());
		transactionBusiness.setBlockHash(transaction.getBlockHash());
		transactionBusiness.setTransactionId(transaction.getId());
		transactionBusiness.setTxHash(transaction.getTxHash());
		transactionBusiness.setCoinType(transaction.getCoinType());
		transactionBusiness.setTxFrom(transaction.getTxFrom());
		transactionBusiness.setTxTo(transaction.getTxTo());
		transactionBusiness.setAmount(transaction.getTxValue());
		transactionBusiness.setFee(transaction.getActualFee());
		transactionBusiness.setTxReceiptStatus(transaction.getTxReceiptStatus());
		transactionBusiness.setTxTime(transaction.getTxTime());

		if(isWatchFrom) {
			withdrawalService.addUntreated(transactionBusiness);
		}

		if(isWatchTo) {
			transactionBusiness.setId(null);
			depositService.addUntreated(transactionBusiness);
		}

	}

	protected void handlerContractTransaction(Transaction transaction, boolean isWatchFrom) {
		transaction.setContractAddress(transaction.getTxTo());
		transaction.setTxTo(null);

		List<Type> transferResponseList = null;
		try {
			transferResponseList = ethereumBlockService.getErc20ContractService().getTransfer(transaction.getTxInput());
		} catch (Exception e) {
			logger.error("get transfer error, transaction:{}", transaction, e);
		}
		if(transferResponseList == null || transferResponseList.isEmpty()) {
			logger.info("transfer response list is null, transaction:{}", transaction);
			// 交易失败但是是观察地址需要处理手续费
			if(isWatchFrom == false) {
				transaction.setTxValue(BigInteger.ZERO);
				return;
			}
		} else {
			Address address = (Address)transferResponseList.get(0);
			Uint256 amount = (Uint256)transferResponseList.get(1);

			transaction.setTxTo(address.getValue());
			transaction.setTxValue(amount.getValue());
		}

	}

	protected void analysis(Transaction transaction) {

		boolean isWatchFrom = isWatch(transaction.getTxFrom());

		Boolean isContractAddress = ethereumBlockService.isContractAddress(transaction.getTxTo());
		if(isContractAddress) {
			handlerContractTransaction(transaction, isWatchFrom);
		}

		boolean isWatchTo = false;
		if(StringUtils.isTrimEmpty(transaction.getTxTo())) {
			isWatchTo = isWatch(transaction.getTxTo());
		}

		if(isWatchFrom == false && isWatchTo == false) {
			return;
		}

		if(isDuplicationData(
				transaction.getBlockHash()
				,transaction.getTxIndex()
				,transaction.getTxHash()
				,transaction.getBlockNumber()
				,transaction.getTxFrom(),
				transaction.getTxTo())
		) {
			return;
		}

		if(!StringUtils.isTrimEmpty(transaction.getContractAddress())) {
			Contract contract = contractService.getContractAndAdd(transaction.getContractAddress());
			if(!contract.isErc20Contract()) {
				logger.error("is not erc20 contract, transaction:{}",transaction);
				return;
			}
			transaction.setCoinType(contract.getSymbol());
		}

		persistenceTransaction(transaction, isWatchFrom, isWatchTo);


	}

	@Override
	public void analysis(org.web3j.protocol.core.methods.response.Transaction tx, TransactionReceipt transactionReceipt, Date blockCreateTime) {


		Transaction transaction = null;
		try {
			if(StringUtils.isTrimEmpty(tx.getTo())) {
				logger.info("tx to is null, txhash:{}", tx.getHash());
				return;
			}

			transaction = new Transaction();

			transaction.setTxHash(tx.getHash());
			transaction.setBlockNumber(tx.getBlockNumber());
			transaction.setTxFrom(tx.getFrom());
			transaction.setTxTo(tx.getTo());
			transaction.setTxInput(tx.getInput());
			transaction.setTxIndex(tx.getTransactionIndex());
			transaction.setGasLimit(tx.getGas());
			transaction.setGasPrice(tx.getGasPrice());
			transaction.setNonce(tx.getNonce());
			transaction.setTxValue(tx.getValue());
			transaction.setCoinType(CoinType.ETH.name());
			transaction.setGasUsed(transactionReceipt.getGasUsed());
			transaction.setTxReceiptStatus(Transaction.convertTxReceiptStatus(transactionReceipt.getStatus()));
			transaction.setTxTime(blockCreateTime);
			transaction.setConfirmBlockNumber(0);
			transaction.setActualFee(transaction.getGasUsed().multiply(transaction.getGasPrice()));
			transaction.setBlockHash(tx.getBlockHash());

			analysis(transaction);

		} catch (Exception e) {
			// geth连接超时
			logger.error("analysis block transaction error, transaction:{}", transaction, e);
			analysis(tx, transactionReceipt, blockCreateTime);
		}

	}

	@Transactional
	protected void updateTxStatusToBlockConfirmedValid(Transaction transaction, int confirmBlockNumber) {
		transactionBusinessService.updateTxStatusToBlockConfirmedValid(transaction.getId());

		Transaction updateTransaction = new Transaction();
		updateTransaction.setId(transaction.getId());
		updateTransaction.setConfirmBlockNumber(confirmBlockNumber);
		updateTransaction.setTxStatus(BlockChain.STATUS_VERIFY_VALID);

		transactionDAO.updateById(updateTransaction);
	}

	@Override
	public void updateTxStatusToBlockConfirmedValid(BigInteger blockNumber, String blockHash, int confirmBlockNumber) {
		Transaction queryTransaction = new Transaction();
		queryTransaction.setBlockNumber(blockNumber);
		queryTransaction.setBlockHash(blockHash);
		queryTransaction.setTxStatus(BlockChain.STATUS_UNVERIFIED);

		List<Transaction> transactionList = transactionDAO.queryList(queryTransaction);

		if(transactionList == null || transactionList.isEmpty()) {
			return;
		}

		for(Transaction transaction : transactionList) {
			updateTxStatusToBlockConfirmedValid(transaction, confirmBlockNumber);
		}

	}

	@Transactional
	protected void updateTxStatusToBlockConfirmedInvalid(Transaction transaction, int confirmBlockNumber) {
		transactionBusinessService.updateTxStatusToBlockConfirmedInvalid(transaction.getId());

		Transaction updateTransaction = new Transaction();
		updateTransaction.setId(transaction.getId());
		updateTransaction.setConfirmBlockNumber(confirmBlockNumber);
		updateTransaction.setTxStatus(BlockChain.STATUS_VERIFY_INVALID);

		transactionDAO.updateById(updateTransaction);
	}

	@Override
	public void updateTxStatusToBlockConfirmedInvalid(BigInteger blockNumber, String blockHash, int confirmBlockNumber) {
		Transaction queryTransaction = new Transaction();
		queryTransaction.setBlockNumber(blockNumber);
		queryTransaction.setBlockHash(blockHash);
		queryTransaction.setTxStatus(BlockChain.STATUS_UNVERIFIED);

		List<Transaction> transactionList = transactionDAO.queryList(queryTransaction);

		if(transactionList == null || transactionList.isEmpty()) {
			return;
		}

		for(Transaction transaction : transactionList) {
			updateTxStatusToBlockConfirmedInvalid(transaction, confirmBlockNumber);
		}
	}

}
