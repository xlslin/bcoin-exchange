package com.sharingif.blockchain.bitcoin.block.service.impl;


import javax.annotation.Resource;

import com.sharingif.blockchain.bitcoin.account.service.AddressListenerService;
import com.sharingif.blockchain.bitcoin.app.constants.Constants;
import com.sharingif.blockchain.bitcoin.app.constants.ScriptTypes;
import com.sharingif.blockchain.bitcoin.block.model.entity.BlockChain;
import com.sharingif.blockchain.bitcoin.block.model.entity.UtxoVin;
import com.sharingif.blockchain.bitcoin.block.service.BitCoinBlockService;
import com.sharingif.cube.core.util.StringUtils;
import org.bitcoincore.api.rawtransactions.entity.ScriptPubKey;
import org.bitcoincore.api.rawtransactions.entity.Vin;
import org.bitcoincore.api.rawtransactions.entity.Vout;
import org.springframework.stereotype.Service;

import com.sharingif.blockchain.bitcoin.block.model.entity.Transaction;
import com.sharingif.blockchain.bitcoin.block.dao.TransactionDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.bitcoin.block.service.TransactionService;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@Service
public class TransactionServiceImpl extends BaseServiceImpl<Transaction, java.lang.String> implements TransactionService {
	
	private TransactionDAO transactionDAO;
	private BitCoinBlockService bitCoinBlockService;
	private AddressListenerService addressListenerService;

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

	public void addUntreatedTransaction(Transaction transaction) {
		transaction.setTxStatus(BlockChain.STATUS_UNVERIFIED);
		add(transaction);
	}

	public void persistenceTransaction(Transaction transaction) {

		Transaction queryTransaction = new Transaction();
		queryTransaction.setBlockNumber(transaction.getBlockNumber());
		queryTransaction.setBlockHash(transaction.getBlockHash());
		queryTransaction.setTxHash(transaction.getTxHash());

		queryTransaction = transactionDAO.query(queryTransaction);

		if(queryTransaction != null) {
			return;
		}

		transaction.setTxStatus(BlockChain.STATUS_UNVERIFIED);
		add(transaction);
	}

	/**
	 * 处理输入交易金额
	 * @param blockHash
	 * @param vInList
	 * @return
	 */
	protected List<UtxoVin> handletUtxoVin(String blockHash, List<Vin> vInList) {
		List<UtxoVin> utxoVinList = new ArrayList<UtxoVin>(vInList.size());

		for(Vin vin : vInList) {

			String txId = vin.getTxId();

			// coinbase transaction
			if(StringUtils.isTrimEmpty(txId)) {
				continue;
			}

			org.bitcoincore.api.rawtransactions.entity.Transaction tx = bitCoinBlockService.getFullRawTransaction(txId, blockHash);
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

	@Override
	public void analysis(org.bitcoincore.api.rawtransactions.entity.Transaction tx, BigInteger blockNumber, String blockHash, Date blockCreateTime) {
		Transaction transaction = null;
		try {

			List<UtxoVin> utxoVinList = handletUtxoVin(blockHash, tx.getvIn());
			List<Vout> vOutList = tx.getvOut();

			updateValueUnit(utxoVinList, vOutList);

			BigInteger txFee = computeTxFee(utxoVinList, vOutList);

			transaction = new Transaction();
			transaction.setBlockNumber(blockNumber);
			transaction.setBlockHash(blockHash);
			transaction.setTxHash(tx.getTxId());
			transaction.setTxFee(txFee);
			transaction.setTxTime(blockCreateTime);
			transaction.setConfirmBlockNumber(0);

			boolean isAddUntreatedTransaction = false;

			for(UtxoVin utxoVin : utxoVinList) {
				boolean isWatchFrom = isWatch(utxoVin.getVout(), transaction);
				if(isWatchFrom) {
					if(!isAddUntreatedTransaction) {
						persistenceTransaction(transaction);
						isAddUntreatedTransaction = true;
					}

				}
			}

			for(Vout vout : vOutList) {

			}

		} catch (Exception e) {
		// geth连接超时
		logger.error("analysis block transaction error", e);
		logger.error("analysis block transaction error, transaction:{}", transaction);
		analysis(tx, blockNumber, blockHash, blockCreateTime);
	}
	}
}
