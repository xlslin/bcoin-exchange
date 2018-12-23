package com.sharingif.blockchain.ether.block.service.impl;


import com.sharingif.blockchain.api.account.entity.AddressListenerIsWatchReq;
import com.sharingif.blockchain.api.account.entity.AddressListenerIsWatchRsp;
import com.sharingif.blockchain.api.account.service.AddressListenerApiService;
import com.sharingif.blockchain.ether.app.autoconfigure.constants.CoinType;
import com.sharingif.blockchain.ether.block.dao.TransactionDAO;
import com.sharingif.blockchain.ether.block.model.entity.Contract;
import com.sharingif.blockchain.ether.block.model.entity.Transaction;
import com.sharingif.blockchain.ether.block.service.BlockChainService;
import com.sharingif.blockchain.ether.block.service.ContractService;
import com.sharingif.blockchain.ether.block.service.EthereumBlockService;
import com.sharingif.blockchain.ether.block.service.TransactionService;
import com.sharingif.blockchain.ether.deposit.model.entity.Deposit;
import com.sharingif.blockchain.ether.deposit.service.DepositService;
import com.sharingif.blockchain.ether.withdrawal.model.entity.Withdrawal;
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
	private AddressListenerApiService addressListenerApiService;
	private BlockChainService blockChainService;

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
	public DepositService getDepositService() {
		return depositService;
	}
	@Resource
	public void setWithdrawalService(WithdrawalService withdrawalService) {
		this.withdrawalService = withdrawalService;
	}
	public WithdrawalService getWithdrawalService() {
		return withdrawalService;
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
	public void setBlockChainService(BlockChainService blockChainService) {
		this.blockChainService = blockChainService;
	}

	public void addUntreatedTransaction(Transaction transaction) {
		transaction.setTxStatus(Transaction.TX_STATUS_UNTREATED);
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

		if(isWatchFrom) {
			Withdrawal withdrawal = new Withdrawal();
			withdrawal.setBlockNumber(transaction.getBlockNumber());
			withdrawal.setTransactionId(transaction.getId());
			withdrawal.setTxHash(transaction.getTxHash());
			withdrawal.setCoinType(transaction.getCoinType());
			withdrawal.setTxFrom(transaction.getTxFrom());
			withdrawal.setTxTo(transaction.getTxTo());
			withdrawal.setAmount(transaction.getTxValue());
			withdrawal.setFee(transaction.getActualFee());
			withdrawalService.addUntreated(withdrawal);
		}

		if(isWatchTo) {
			Deposit deposit = new Deposit();
			deposit.setBlockNumber(transaction.getBlockNumber());
			deposit.setTransactionId(transaction.getId());
			deposit.setTxHash(transaction.getTxHash());
			deposit.setCoinType(transaction.getCoinType());
			deposit.setTxFrom(transaction.getTxFrom());
			deposit.setTxTo(transaction.getTxTo());
			deposit.setAmount(transaction.getTxValue());
			deposit.setFee(transaction.getActualFee());
			depositService.addUntreated(deposit);
		}
	}

	protected void persistenceTransaction(Transaction transaction) {
		boolean isWatchFrom = isWatch(transaction.getTxFrom());

		boolean isWatchTo = isWatch(transaction.getTxTo());

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
		persistenceTransaction(transaction, isWatchFrom, isWatchTo);
	}

	protected void handlerContractTransaction(Transaction transaction) {
		transaction.setContractAddress(transaction.getTxTo());
		transaction.setTxTo(null);

		Contract contract = contractService.getContractAndAdd(transaction.getContractAddress());
		if(!contract.isErc20Contract()) {
			logger.error("is not erc20 contract, transaction:{}",transaction);
			return;
		}

		List<Type> transferResponseList = ethereumBlockService.getErc20ContractService().getTransfer(transaction.getTxInput());
		if(transferResponseList == null || transferResponseList.isEmpty()) {
			logger.error("transfer response list is null, transaction:{}", transaction);
			return;
		}

		Address address = (Address)transferResponseList.get(0);
		Uint256 amount = (Uint256)transferResponseList.get(1);

		transaction.setTxTo(address.getValue());
		transaction.setTxValue(amount.getValue());

		transaction.setCoinType(contract.getSymbol());

	}

	protected void analysis(Transaction transaction) {

		Boolean isContractAddress = ethereumBlockService.isContractAddress(transaction.getTxTo());
		if(isContractAddress) {
			handlerContractTransaction(transaction);
		}

		persistenceTransaction(transaction);


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

	@Override
	public int updateStatusToBlockConfirmedValid(BigInteger blockNumber, String blockHash, int confirmBlockNumber) {
		Transaction transaction = new Transaction();
		transaction.setBlockNumber(blockNumber);
		transaction.setBlockHash(blockHash);

		transaction.setConfirmBlockNumber(confirmBlockNumber);
		transaction.setTxStatus(Transaction.TX_STATUS_BLOCK_CONFIRMED_VALID);

		return transactionDAO.updateByBlockNumberBlockHash(transaction);
	}

	@Override
	public int updateStatusToBlockConfirmedInvalid(BigInteger blockNumber, String blockHash, int confirmBlockNumber) {
		Transaction transaction = new Transaction();
		transaction.setBlockNumber(blockNumber);
		transaction.setBlockHash(blockHash);

		transaction.setConfirmBlockNumber(confirmBlockNumber);
		transaction.setTxStatus(Transaction.TX_STATUS_BLOCK_CONFIRMED_INVALID);

		return transactionDAO.updateByBlockNumberBlockHash(transaction);
	}

}
