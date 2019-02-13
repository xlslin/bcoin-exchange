package com.sharingif.blockchain.bitcoin.block.service.impl;


import javax.annotation.Resource;

import com.sharingif.blockchain.bitcoin.account.service.AddressListenerService;
import com.sharingif.blockchain.bitcoin.app.constants.CoinType;
import com.sharingif.blockchain.bitcoin.app.constants.Constants;
import com.sharingif.blockchain.bitcoin.app.constants.ScriptTypes;
import com.sharingif.blockchain.bitcoin.block.model.entity.BlockChain;
import com.sharingif.blockchain.bitcoin.block.model.entity.TransactionBusiness;
import com.sharingif.blockchain.bitcoin.block.model.entity.UtxoVin;
import com.sharingif.blockchain.bitcoin.block.service.BitCoinBlockService;
import com.sharingif.blockchain.bitcoin.block.service.TransactionBusinessService;
import com.sharingif.blockchain.bitcoin.deposit.service.DepositService;
import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.Withdrawal;
import com.sharingif.blockchain.bitcoin.withdrawal.service.WithdrawalService;
import com.sharingif.cube.core.util.StringUtils;
import org.bitcoincore.api.rawtransactions.entity.ScriptPubKey;
import org.bitcoincore.api.rawtransactions.entity.Vin;
import org.bitcoincore.api.rawtransactions.entity.Vout;
import org.springframework.stereotype.Service;

import com.sharingif.blockchain.bitcoin.block.model.entity.Transaction;
import com.sharingif.blockchain.bitcoin.block.dao.TransactionDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.bitcoin.block.service.TransactionService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@Service
public class TransactionServiceImpl extends BaseServiceImpl<Transaction, java.lang.String> implements TransactionService {
	
	private TransactionDAO transactionDAO;
	private BitCoinBlockService bitCoinBlockService;
	private AddressListenerService addressListenerService;
	private WithdrawalService withdrawalService;
	private DepositService depositService;
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
	public void setBitCoinBlockService(BitCoinBlockService bitCoinBlockService) {
		this.bitCoinBlockService = bitCoinBlockService;
	}
	@Resource
	public void setAddressListenerService(AddressListenerService addressListenerService) {
		this.addressListenerService = addressListenerService;
	}
	@Resource
	public void setWithdrawalService(WithdrawalService withdrawalService) {
		this.withdrawalService = withdrawalService;
	}
	@Resource
	public void setDepositService(DepositService depositService) {
		this.depositService = depositService;
	}
	@Resource
	public void setTransactionBusinessService(TransactionBusinessService transactionBusinessService) {
		this.transactionBusinessService = transactionBusinessService;
	}

	public void addUntreatedTransaction(Transaction transaction) {
		transaction.setTxStatus(BlockChain.STATUS_UNVERIFIED);
		add(transaction);
	}

	protected Boolean isDuplicationTransaction(BigInteger blockNumber, String blockHash, String txHash, Transaction transaction) {
		Transaction queryTransaction = new Transaction();
		queryTransaction.setBlockNumber(blockNumber);
		queryTransaction.setBlockHash(blockHash);
		queryTransaction.setTxHash(txHash);

		queryTransaction = transactionDAO.query(queryTransaction);

		if(queryTransaction == null) {
			return false;
		}

		transaction.setId(queryTransaction.getId());

		return true;
	}

	/**
	 * 处理vin中的vout
	 * @param vInList
	 * @return
	 */
	protected List<UtxoVin> handletUtxoVin(List<Vin> vInList) {
		List<UtxoVin> utxoVinList = new ArrayList<UtxoVin>(vInList.size());

		for(Vin vin : vInList) {

			String txId = vin.getTxId();

			// coinbase transaction
			if(StringUtils.isTrimEmpty(txId)) {
				continue;
			}

			org.bitcoincore.api.rawtransactions.entity.Transaction tx = bitCoinBlockService.getFullRawTransaction(txId);
			Vout vout = tx.getvOut().get(vin.getvOut());

			UtxoVin utxoVin = new UtxoVin();
			utxoVin.setVin(vin);
			utxoVin.setVout(vout);

			utxoVinList.add(utxoVin);
		}

		return utxoVinList;
	}

	/**
	 * 修改金额单位
	 * @param utxoVinList
	 * @param vOutList
	 */
	protected void updateValueUnit(List<UtxoVin> utxoVinList, List<Vout> vOutList) {
		for (UtxoVin utxoVin : utxoVinList) {
			utxoVin.getVout().setValue(utxoVin.getVout().getValue().multiply(Constants.BTC_UNIT));
		}
		for(Vout out : vOutList) {
			out.setValue(out.getValue().multiply(Constants.BTC_UNIT));
		}
	}

	/**
	 * 计算交易手续费
	 * @param utxoVinList
	 * @param vOutList
	 * @return
	 */
	protected BigInteger computeTxFee(List<UtxoVin> utxoVinList, List<Vout> vOutList) {
		BigInteger txFee = BigInteger.ZERO;

		BigDecimal inValue = BigDecimal.ZERO;
		for(UtxoVin utxoVin : utxoVinList) {
			inValue = inValue.add(utxoVin.getVout().getValue());
		}

		if(inValue.compareTo(BigDecimal.ZERO) == 0) {
			return txFee;
		}

		BigDecimal outValue = BigDecimal.ZERO;
		for(Vout vout : vOutList) {
			outValue = outValue.add(vout.getValue());
		}

		txFee = inValue.subtract(outValue).toBigInteger();

		return txFee;
	}

