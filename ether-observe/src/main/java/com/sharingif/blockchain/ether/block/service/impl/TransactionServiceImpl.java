package com.sharingif.blockchain.ether.block.service.impl;


import com.sharingif.blockchain.ether.account.service.AddressListenerService;
import com.sharingif.blockchain.ether.app.autoconfigure.constants.CoinType;
import com.sharingif.blockchain.ether.block.dao.TransactionDAO;
import com.sharingif.blockchain.ether.block.model.entity.BlockChain;
import com.sharingif.blockchain.ether.block.model.entity.Contract;
import com.sharingif.blockchain.ether.block.model.entity.Transaction;
import com.sharingif.blockchain.ether.block.model.entity.TransactionBusiness;
import com.sharingif.blockchain.ether.block.service.ContractService;
import com.sharingif.blockchain.ether.block.service.EthereumBlockService;
import com.sharingif.blockchain.ether.block.service.TransactionBusinessService;
import com.sharingif.blockchain.ether.block.service.TransactionService;
import com.sharingif.blockchain.ether.deposit.service.DepositService;
import com.sharingif.blockchain.ether.withdrawal.service.WithdrawalService;
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
	private AddressListenerService addressListenerService;
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
	public void setAddressListenerService(AddressListenerService addressListenerService) {
		this.addressListenerService = addressListenerService;
	}
	@Resource
	public void setTransactionBusinessService(TransactionBusinessService transactionBusinessService) {
		this.transactionBusinessService = transactionBusinessService;
	}

	public void addUntreatedTransaction(Transaction transaction) {
		transaction.setTxStatus(BlockChain.STATUS_UNVERIFIED);
		add(transaction);
	}


	protected Boolean isDuplicationData(BigInteger blockNumber, String blockHash, String txHash) {
		Transaction transaction = new Transaction();
		transaction.setBlockNumber(blockNumber);
		transaction.setBlockHash(blockHash);
		transaction.setTxHash(txHash);

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

		boolean isWatchFrom = addressListenerService.isWatch(transaction.getTxFrom());

		Boolean isContractAddress = ethereumBlockService.isContractAddress(transaction.getTxTo());
		if(isContractAddress) {
			handlerContractTransaction(transaction, isWatchFrom);
		}

		boolean isWatchTo = false;
		if(!StringUtils.isTrimEmpty(transaction.getTxTo())) {
			isWatchTo = addressListenerService.isWatch(transaction.getTxTo());
		}

		if(isWatchFrom == false && isWatchTo == false) {
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
			// tx to 为空，交易类型为创建合约
			if(StringUtils.isTrimEmpty(tx.getTo())) {
				logger.info("tx to is null, txhash:{}", tx.getHash());
				return;
			}

			// 数据是否重复，如果重复不处理
			if(isDuplicationData(
					tx.getBlockNumber()
					,tx.getBlockHash()
					,tx.getHash()
			)) {
				return;
			}

			transaction = new Transaction();
			transaction.setBlockHash(tx.getBlockHash());
			transaction.setTxHash(tx.getHash());
			transaction.setBlockNumber(tx.getBlockNumber());
			transaction.setTxFrom(tx.getFrom());
			transaction.setTxTo(tx.getTo());
			transaction.setCoinType(CoinType.ETH.name());
			transaction.setTxInput(tx.getInput());
			transaction.setTxValue(tx.getValue());
			transaction.setTxIndex(tx.getTransactionIndex());
			transaction.setGasLimit(tx.getGas());
			transaction.setGasUsed(transactionReceipt.getGasUsed());
			transaction.setGasPrice(tx.getGasPrice());
			transaction.setActualFee(transaction.getGasUsed().multiply(transaction.getGasPrice()));
			transaction.setNonce(tx.getNonce());
			transaction.setTxReceiptStatus(Transaction.convertTxReceiptStatus(transactionReceipt.getStatus()));
			transaction.setTxTime(blockCreateTime);
			transaction.setConfirmBlockNumber(0);

			analysis(transaction);

		} catch (Exception e) {
			// geth连接超时
			logger.error("analysis block transaction error, transaction:{}", transaction, e);
			analysis(tx, transactionReceipt, blockCreateTime);
		}

	}

	@Override
	public void updateTxStatusToBlockConfirmedValid(BigInteger blockNumber, String blockHash, int confirmBlockNumber) {
		Transaction updateTransaction = new Transaction();
		updateTransaction.setBlockNumber(blockNumber);
		updateTransaction.setBlockHash(blockHash);

		updateTransaction.setConfirmBlockNumber(confirmBlockNumber);
		updateTransaction.setTxStatus(BlockChain.STATUS_VERIFY_VALID);

		transactionDAO.updateByBlockNumberBlockHash(updateTransaction);

		transactionBusinessService.updateTxStatusToValidSettleStatusToReady(blockNumber, blockHash);
	}

	@Override
	public void updateTxStatusToBlockConfirmedInvalid(BigInteger blockNumber, String blockHash, int confirmBlockNumber) {
		Transaction updateTransaction = new Transaction();
		updateTransaction.setBlockNumber(blockNumber);
		updateTransaction.setBlockHash(blockHash);

		updateTransaction.setConfirmBlockNumber(confirmBlockNumber);
		updateTransaction.setTxStatus(BlockChain.STATUS_VERIFY_INVALID);

		transactionDAO.updateByBlockNumberBlockHash(updateTransaction);

		transactionBusinessService.updateTxStatusToInvalidSettleStatusToReady(blockNumber, blockHash);
	}

}
