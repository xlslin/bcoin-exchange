package com.sharingif.blockchain.bitcoin.withdrawal.service.impl;


import com.sharingif.blockchain.api.bitcoin.entity.*;
import com.sharingif.blockchain.api.bitcoin.service.BitCoinApiService;
import com.sharingif.blockchain.bitcoin.account.model.entity.AccountJnl;
import com.sharingif.blockchain.bitcoin.account.model.entity.AccountUnspent;
import com.sharingif.blockchain.bitcoin.account.service.AccountService;
import com.sharingif.blockchain.bitcoin.api.withdrawal.entity.ApplyWithdrawalBitCoinReq;
import com.sharingif.blockchain.bitcoin.api.withdrawal.entity.ApplyWithdrawalBitCoinRsp;
import com.sharingif.blockchain.bitcoin.app.InvalidAddressException;
import com.sharingif.blockchain.bitcoin.app.constants.CoinType;
import com.sharingif.blockchain.bitcoin.app.constants.Constants;
import com.sharingif.blockchain.bitcoin.block.dao.TransactionBusinessDAO;
import com.sharingif.blockchain.bitcoin.block.model.entity.BlockChain;
import com.sharingif.blockchain.bitcoin.block.model.entity.TransactionBusiness;
import com.sharingif.blockchain.bitcoin.block.service.BitCoinBlockService;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WithdrawalServiceImpl extends BaseServiceImpl<Withdrawal, java.lang.String> implements WithdrawalService {
	
	private WithdrawalDAO withdrawalDAO;
	private TransactionBusinessDAO transactionBusinessDAO;
	private AccountService accountService;
	private JobConfig withdrawalInitNoticeNoticeJobConfig;
	private JobConfig withdrawalSuccessNoticeJobConfig;
	private JobConfig withdrawalFailureNoticeJobConfig;
	private JobService jobService;
	private BitCoinApiService bitCoinApiService;
	private BitCoinBlockService bitCoinBlockService;
	private WithdrawalTransactionService withdrawalTransactionService;

	public WithdrawalDAO getWithdrawalDAO() {
		return withdrawalDAO;
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
		if(btcAddress.length() != 34) {
			throw new InvalidAddressException();
		}

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

	protected void withdrawal(List<Withdrawal> withdrawalList) {
		// 取现总金额
		BigInteger withdrawalTotalBalance = BigInteger.ZERO;
		for(Withdrawal withdrawal : withdrawalList) {
			withdrawalTotalBalance = withdrawalTotalBalance.add(withdrawal.getAmount());
		}

		// 根据取现总金额获取可取现的账号和unspent
		List<AccountUnspent> accountUnspentList = accountService.getAccountListByBalance(CoinType.BTC.name(), withdrawalTotalBalance);
		if(accountUnspentList == null) {
			return;
		}

		BigInteger fee = Constants.BTC_COIN_TRANSFOR_FEE;

		SignMessageReq req = new SignMessageReq();
		req.setFee(fee);

		List<SignMessageVinReq> vinList = new ArrayList<SignMessageVinReq>(accountUnspentList.size());
		for(AccountUnspent accountUnspent : accountUnspentList) {
			SignMessageVinReq signMessageVinReq = new SignMessageVinReq();
			signMessageVinReq.setFromAddress(accountUnspent.getAccount().getAddress());

			List<Unspent> unspentList = accountUnspent.getUnspentList();
			List<SignMessageUnspentReq> signMessageUnspentReqList =  new ArrayList<SignMessageUnspentReq>(unspentList.size());
			for(Unspent unspent : unspentList) {
				SignMessageUnspentReq signMessageUnspentReq = new SignMessageUnspentReq();
				signMessageUnspentReq.setTxId(unspent.getTxId());
				signMessageUnspentReq.setVout(unspent.getvOut());
				signMessageUnspentReq.setScriptPubKey(unspent.getScriptPubKey());
				signMessageUnspentReq.setAmount(unspent.getAmount().toBigInteger());

				signMessageUnspentReqList.add(signMessageUnspentReq);
			}

			signMessageVinReq.setUnspentList(signMessageUnspentReqList);

			vinList.add(signMessageVinReq);
		}

		List<SignMessageVoutReq> voutList = new ArrayList<SignMessageVoutReq>(withdrawalList.size());
		for(Withdrawal withdrawal : withdrawalList) {
			SignMessageVoutReq signMessageVoutReq = new SignMessageVoutReq();
			signMessageVoutReq.setToAddress(withdrawal.getTxTo());
			signMessageVoutReq.setAmount(withdrawal.getAmount());

			voutList.add(signMessageVoutReq);
		}

		SignMessageRsp rsp = bitCoinApiService.signMessage(req);
		String hexstring = bitCoinBlockService.signRawTransaction(rsp.getHexValue());

		updateStatusToProcessing(withdrawalList);

		String txHash = bitCoinBlockService.sendRawTransaction(hexstring);

		if(StringUtils.isTrimEmpty(txHash)) {
			logger.error("withdrawal generate txhash is null, withdrawalList:{}", withdrawalList);
			return;
		}

		updateWithdrawal(txHash, fee, accountUnspentList, withdrawalList);
	}

	@Override
	public void withdrawal() {
		Withdrawal queryWithdrawal = new Withdrawal();
		queryWithdrawal.setStatus(Withdrawal.STATUS_UNTREATED);
		PaginationCondition<Withdrawal> paginationCondition = new PaginationCondition<Withdrawal>();
		paginationCondition.setCondition(queryWithdrawal);
		paginationCondition.setQueryCount(false);
		paginationCondition.setCurrentPage(1);
		paginationCondition.setPageSize(20);

		PaginationRepertory<Withdrawal> withdrawalPaginationRepertory = withdrawalDAO.queryPagination(paginationCondition);
		List<Withdrawal> withdrawalList = withdrawalPaginationRepertory.getPageItems();
		if(withdrawalList == null || withdrawalList.isEmpty()) {
			return;
		}

		withdrawal(withdrawalList);
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
	public void readyInitNotice() {
		WithdrawalTransaction queryWithdrawalTransaction = new WithdrawalTransaction();
		queryWithdrawalTransaction.setStatus(Withdrawal.STATUS_PROCESSING);

		PaginationCondition<WithdrawalTransaction> paginationCondition = new PaginationCondition<>();
		paginationCondition.setCondition(queryWithdrawalTransaction);
		paginationCondition.setQueryCount(false);
		paginationCondition.setCurrentPage(1);
		paginationCondition.setPageSize(20);

		PaginationRepertory<WithdrawalTransaction> withdrawalPaginationRepertory = withdrawalTransactionService.getPagination(paginationCondition);
		List<WithdrawalTransaction> withdrawalTransactionList = withdrawalPaginationRepertory.getPageItems();
		if(withdrawalTransactionList == null || withdrawalTransactionList.isEmpty()) {
			return;
		}

		for (WithdrawalTransaction withdrawalTransaction : withdrawalTransactionList) {
			List<WithdrawalVout> withdrawalVoutList = withdrawalTransactionService.getWithdrawalVoutService().getByTxHash(withdrawalTransaction.getTxHash());
			readyInitNotice(withdrawalTransaction, withdrawalVoutList);
		}

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
		// TODO发送通知信息

		initNotice(withdrawal.getId(), withdrawal.getTxHash());
	}

	@Override
	public void initNotice(String id) {
		Withdrawal withdrawal = withdrawalDAO.queryById(id);
		if(!Withdrawal.STATUS_INIT_NOTICED.equals(withdrawal.getStatus())) {
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

	protected boolean updateWithdrawalTransactionStatusToSuccess(TransactionBusiness transactionBusiness) {
		List<WithdrawalVin> withdrawalVinList = withdrawalTransactionService.getWithdrawalVinService().getByTxHash(transactionBusiness.getTxHash());
		int transactionBusinessCount = 0;
		for (WithdrawalVin queryWithdrawalVin : withdrawalVinList) {
			if (queryWithdrawalVin.getAddress().equals(transactionBusiness.getTxFrom())) {
				continue;
			}

			TransactionBusiness queryTransactionBusiness = new TransactionBusiness();
			queryTransactionBusiness.setTxHash(transactionBusiness.getTxHash());
			queryTransactionBusiness.setTxFrom(queryWithdrawalVin.getAddress());
			queryTransactionBusiness.setStatus(TransactionBusiness.STATUS_UNTREATED);
			queryTransactionBusiness.setTxStatus(BlockChain.STATUS_VERIFY_VALID);

			List<TransactionBusiness> transactionBusinessList = transactionBusinessDAO.queryList(queryTransactionBusiness);
			if(transactionBusinessList == null || transactionBusinessList.isEmpty()) {
				return false;
			}

			for(TransactionBusiness tb : transactionBusinessList) {
				if(TransactionBusiness.STATUS_UNTREATED.equals(tb.getStatus()) && queryWithdrawalVin.getAddress().equals(tb.getTxFrom())) {
					transactionBusinessCount++;
				} else {
					return false;
				}

			}

		}

		if(transactionBusinessCount == 1) {
			return true;
		}

		return false;
	}

	@Transactional
	protected void finishNotice(TransactionBusiness transactionBusiness, boolean updateWithdrawalTransactionStatusToSuccess) {
		TransactionBusiness updateTransactionBusiness = new TransactionBusiness();
		updateTransactionBusiness.setId(transactionBusiness.getId());
		updateTransactionBusiness.setStatus(TransactionBusiness.STATUS_FINISH_NOTICED);

		transactionBusinessDAO.updateById(updateTransactionBusiness);

		if(updateWithdrawalTransactionStatusToSuccess) {
			withdrawalTransactionService.updateStatusToSuccess(transactionBusiness.getTxHash());

			Withdrawal updateWithdrawal = new Withdrawal();
			updateWithdrawal.setTxHash(transactionBusiness.getTxHash());
			updateWithdrawal.setStatus(Withdrawal.STATUS_SUCCESS);

			withdrawalDAO.updateByTxHash(updateWithdrawal);
		}
	}

	@Override
	public void finishNotice() {
		TransactionBusiness queryTransactionBusiness = new TransactionBusiness();
		queryTransactionBusiness.setStatus(TransactionBusiness.STATUS_UNTREATED);
		queryTransactionBusiness.setSettleStatus(TransactionBusiness.SETTLE_STATUS_FINISH);
		queryTransactionBusiness.setType(TransactionBusiness.TYPE_WITHDRAWAL);
		PaginationCondition<TransactionBusiness> paginationCondition = new PaginationCondition<TransactionBusiness>();
		paginationCondition.setCondition(queryTransactionBusiness);
		paginationCondition.setQueryCount(false);
		paginationCondition.setCurrentPage(1);
		paginationCondition.setPageSize(20);

		PaginationRepertory<TransactionBusiness> transactionBusinessPaginationRepertory = transactionBusinessDAO.queryPagination(paginationCondition);
		List<TransactionBusiness> transactionBusinessList = transactionBusinessPaginationRepertory.getPageItems();
		if(transactionBusinessList == null || transactionBusinessList.isEmpty()) {
			return;
		}

		for (TransactionBusiness transactionBusiness : transactionBusinessList) {

			boolean updateWithdrawalTransactionStatusToSuccess = false;
			if(BlockChain.STATUS_VERIFY_VALID.equals(transactionBusiness.getTxStatus())) {
				WithdrawalTransaction withdrawalTransaction = withdrawalTransactionService.getById(transactionBusiness.getTxHash());
				if(withdrawalTransaction != null) {
					updateWithdrawalTransactionStatusToSuccess(transactionBusiness);
				}
			}

			finishNotice(transactionBusiness, updateWithdrawalTransactionStatusToSuccess);
		}

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

	protected void readyWithdrawalNotice(String status, JobConfig jobConfig) {
		Withdrawal queryWithdrawal = new Withdrawal();
		queryWithdrawal.setStatus(status);
		PaginationCondition<Withdrawal> paginationCondition = new PaginationCondition<Withdrawal>();
		paginationCondition.setCondition(queryWithdrawal);
		paginationCondition.setQueryCount(false);
		paginationCondition.setCurrentPage(1);
		paginationCondition.setPageSize(20);

		PaginationRepertory<Withdrawal> withdrawalPaginationRepertory = withdrawalDAO.queryPagination(paginationCondition);
		List<Withdrawal> withdrawalList = withdrawalPaginationRepertory.getPageItems();
		if(withdrawalList == null || withdrawalList.isEmpty()) {
			return;
		}

		for(Withdrawal withdrawal : withdrawalList) {
			readyWithdrawalNotice(withdrawal, jobConfig);
		}
	}

	@Override
	public void readyWithdrawalSuccessNotice() {
		readyWithdrawalNotice(Withdrawal.STATUS_SUCCESS, withdrawalSuccessNoticeJobConfig);
	}

	@Override
	public void withdrawalSuccessNotice(String id) {
		// TODO发送通知信息
		Withdrawal withdrawal = getById(id);


		updateStatusToSuccessNoticed(id);
	}

	@Override
	public void readyWithdrawalFailureNotice() {
		readyWithdrawalNotice(Withdrawal.STATUS_FAILURE, withdrawalFailureNoticeJobConfig);
	}

	@Override
	public void withdrawalFailureNotice(String id) {
		// TODO发送通知信息
		Withdrawal withdrawal = getById(id);


		updateStatusToFailureNoticed(id);
	}
	
}