	protected boolean isWatch(Vout vout, Transaction transaction) {
		ScriptPubKey scriptPubKey = vout.getScriptPubKey();
		String scriptTypes = scriptPubKey.getType();
		List<String> addresses = scriptPubKey.getAddresses();

		if((!ScriptTypes.PUB_KEY_HASH.getName().equals(scriptTypes) && !ScriptTypes.SCRIPT_HASH.getName().equals(scriptTypes)) || addresses.size()>1) {
			logger.error("script types error, vout:{}, transaction:{}", vout, transaction);

			return false;
		}

		String address = addresses.get(0);

		return addressListenerService.isWatch(address);

	}

	private TransactionBusiness handleTransactionBusiness(Vout vout, Transaction transaction) {

		TransactionBusiness transactionBusiness = new TransactionBusiness();
		transactionBusiness.setBlockNumber(transaction.getBlockNumber());
		transactionBusiness.setBlockHash(transaction.getBlockHash());
		transactionBusiness.setTxHash(transaction.getTxHash());
		transactionBusiness.setTransactionId(transaction.getId());
		transactionBusiness.setCoinType(CoinType.BTC.name());
		transactionBusiness.setAmount(vout.getValue().toBigInteger());
		transactionBusiness.setFee(BigInteger.ZERO);
		transactionBusiness.setTxTime(transaction.getTxTime());

		return transactionBusiness;

	}

	@Transactional
	protected void withdrawal(TransactionBusiness transactionBusiness) {
		withdrawalService.addUntreated(transactionBusiness);
	}

	protected void withdrawal(int vioIndex, Vout vout, Transaction transaction) {
		TransactionBusiness transactionBusiness = handleTransactionBusiness(vout, transaction);
		transactionBusiness.setTxFrom(vout.getScriptPubKey().getAddresses().get(0));
		transactionBusiness.setVioIndex(new BigInteger(String.valueOf(vioIndex)));

		TransactionBusiness queryTransactionBusiness = transactionBusinessService.getTransactionBusiness(
				transactionBusiness.getBlockNumber()
				,transactionBusiness.getBlockHash()
				,transactionBusiness.getTxHash()
				,transactionBusiness.getVioIndex()
				,TransactionBusiness.TYPE_WITHDRAWAL
		);
		if(queryTransactionBusiness != null) {
			return;
		}

		withdrawal(transactionBusiness);
	}

	@Transactional
	protected void deposit(TransactionBusiness transactionBusiness) {
		depositService.addUntreated(transactionBusiness);
	}

	protected void deposit(int vioIndex, Vout vout, Transaction transaction) {
		TransactionBusiness transactionBusiness = handleTransactionBusiness(vout, transaction);
		transactionBusiness.setTxTo(vout.getScriptPubKey().getAddresses().get(0));
		transactionBusiness.setVioIndex(new BigInteger(String.valueOf(vioIndex)));

		TransactionBusiness queryTransactionBusiness = transactionBusinessService.getTransactionBusiness(
				transactionBusiness.getBlockNumber()
				,transactionBusiness.getBlockHash()
				,transactionBusiness.getTxHash()
				,transactionBusiness.getVioIndex()
				,TransactionBusiness.TYPE_DEPOSIT
		);
		if(queryTransactionBusiness != null) {
			return;
		}

		deposit(transactionBusiness);
	}

	@Override
	public void analysis(org.bitcoincore.api.rawtransactions.entity.Transaction tx, BigInteger blockNumber, String blockHash, Date blockCreateTime, BigInteger txIndex) {
		Transaction transaction = null;
		try {
			List<UtxoVin> utxoVinList = handletUtxoVin(tx.getvIn());
			List<Vout> vOutList = tx.getvOut();

			updateValueUnit(utxoVinList, vOutList);

			BigInteger txFee = computeTxFee(utxoVinList, vOutList);

			transaction = new Transaction();
			transaction.setBlockNumber(blockNumber);
			transaction.setBlockHash(blockHash);
			transaction.setTxHash(tx.getTxId());
			transaction.setTxIndex(txIndex);
			transaction.setTxFee(txFee);
			transaction.setTxTime(blockCreateTime);
			transaction.setConfirmBlockNumber(0);

			boolean isAddUntreatedTransaction = false;
			for(int i=0; i<utxoVinList.size(); i++) {
				UtxoVin utxoVin = utxoVinList.get(i);
				Vout vout = utxoVin.getVout();
				if(isWatch(vout, transaction)) {
					if(!isAddUntreatedTransaction) {
						isAddUntreatedTransaction = isDuplicationTransaction(transaction.getBlockNumber(), transaction.getBlockHash(), transaction.getTxHash(), transaction);
					}
					if(!isAddUntreatedTransaction) {
						addUntreatedTransaction(transaction);
						isAddUntreatedTransaction = true;
					}

					withdrawal(i, vout, transaction);
				}

			}
			for(int i=0; i<vOutList.size(); i++) {
				Vout vout = vOutList.get(i);
				if(isWatch(vout, transaction)) {
					if(!isAddUntreatedTransaction) {
						isAddUntreatedTransaction = isDuplicationTransaction(transaction.getBlockNumber(), transaction.getBlockHash(), transaction.getTxHash(), transaction);
					}
					if(!isAddUntreatedTransaction) {
						addUntreatedTransaction(transaction);
						isAddUntreatedTransaction = true;
					}

					deposit(i, vout, transaction);
				}
			}


		} catch (Exception e) {
			// 连接超时
			logger.error("analysis block transaction error", e);
			logger.error("analysis block transaction error, tx:{}", tx);
			analysis(tx, blockNumber, blockHash, blockCreateTime, txIndex);
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

	@Override
	public Transaction getVerifyValidStatusByTxHash(String txHash) {
		Transaction queryTransaction = new Transaction();
		queryTransaction.setTxHash(txHash);
		queryTransaction.setTxStatus(BlockChain.STATUS_VERIFY_VALID);

		return transactionDAO.query(queryTransaction);
	}
}
