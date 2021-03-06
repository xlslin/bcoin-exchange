package com.sharingif.blockchain.bitcoin.withdrawal.service.impl;


import com.sharingif.blockchain.api.bitcoin.entity.*;
import com.sharingif.blockchain.api.bitcoin.service.BitCoinApiService;
import com.sharingif.blockchain.bitcoin.account.model.entity.Account;
import com.sharingif.blockchain.bitcoin.account.model.entity.AccountJnl;
import com.sharingif.blockchain.bitcoin.account.model.entity.AccountUnspent;
import com.sharingif.blockchain.bitcoin.account.service.AccountService;
import com.sharingif.blockchain.bitcoin.api.withdrawal.entity.ApplyWithdrawalBitCoinReq;
import com.sharingif.blockchain.bitcoin.api.withdrawal.entity.ApplyWithdrawalBitCoinRsp;
import com.sharingif.blockchain.bitcoin.app.constants.CoinType;
import com.sharingif.blockchain.bitcoin.app.constants.Constants;
import com.sharingif.blockchain.bitcoin.app.exception.InvalidAddressException;
import com.sharingif.blockchain.bitcoin.block.dao.TransactionBusinessDAO;
import com.sharingif.blockchain.bitcoin.block.model.entity.BlockChain;
import com.sharingif.blockchain.bitcoin.block.model.entity.Transaction;
import com.sharingif.blockchain.bitcoin.block.model.entity.TransactionBusiness;
import com.sharingif.blockchain.bitcoin.block.service.BitCoinBlockService;
import com.sharingif.blockchain.bitcoin.block.service.TransactionService;
import com.sharingif.blockchain.bitcoin.withdrawal.dao.WithdrawalDAO;
import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.Withdrawal;
import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.WithdrawalTransaction;
import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.WithdrawalVin;
import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.WithdrawalVout;
import com.sharingif.blockchain.bitcoin.withdrawal.service.WithdrawalService;
import com.sharingif.blockchain.bitcoin.withdrawal.service.WithdrawalTransactionService;
import com.sharingif.cube.batch.core.JobConfig;
import com.sharingif.cube.batch.core.JobModel;
import com.sharingif.cube.batch.core.JobService;
import com.sharingif.cube.core.util.StringUtils;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import org.bitcoincore.api.wallet.entity.Unspent;
import org.bitcoinj.core.Base58;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WithdrawalServiceImpl extends BaseServiceImpl<Withdrawal, java.lang.String> implements WithdrawalService {

	private int omniUsdtProperty;
	private WithdrawalDAO withdrawalDAO;
	private TransactionService transactionService;
	private TransactionBusinessDAO transactionBusinessDAO;
	private AccountService accountService;
	private JobConfig withdrawalInitNoticeNoticeJobConfig;
	private JobConfig withdrawalSuccessNoticeJobConfig;
	private JobConfig withdrawalFailureNoticeJobConfig;
	private JobService jobService;
	private BitCoinApiService bitCoinApiService;
	private BitCoinBlockService bitCoinBlockService;
	private WithdrawalTransactionService withdrawalTransactionService;

	@Value("${omni.usdt.property}")
	public void setOmniUsdtProperty(int omniUsdtProperty) {
		this.omniUsdtProperty = omniUsdtProperty;
	}
	@Resource
	public void setWithdrawalDAO(WithdrawalDAO withdrawalDAO) {
		super.setBaseDAO(withdrawalDAO);
		this.withdrawalDAO = withdrawalDAO;
	}
	@Resource
	public void setTransactionBusinessDAO(TransactionBusinessDAO transactionBusinessDAO) {
		this.transactionBusinessDAO = transactionBusinessDAO;
	}
	@Resource
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	@Resource
	public void setWithdrawalInitNoticeNoticeJobConfig(JobConfig withdrawalInitNoticeNoticeJobConfig) {
		this.withdrawalInitNoticeNoticeJobConfig = withdrawalInitNoticeNoticeJobConfig;
	}
	@Resource
	public void setWithdrawalSuccessNoticeJobConfig(JobConfig withdrawalSuccessNoticeJobConfig) {
		this.withdrawalSuccessNoticeJobConfig = withdrawalSuccessNoticeJobConfig;
	}
	@Resource
	public void setWithdrawalFailureNoticeJobConfig(JobConfig withdrawalFailureNoticeJobConfig) {
		this.withdrawalFailureNoticeJobConfig = withdrawalFailureNoticeJobConfig;
	}
	@Resource
	public void setJobService(JobService jobService) {
		this.jobService = jobService;
	}
	@Resource
	public void setBitCoinApiService(BitCoinApiService bitCoinApiService) {
		this.bitCoinApiService = bitCoinApiService;
	}
	@Resource
	public void setBitCoinBlockService(BitCoinBlockService bitCoinBlockService) {
		this.bitCoinBlockService = bitCoinBlockService;
	}
	@Resource
	public void setWithdrawalTransactionService(WithdrawalTransactionService withdrawalTransactionService) {
		this.withdrawalTransactionService = withdrawalTransactionService;
	}
	@Resource
	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@Override
	public WithdrawalTransactionService getWithdrawalTransactionService() {
		return withdrawalTransactionService;
	}

	@Override
	public void addUntreated(TransactionBusiness transactionBusiness) {

		transactionBusiness.setType(TransactionBusiness.TYPE_WITHDRAWAL);
		transactionBusiness.setStatus(TransactionBusiness.STATUS_UNTREATED);
		transactionBusiness.setSettleStatus(TransactionBusiness.SETTLE_STATUS_UNTREATED);
		transactionBusiness.setTxStatus(BlockChain.STATUS_UNVERIFIED);

		transactionBusinessDAO.insert(transactionBusiness);

		withdrawal(transactionBusiness);
	}

	@Override
	public int updateStatusToProcessing(String id) {
		Withdrawal withdrawal = new Withdrawal();
		withdrawal.setId(id);

		withdrawal.setStatus(Withdrawal.STATUS_PROCESSING);

		return withdrawalDAO.updateById(withdrawal);
	}

	@Override
	public int updateStatusToNoticing(String id) {
		Withdrawal withdrawal = new Withdrawal();
		withdrawal.setId(id);

		withdrawal.setStatus(Withdrawal.STATUS_NOTICING);

		return withdrawalDAO.updateById(withdrawal);
	}

	@Override
	public int updateStatusToSuccessNoticed(String id) {
		Withdrawal withdrawal = new Withdrawal();
		withdrawal.setId(id);

		withdrawal.setStatus(Withdrawal.STATUS_SUCCESS_NOTICED);

		return withdrawalDAO.updateById(withdrawal);
	}

	@Override
	public int updateStatusToFailureNoticed(String id) {
		Withdrawal withdrawal = new Withdrawal();
		withdrawal.setId(id);

		withdrawal.setStatus(Withdrawal.STATUS_FAILURE_NOTICED);

		return withdrawalDAO.updateById(withdrawal);
	}

	@Override
	public ApplyWithdrawalBitCoinRsp apply(ApplyWithdrawalBitCoinReq req) {
		// 校验地址
		String btcAddress = req.getAddress();
		// 先判断长度是否是34
//		if(btcAddress.length() != 34) {
//			throw new InvalidAddressException();
//		}

		try {
			Base58.decodeChecked(btcAddress);
		} catch (Exception e) {
			throw new InvalidAddressException();
		}

        Withdrawal withdrawal = Withdrawal.convertApplyWithdrawalBitCoinReqToWithdrawal(req);
        withdrawal.setStatus(Withdrawal.STATUS_UNTREATED);

        withdrawalDAO.insert(withdrawal);

        ApplyWithdrawalBitCoinRsp rsp = new ApplyWithdrawalBitCoinRsp();
        rsp.setId(withdrawal.getId());
        rsp.setWithdrawalId(withdrawal.getWithdrawalId());

		return rsp;
	}

	@Transactional
	protected void updateStatusToProcessing(List<Withdrawal> withdrawalList) {
		for(Withdrawal withdrawal : withdrawalList) {
			updateStatusToProcessing(withdrawal.getId());
		}
	}

	@Transactional
	protected void updateWithdrawal(String txHash, BigInteger totalFee, List<AccountUnspent> accountUnspentList, List<Withdrawal> withdrawalList) {
		withdrawalTransactionService.addWithdrawalTransaction(txHash, totalFee, accountUnspentList, withdrawalList);

		BigInteger withdrawalFee = totalFee.divide(new BigInteger(String.valueOf(withdrawalList.size())));

		for(int i=0; i<withdrawalList.size()-1; i++) {
			Withdrawal withdrawal = withdrawalList.get(i);

			Withdrawal updateWithdrawal = new Withdrawal();
			updateWithdrawal.setId(withdrawal.getId());

			updateWithdrawal.setTxHash(txHash);
			updateWithdrawal.setFee(withdrawalFee);

			withdrawalDAO.updateById(updateWithdrawal);

			totalFee = totalFee.subtract(withdrawalFee);
		}

		Withdrawal withdrawal = withdrawalList.get(withdrawalList.size()-1);

		Withdrawal updateWithdrawal = new Withdrawal();
		updateWithdrawal.setId(withdrawal.getId());

		updateWithdrawal.setTxHash(txHash);
		updateWithdrawal.setFee(totalFee);

		withdrawalDAO.updateById(updateWithdrawal);

	}

	@Override
	public void btc(List<Withdrawal> withdrawalList) {
		// 取现总金额
		BigInteger withdrawalTotalBalance = BigInteger.ZERO;
		for(Withdrawal withdrawal : withdrawalList) {
			withdrawalTotalBalance = withdrawalTotalBalance.add(withdrawal.getAmount());
		}

		// 取现手续费
		BigInteger fee = Constants.BTC_COIN_TRANSFOR_FEE;

		// 根据取现总金额获取可取现的账号和unspent
		List<AccountUnspent> accountUnspentList = accountService.getAccountListByBalance(withdrawalTotalBalance.add(fee));
		if(accountUnspentList == null) {
			logger.error("withdrawal btc failure, Insufficient balance, withdrawal balance:{}", withdrawalTotalBalance.add(fee));
			return;
		}

		SignMessageReq req = new SignMessageReq();
		req.setFee(fee);

		List<SignMessageVinReq> vinList = new ArrayList<SignMessageVinReq>(accountUnspentList.size());
		for(AccountUnspent accountUnspent : accountUnspentList) {
			List<Unspent> unspentList = accountUnspent.getUnspentList();

			if(unspentList == null || unspentList.isEmpty()) {
				continue;
			}

			SignMessageVinReq signMessageVinReq = accountUnspent.getSignMessageVinReq();

			vinList.add(signMessageVinReq);
		}
		req.setVinList(vinList);

		List<SignMessageVoutReq> voutList = new ArrayList<SignMessageVoutReq>(withdrawalList.size());
		for(Withdrawal withdrawal : withdrawalList) {
			SignMessageVoutReq signMessageVoutReq = new SignMessageVoutReq();
			signMessageVoutReq.setToAddress(withdrawal.getTxTo());
			signMessageVoutReq.setAmount(withdrawal.getAmount());

			voutList.add(signMessageVoutReq);
		}
		req.setVoutList(voutList);

		SignMessageRsp rsp = bitCoinApiService.signMessage(req);
		String hexstring = rsp.getHexValue();

		updateStatusToProcessing(withdrawalList);
		accountUnspentList.forEach(accountUnspent ->{
			accountService.lockAccount(accountUnspent.getAccount().getId());
		});

		String txHash = bitCoinBlockService.sendRawTransaction(hexstring);

		if(StringUtils.isTrimEmpty(txHash)) {
			logger.error("withdrawal generate txhash is null, withdrawalList:{}", withdrawalList);
			return;
		}

		updateWithdrawal(txHash, fee, accountUnspentList, withdrawalList);
	}

	@Transactional
	protected void updateWithdrawal(String txHash, BigInteger fee, AccountUnspent accountUnspent, Withdrawal withdrawal) {
		withdrawalTransactionService.addWithdrawalTransaction(txHash, fee, accountUnspent, withdrawal);


		Withdrawal updateWithdrawal = new Withdrawal();
		updateWithdrawal.setId(withdrawal.getId());

		updateWithdrawal.setTxHash(txHash);
		updateWithdrawal.setFee(fee);

		withdrawalDAO.updateById(updateWithdrawal);

	}

	@Override
	public void usdt(List<Withdrawal> withdrawalList) {
		withdrawalList.forEach(withdrawal -> {

			// 取现手续费
			BigInteger fee = Constants.BTC_COIN_TRANSFOR_FEE;
			// USDT收款人btc金额
			BigInteger tranferToBalance = Constants.USDT_RECIPIENT_BTC_AMOUNT;

			AccountUnspent accountUnspent = accountService.getUsdtAccountByBalance(fee.add(tranferToBalance), withdrawal.getAmount());
			if(accountUnspent == null) {
				logger.error("withdrawal omni failure, Insufficient balance, fee:{},withdrawal balance:{}", fee.add(tranferToBalance), withdrawal.getAmount());
				return;
			}

			OmniSimpleSendSignMessageReq req = new OmniSimpleSendSignMessageReq();
			req.setFee(fee);

			SignMessageVinReq vin = accountUnspent.getSignMessageVinReq();
			req.setVin(vin);

			SignMessageVoutReq vout = new SignMessageVoutReq();
			vout.setToAddress(withdrawal.getTxTo());
			vout.setAmount(tranferToBalance);
			req.setVout(vout);

			String opReturn = String.format("6f6d6e6900000000%08x%016x", omniUsdtProperty, withdrawal.getAmount());
			req.setOpReturn(opReturn);

			req.setChange(accountUnspent.getAccount().getAddress());

			SignMessageRsp rsp = bitCoinApiService.omniSimpleSendSignMessage(req);
			String hexstring = rsp.getHexValue();

			updateStatusToProcessing(withdrawal.getId());
			accountService.lockAccount(accountUnspent.getAccount().getId());
			Account btcAccount = accountService.getAccount(accountUnspent.getAccount().getAddress(), CoinType.BTC.name());
			accountService.lockAccount(btcAccount.getId());

			String txHash = bitCoinBlockService.sendRawTransaction(hexstring);

			if(StringUtils.isTrimEmpty(txHash)) {
				logger.error("withdrawal generate txhash is null, withdrawalList:{}", withdrawalList);
				return;
			}

			updateWithdrawal(txHash, fee, accountUnspent, withdrawal);

		});
	}

	@Override
	public void withdrawal(TransactionBusiness transactionBusiness) {
		accountService.frozenBalance(
				transactionBusiness.getTxFrom()
				,transactionBusiness.getCoinType()
				,transactionBusiness.getAmount()
				,transactionBusiness.getTxFrom()
				,transactionBusiness.getTxTo()
				,AccountJnl.TYPE_WITHDRAWAL
				,transactionBusiness.getId()
				,transactionBusiness.getTxTime()
		);

	}

	@Transactional
	protected void readyInitNotice(WithdrawalTransaction withdrawalTransaction, List<WithdrawalVout> withdrawalVoutList) {
		for(WithdrawalVout withdrawalVout : withdrawalVoutList) {
			JobModel jobModel = new JobModel();
			jobModel.setLookupPath(withdrawalInitNoticeNoticeJobConfig.getLookupPath());
			jobModel.setDataId(withdrawalVout.getWithdrawalId());
			jobModel.setPlanExecuteTime(new Date());
			jobService.add(null, jobModel);

			Withdrawal updateWithdrawal = new Withdrawal();
			updateWithdrawal.setId(withdrawalVout.getWithdrawalId());
			updateWithdrawal.setStatus(Withdrawal.STATUS_INIT_NOTICE);

			withdrawalDAO.updateById(updateWithdrawal);
		}

		withdrawalTransactionService.updateStatusToInitNotice(withdrawalTransaction.getTxHash());
	}

	@Override
	public void readyInitNotice(List<WithdrawalTransaction> withdrawalTransactionList) {
		withdrawalTransactionList.forEach(withdrawalTransaction -> {
			List<WithdrawalVout> withdrawalVoutList = withdrawalTransactionService.getWithdrawalVoutService().getByTxHash(withdrawalTransaction.getTxHash());
			readyInitNotice(withdrawalTransaction, withdrawalVoutList);
		});
	}

	@Transactional
	protected void initNotice(String withdrawalId, String txHash) {
		Withdrawal updateWithdrawal = new Withdrawal();
		updateWithdrawal.setId(withdrawalId);
		updateWithdrawal.setStatus(Withdrawal.STATUS_INIT_NOTICED);

		withdrawalDAO.updateById(updateWithdrawal);

		Withdrawal queryWithdrawal = new Withdrawal();
		queryWithdrawal.setTxHash(txHash);
		queryWithdrawal.setStatus(Withdrawal.STATUS_INIT_NOTICE);
		List<Withdrawal> withdrawalList = withdrawalDAO.queryListForUpdate(queryWithdrawal);
		if(withdrawalList == null || withdrawalList.isEmpty()) {
			withdrawalTransactionService.updateStatusToInitNoticed(txHash);
		}
	}

	protected void initNotice(Withdrawal withdrawal) {

		DepositWithdrawalNoticeReq req = new DepositWithdrawalNoticeReq();
		req.setId(withdrawal.getId());
		req.setCoinType(withdrawal.getCoinType());
		req.setTxTo(withdrawal.getTxTo());
		req.setAmount(withdrawal.getAmount());
		req.setStatus(DepositWithdrawalNoticeReq.STATUS_PROCESSING);
		bitCoinApiService.depositWithdrawalNotice(req);

		initNotice(withdrawal.getId(), withdrawal.getTxHash());
	}

	@Override
	public void initNotice(String id) {
		Withdrawal withdrawal = withdrawalDAO.queryById(id);
		if(!Withdrawal.STATUS_INIT_NOTICE.equals(withdrawal.getStatus())) {
			return;
		}

		initNotice(withdrawal);
	}

	protected void withdrawalSuccess(TransactionBusiness transactionBusiness) {
		accountService.subtractFrozenBalance(
				transactionBusiness.getTxFrom()
				,transactionBusiness.getCoinType()
				,transactionBusiness.getAmount()
				,transactionBusiness.getTxFrom()
				,transactionBusiness.getTxTo()
				,transactionBusiness.getId()
				,transactionBusiness.getTxTime()
		);

	}

	protected void withdrawalFailure(TransactionBusiness transactionBusiness) {
		accountService.unFrozenBalance(
				transactionBusiness.getTxFrom()
				,transactionBusiness.getCoinType()
				,transactionBusiness.getAmount()
				,transactionBusiness.getTxFrom()
				,transactionBusiness.getTxTo()
				,AccountJnl.TYPE_WITHDRAWAL_REBACK
				,transactionBusiness.getId()
				,transactionBusiness.getTxTime()
		);

	}

	@Override
	public void withdrawalConfirmed(TransactionBusiness transactionBusiness, String txStatus) {
		if(BlockChain.STATUS_VERIFY_VALID.equals(txStatus)) {
			withdrawalSuccess(transactionBusiness);
		} else {
			withdrawalFailure(transactionBusiness);
		}
	}

	@Transactional
	protected void finishNotice(TransactionBusiness transactionBusiness) {
		TransactionBusiness updateTransactionBusiness = new TransactionBusiness();
		updateTransactionBusiness.setId(transactionBusiness.getId());
		updateTransactionBusiness.setStatus(TransactionBusiness.STATUS_FINISH_NOTICED);

		transactionBusinessDAO.updateById(updateTransactionBusiness);

		WithdrawalTransaction withdrawalTransaction = withdrawalTransactionService.getById(transactionBusiness.getTxHash());
		if(withdrawalTransaction == null) {
			return;
		}

		int withdrawalVinCount = withdrawalTransactionService.getWithdrawalVinService().getCountByTxHash(transactionBusiness.getTxHash());
		int transactionBusinessCount = transactionBusinessDAO.queryCountByTxHashTypeStatus(transactionBusiness.getTxHash(), TransactionBusiness.TYPE_WITHDRAWAL ,TransactionBusiness.STATUS_FINISH_NOTICED);

		// 解锁取现时锁定的账号
		accountService.unLockAccount(transactionBusiness.getTxFrom(), transactionBusiness.getCoinType());

		if(withdrawalVinCount == transactionBusinessCount) {
			withdrawalTransactionService.updateStatusToSuccess(transactionBusiness.getTxHash());

			Withdrawal updateWithdrawal = new Withdrawal();
			updateWithdrawal.setTxHash(transactionBusiness.getTxHash());
			updateWithdrawal.setTxTime(transactionBusiness.getTxTime());
			updateWithdrawal.setStatus(Withdrawal.STATUS_SUCCESS);

			withdrawalDAO.updateByTxHash(updateWithdrawal);
		}

	}

	@Override
	public void finishNotice(List<TransactionBusiness> transactionBusinessList) {
		transactionBusinessList.forEach(transactionBusiness -> {
			finishNotice(transactionBusiness);
		});
	}

	@Transactional
	protected void readyWithdrawalNotice(Withdrawal withdrawal, JobConfig jobConfig) {
		updateStatusToNoticing(withdrawal.getId());

		JobModel jobModel = new JobModel();
		jobModel.setLookupPath(jobConfig.getLookupPath());
		jobModel.setDataId(withdrawal.getId());
		jobModel.setPlanExecuteTime(withdrawal.getTxTime());
		jobService.add(null, jobModel);
	}

	@Override
	public void readyWithdrawalSuccessNotice(List<Withdrawal> withdrawalList) {
		withdrawalList.forEach(withdrawal -> {
			readyWithdrawalNotice(withdrawal, withdrawalSuccessNoticeJobConfig);
		});
	}

	@Override
	public void withdrawalSuccessNotice(String id) {
		Withdrawal withdrawal = getById(id);

		Transaction transaction = transactionService.getVerifyValidStatusByTxHash(withdrawal.getTxHash());

		DepositWithdrawalNoticeReq req = new DepositWithdrawalNoticeReq();
		req.setId(withdrawal.getId());
		req.setBlockNumber(transaction.getBlockNumber());
		req.setBlockHash(transaction.getBlockHash());
		req.setTxHash(withdrawal.getTxHash());
		req.setTxIndex(transaction.getTxIndex());
		req.setCoinType(withdrawal.getCoinType());
		req.setTxTo(withdrawal.getTxTo());
		req.setAmount(withdrawal.getAmount());
		req.setFee(withdrawal.getFee());
		req.setStatus(DepositWithdrawalNoticeReq.STATUS_SUCCESS);
		bitCoinApiService.depositWithdrawalNotice(req);

		updateStatusToSuccessNoticed(id);
	}

	@Override
	public void readyWithdrawalFailureNotice(List<Withdrawal> withdrawalList) {
		withdrawalList.forEach(withdrawal -> {
			readyWithdrawalNotice(withdrawal, withdrawalFailureNoticeJobConfig);
		});
	}

	@Override
	public void withdrawalFailureNotice(String id) {
		// 发送通知信息
		Withdrawal withdrawal = getById(id);

		DepositWithdrawalNoticeReq req = new DepositWithdrawalNoticeReq();
		req.setId(withdrawal.getId());
		req.setTxHash(withdrawal.getTxHash());
		req.setCoinType(withdrawal.getCoinType());
		req.setTxTo(withdrawal.getTxTo());
		req.setAmount(withdrawal.getAmount());
		req.setStatus(DepositWithdrawalNoticeReq.STATUS_FAIL);
		bitCoinApiService.depositWithdrawalNotice(req);

		updateStatusToFailureNoticed(id);
	}
	
}
