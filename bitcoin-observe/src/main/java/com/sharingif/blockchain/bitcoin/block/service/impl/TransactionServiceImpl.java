package com.sharingif.blockchain.bitcoin.block.service.impl;


import javax.annotation.Resource;

import com.sharingif.blockchain.bitcoin.account.service.AddressListenerService;
import com.sharingif.blockchain.bitcoin.app.constants.CoinType;
import com.sharingif.blockchain.bitcoin.app.constants.Constants;
import com.sharingif.blockchain.bitcoin.app.constants.ScriptTypes;
import com.sharingif.blockchain.bitcoin.block.model.entity.*;
import com.sharingif.blockchain.bitcoin.block.service.BitCoinBlockService;
import com.sharingif.blockchain.bitcoin.block.service.OmniBlockService;
import com.sharingif.blockchain.bitcoin.block.service.TransactionBusinessService;
import com.sharingif.blockchain.bitcoin.deposit.service.DepositService;
import com.sharingif.blockchain.bitcoin.withdrawal.service.WithdrawalService;
import com.sharingif.cube.core.util.StringUtils;
import org.bitcoincore.api.rawtransactions.entity.ScriptPubKey;
import org.bitcoincore.api.rawtransactions.entity.Vin;
import org.bitcoincore.api.rawtransactions.entity.Vout;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sharingif.blockchain.bitcoin.block.dao.TransactionDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.bitcoin.block.service.TransactionService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@Service
public class TransactionServiceImpl extends BaseServiceImpl<Transaction, java.lang.String> implements TransactionService {

	private String omniUsdt;

	private TransactionDAO transactionDAO;
	private BitCoinBlockService bitCoinBlockService;
	private OmniBlockService omniBlockService;
	private AddressListenerService addressListenerService;
	private WithdrawalService withdrawalService;
	private DepositService depositService;
	private TransactionBusinessService transactionBusinessService;


