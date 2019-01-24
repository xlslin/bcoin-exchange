package com.sharingif.blockchain.bitcoin.withdrawal.service.impl;


import javax.annotation.Resource;

import com.sharingif.blockchain.bitcoin.account.model.entity.AccountJnl;
import com.sharingif.blockchain.bitcoin.account.service.AccountService;
import com.sharingif.blockchain.bitcoin.block.dao.TransactionBusinessDAO;
import com.sharingif.blockchain.bitcoin.block.model.entity.BlockChain;
import com.sharingif.blockchain.bitcoin.block.model.entity.Transaction;
import com.sharingif.blockchain.bitcoin.block.model.entity.TransactionBusiness;
import com.sharingif.cube.batch.core.JobConfig;
import com.sharingif.cube.batch.core.JobModel;
import com.sharingif.cube.batch.core.JobService;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;
import org.springframework.stereotype.Service;

import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.Withdrawal;
import com.sharingif.blockchain.bitcoin.withdrawal.dao.WithdrawalDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.bitcoin.withdrawal.service.WithdrawalService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
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

	@Override
	public void addUntreated(TransactionBusiness transactionBusiness) {

		transactionBusiness.setType(TransactionBusiness.TYPE_WITHDRAWAL);
		transactionBusiness.setStatus(TransactionBusiness.STATUS_UNTREATED);
		transactionBusiness.setSettleStatus(TransactionBusiness.SETTLE_STATUS_UNTREATED);
		transactionBusiness.setTxStatus(BlockChain.STATUS_UNVERIFIED);

		transactionBusinessDAO.insert(transactionBusiness);

		withdrawal(transactionBusiness);
	}

	@Transactional
	protected void readyInitNotice(TransactionBusiness transactionBusiness) {

		JobModel jobModel = new JobModel();
		jobModel.setLookupPath(withdrawalInitNoticeNoticeJobConfig.getLookupPath());
		jobModel.setDataId(transactionBusiness.getId());
		jobModel.setPlanExecuteTime(transactionBusiness.getTxTime());
		jobService.add(null, jobModel);

		TransactionBusiness updateTransactionBusiness = new TransactionBusiness();
		updateTransactionBusiness.setId(transactionBusiness.getId());
		updateTransactionBusiness.setStatus(TransactionBusiness.STATUS_INIT_NOTICE);

		transactionBusinessDAO.updateById(updateTransactionBusiness);
	}

	@Override
	public void readyInitNotice() {
		TransactionBusiness queryTransactionBusiness = new TransactionBusiness();
		queryTransactionBusiness.setStatus(TransactionBusiness.STATUS_UNTREATED);
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
			readyInitNotice(transactionBusiness);
		}
	}

	@Transactional
	protected void initNotice(TransactionBusiness transactionBusiness, Withdrawal withdrawal, Transaction transaction) {
		TransactionBusiness updateTransactionBusiness = new TransactionBusiness();
		updateTransactionBusiness.setId(transactionBusiness.getId());
		updateTransactionBusiness.setStatus(TransactionBusiness.STATUS_INIT_NOTICED);

		transactionBusinessDAO.updateById(updateTransactionBusiness);

		if(withdrawal == null) {
			return;
		}

		// 分叉数据不处理，防止通知提现失败导致重复提现
		if(BlockChain.STATUS_VERIFY_INVALID.equals(transactionBusiness.getTxStatus())) {
			return;
		}

	}

	@Override
	public void initNotice(String id) {
		TransactionBusiness transactionBusiness = transactionBusinessDAO.queryById(id);
		if(!TransactionBusiness.STATUS_INIT_NOTICE.equals(transactionBusiness.getStatus())) {
			return;
		}

//		Withdrawal withdrawal = getWithdrawalByTxHash(transactionBusiness.getTxHash());
//		Transaction transaction = transactionService.getById(transactionBusiness.getTransactionId());
		initNotice(transactionBusiness, null, null);

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
	protected void finishNotice(TransactionBusiness transactionBusiness, Withdrawal withdrawal) {
		TransactionBusiness updateTransactionBusiness = new TransactionBusiness();
		updateTransactionBusiness.setId(transactionBusiness.getId());
		updateTransactionBusiness.setStatus(TransactionBusiness.STATUS_FINISH_NOTICED);

		transactionBusinessDAO.updateById(updateTransactionBusiness);

		if(withdrawal == null) {
			return;
		}

		// 分叉无效数据不做处理
		if(BlockChain.STATUS_VERIFY_INVALID.equals(transactionBusiness.getTxStatus())) {
			return;
		}

		Withdrawal updateWithdrawal = new Withdrawal();
		updateWithdrawal.setId(withdrawal.getId());
//		updateWithdrawal.setStatus(Withdrawal.STATUS_SUCCESS);

		withdrawalDAO.updateById(updateWithdrawal);

	}

	@Override
	public void finishNotice() {
		TransactionBusiness queryTransactionBusiness = new TransactionBusiness();
		queryTransactionBusiness.setStatus(TransactionBusiness.STATUS_INIT_NOTICED);
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
//			Withdrawal withdrawal = getWithdrawalByTxHash(transactionBusiness.getTxHash());
			finishNotice(transactionBusiness,null);
		}
	}
	
}
