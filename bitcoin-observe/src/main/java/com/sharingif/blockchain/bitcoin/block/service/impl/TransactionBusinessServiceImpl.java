package com.sharingif.blockchain.bitcoin.block.service.impl;


import javax.annotation.Resource;

import com.sharingif.blockchain.bitcoin.block.model.entity.BlockChain;
import com.sharingif.blockchain.bitcoin.block.service.TransactionBusinessAccountService;
import com.sharingif.blockchain.bitcoin.deposit.service.DepositService;
import com.sharingif.blockchain.bitcoin.withdrawal.service.WithdrawalService;
import org.springframework.stereotype.Service;

import com.sharingif.blockchain.bitcoin.block.model.entity.TransactionBusiness;
import com.sharingif.blockchain.bitcoin.block.dao.TransactionBusinessDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.bitcoin.block.service.TransactionBusinessService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Service
public class TransactionBusinessServiceImpl extends BaseServiceImpl<TransactionBusiness, java.lang.String> implements TransactionBusinessService {
	
	private TransactionBusinessDAO transactionBusinessDAO;
	private DepositService depositService;
	private WithdrawalService withdrawalService;
	private TransactionBusinessAccountService transactionBusinessAccountService;

	public TransactionBusinessDAO getTransactionBusinessDAO() {
		return transactionBusinessDAO;
	}
	@Resource
	public void setTransactionBusinessDAO(TransactionBusinessDAO transactionBusinessDAO) {
		super.setBaseDAO(transactionBusinessDAO);
		this.transactionBusinessDAO = transactionBusinessDAO;
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
	public void setTransactionBusinessAccountService(TransactionBusinessAccountService transactionBusinessAccountService) {
		this.transactionBusinessAccountService = transactionBusinessAccountService;
	}

	@Override
	public int updateSettleStatusToSettling(String id) {
		TransactionBusiness transactionBusiness = new TransactionBusiness();
		transactionBusiness.setId(id);
		transactionBusiness.setSettleStatus(TransactionBusiness.SETTLE_STATUS_PROCESSING);

		return transactionBusinessDAO.updateById(transactionBusiness);
	}

	@Override
	public int updateSettleStatusToSettled(String address, String coinType, BigInteger blockNumber) {

		return transactionBusinessDAO.updateSettleStatusByAddressCoinTypeBlockNumberSettleStatus(
				TransactionBusiness.SETTLE_STATUS_FINISH
				,address
				,coinType
				,blockNumber
				,TransactionBusiness.SETTLE_STATUS_PROCESSING
		);
	}

	@Override
	public int getUnsettledCountByAddressCoinTypeSettleStatus(String address, String coinType) {
		return transactionBusinessDAO.queryCountByAddressCoinTypeSettleStatus(address, coinType, TransactionBusiness.SETTLE_STATUS_FINISH);
	}

	@Override
	public void updateTxStatusToValidSettleStatusToReady(BigInteger blockNumber, String blockHash) {
		TransactionBusiness transactionBusiness = new TransactionBusiness();
		transactionBusiness.setBlockNumber(blockNumber);
		transactionBusiness.setBlockHash(blockHash);

		transactionBusiness.setTxStatus(BlockChain.STATUS_VERIFY_VALID);
		transactionBusiness.setSettleStatus(TransactionBusiness.SETTLE_STATUS_READY);

		transactionBusinessDAO.updateByBlockNumberBlockHash(transactionBusiness);
	}

	@Override
	public void updateTxStatusToInvalidSettleStatusToReady(BigInteger blockNumber, String blockHash) {
		TransactionBusiness transactionBusiness = new TransactionBusiness();
		transactionBusiness.setBlockNumber(blockNumber);
		transactionBusiness.setBlockHash(blockHash);

		transactionBusiness.setTxStatus(BlockChain.STATUS_VERIFY_INVALID);
		transactionBusiness.setSettleStatus(TransactionBusiness.SETTLE_STATUS_READY);

		transactionBusinessDAO.updateByBlockNumberBlockHash(transactionBusiness);

	}

	@Override
	public List<TransactionBusiness> getSettleStatusReady() {
		TransactionBusiness queryTransactionBusiness = new TransactionBusiness();
		queryTransactionBusiness.setSettleStatus(TransactionBusiness.SETTLE_STATUS_READY);

		return transactionBusinessDAO.queryList(queryTransactionBusiness);
	}

	@Override
	public TransactionBusiness getTransactionBusiness(BigInteger blockNumber, String blockHash, String txHash, BigInteger vioIndex, String type) {
		TransactionBusiness queryTransactionBusiness = new TransactionBusiness();
		queryTransactionBusiness.setBlockNumber(blockNumber);
		queryTransactionBusiness.setBlockHash(blockHash);
		queryTransactionBusiness.setTxHash(txHash);
		queryTransactionBusiness.setVioIndex(vioIndex);
		queryTransactionBusiness.setType(type);

		return transactionBusinessDAO.query(queryTransactionBusiness);
	}

	@Transactional
	protected void depositSettle(TransactionBusiness transactionBusiness) {
		depositService.depositConfirmed(transactionBusiness);

		transactionBusinessAccountService.addTransactionBusinessAccount(transactionBusiness.getTxTo(), transactionBusiness.getCoinType());

		updateSettleStatusToSettling(transactionBusiness.getId());
	}

	@Transactional
	protected void withdrawalSettle(TransactionBusiness transactionBusiness) {
		withdrawalService.withdrawalConfirmed(transactionBusiness, transactionBusiness.getTxStatus());

		transactionBusinessAccountService.addTransactionBusinessAccount(transactionBusiness.getTxFrom(), transactionBusiness.getCoinType());

		updateSettleStatusToSettling(transactionBusiness.getId());
	}

	@Override
	public void settle(List<TransactionBusiness> transactionBusinessList) {
		transactionBusinessList.forEach(transactionBusiness -> {

			if(TransactionBusiness.TYPE_DEPOSIT.equals(transactionBusiness.getType())) {
				depositSettle(transactionBusiness);
				return;
			}

			if(TransactionBusiness.TYPE_WITHDRAWAL.equals(transactionBusiness.getType())) {
				withdrawalSettle(transactionBusiness);
				return;
			}

		});
	}

}