	@Value("${omni.usdt}")
	public void setOmniUsdt(String omniUsdt) {
		this.omniUsdt = omniUsdt;
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
	public void setOmniBlockService(OmniBlockService omniBlockService) {
		this.omniBlockService = omniBlockService;
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

	protected boolean isWatch(Vout vout, Transaction transaction, OmniNullData omniNullData, int voutIndex) {
		ScriptPubKey scriptPubKey = vout.getScriptPubKey();
		String scriptTypes = scriptPubKey.getType();
		List<String> addresses = scriptPubKey.getAddresses();

		if((!ScriptTypes.PUB_KEY_HASH.getName().equals(scriptTypes) && !ScriptTypes.SCRIPT_HASH.getName().equals(scriptTypes)) || addresses.size()>1) {
			logger.error("script types error, vout:{}, transaction:{}", vout, transaction);

			if(omniNullData != null && ScriptTypes.NULL_DATA.getName().equals(scriptTypes)) {
				omniNullData.setVoutIndex(voutIndex);
				omniNullData.setNullData(scriptPubKey.getAsm());
			}

			return false;
		}

		String address = addresses.get(0);

		return addressListenerService.isWatch(address);

	}

	protected TransactionBusiness handleTransactionBusiness(BigInteger vioIndex, String txFrom, String txTo, BigInteger amount, Transaction transaction, String coinType) {
		TransactionBusiness transactionBusiness = new TransactionBusiness();
		transactionBusiness.setBlockNumber(transaction.getBlockNumber());
		transactionBusiness.setBlockHash(transaction.getBlockHash());
		transactionBusiness.setTxHash(transaction.getTxHash());
		transactionBusiness.setTransactionId(transaction.getId());
		transactionBusiness.setVioIndex(vioIndex);
		transactionBusiness.setCoinType(coinType);
		transactionBusiness.setTxFrom(txFrom);
		transactionBusiness.setTxTo(txTo);
		transactionBusiness.setAmount(amount);
		transactionBusiness.setFee(BigInteger.ZERO);
		transactionBusiness.setTxTime(transaction.getTxTime());

		return transactionBusiness;
	}

	@Transactional
	protected void withdrawal(TransactionBusiness transactionBusiness) {
		withdrawalService.addUntreated(transactionBusiness);
	}

	protected void withdrawal(int vioIndex, String txFrom, String txTo, BigInteger amount, Transaction transaction, String coinType) {
		TransactionBusiness transactionBusiness = handleTransactionBusiness(new BigInteger(String.valueOf(vioIndex)), txFrom, txTo, amount, transaction, coinType);

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

	protected void deposit(int vioIndex, String txFrom, String txTo, BigInteger amount, Transaction transaction, String coinType) {
		TransactionBusiness transactionBusiness = handleTransactionBusiness(new BigInteger(String.valueOf(vioIndex)), txFrom, txTo, amount, transaction, coinType);

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

	protected void handleOmniData(String txId, OmniNullData omniNullData, boolean isWatchFrom, boolean isWatchTo, Transaction transaction) {
		if(omniNullData.getNullData() == null) {
			return;
		}

		if(omniNullData.getNullData().indexOf(omniUsdt) == -1) {
			return;
		}

		org.omnilayer.api.rawtransactions.entity.Transaction omniTransaction = null;
		try {
			omniTransaction = omniBlockService.getTransaction(txId);
		} catch (Exception e) {
			logger.error("omni get transaction error, txId:", txId);
			return;
		}

		if(!omniTransaction.getValid()) {
			logger.info("Transaction not validated, omniTransaction:{}", omniTransaction);
			return;
		}

		BigInteger amount = new BigDecimal(omniTransaction.getAmount()).multiply(Constants.BTC_UNIT).toBigInteger();

		if(isWatchFrom) {
			withdrawal(omniNullData.getVoutIndex(), omniTransaction.getSendingAddress(), omniTransaction.getReferenceAddress(), amount,transaction, CoinType.USDT.name());
			return;
		}

		if(isWatchTo) {
			deposit(omniNullData.getVoutIndex(), omniTransaction.getSendingAddress(), omniTransaction.getReferenceAddress(), amount,transaction, CoinType.USDT.name());
			return;
		}
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
			boolean isWatchFrom = false;
			boolean isWatchTo = false;
			OmniNullData omniNullData = new OmniNullData();

			for(int i=0; i<utxoVinList.size(); i++) {
				UtxoVin utxoVin = utxoVinList.get(i);
				Vout vout = utxoVin.getVout();
				if(isWatch(vout, transaction, null, i)) {
					isWatchFrom = true;
					if(!isAddUntreatedTransaction) {
						isAddUntreatedTransaction = isDuplicationTransaction(transaction.getBlockNumber(), transaction.getBlockHash(), transaction.getTxHash(), transaction);
					}
					if(!isAddUntreatedTransaction) {
						addUntreatedTransaction(transaction);
						isAddUntreatedTransaction = true;
					}

					withdrawal(i, vout.getScriptPubKey().getAddresses().get(0), null, vout.getValue().toBigInteger(), transaction, CoinType.BTC.name());
				}

			}
			for(int i=0; i<vOutList.size(); i++) {
				Vout vout = vOutList.get(i);
				if(isWatch(vout, transaction, omniNullData, i)) {
					isWatchTo = true;
					if(!isAddUntreatedTransaction) {
						isAddUntreatedTransaction = isDuplicationTransaction(transaction.getBlockNumber(), transaction.getBlockHash(), transaction.getTxHash(), transaction);
					}
					if(!isAddUntreatedTransaction) {
						addUntreatedTransaction(transaction);
						isAddUntreatedTransaction = true;
					}

					deposit(i, null, vout.getScriptPubKey().getAddresses().get(0), vout.getValue().toBigInteger(), transaction, CoinType.BTC.name());
				}
			}

			// omni数据处理
			handleOmniData(tx.getTxId(), omniNullData, isWatchFrom, isWatchTo, transaction);

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
