package com.sharingif.blockchain.ether.deposit.service.impl;


import com.sharingif.blockchain.ether.account.model.entity.AccountJnl;
import com.sharingif.blockchain.ether.account.service.AccountService;
import com.sharingif.blockchain.ether.block.model.entity.Transaction;
import com.sharingif.blockchain.ether.block.model.entity.TransactionBusiness;
import com.sharingif.blockchain.ether.block.service.TransactionBusinessService;
import com.sharingif.blockchain.ether.deposit.service.DepositService;
import com.sharingif.cube.batch.core.JobConfig;
import com.sharingif.cube.batch.core.JobModel;
import com.sharingif.cube.batch.core.JobService;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DepositServiceImpl implements DepositService {

    private TransactionBusinessService transactionBusinessService;
    private JobConfig depositInitNoticeJobConfig;
    private JobService jobService;
    private AccountService accountService;
    private JobConfig depositFinishNoticeJobConfig;

    @Resource
    public void setTransactionBusinessService(TransactionBusinessService transactionBusinessService) {
        this.transactionBusinessService = transactionBusinessService;
    }
    @Resource
    public void setDepositInitNoticeJobConfig(JobConfig depositInitNoticeJobConfig) {
        this.depositInitNoticeJobConfig = depositInitNoticeJobConfig;
    }
    @Resource
    public void setJobService(JobService jobService) {
        this.jobService = jobService;
    }
    @Resource
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }
    @Resource
    public void setDepositFinishNoticeJobConfig(JobConfig depositFinishNoticeJobConfig) {
        this.depositFinishNoticeJobConfig = depositFinishNoticeJobConfig;
    }

    @Override
    public void addUntreated(TransactionBusiness transactionBusiness) {
        transactionBusiness.setType(TransactionBusiness.TYPE_DEPOSIT);

        transactionBusinessService.addUntreated(transactionBusiness);

        deposit(transactionBusiness);
    }

    @Transactional
    protected void readyInitDepositNotice(TransactionBusiness transactionBusiness) {
        transactionBusinessService.updateStatusToInitNotice(transactionBusiness.getId());

        JobModel jobModel = new JobModel();
        jobModel.setLookupPath(depositInitNoticeJobConfig.getLookupPath());
        jobModel.setDataId(transactionBusiness.getId());
        jobModel.setPlanExecuteTime(transactionBusiness.getTxTime());
        jobService.add(null, jobModel);
    }

    @Override
    public void readyInitNotice() {
        TransactionBusiness queryTransactionBusiness = new TransactionBusiness();
        queryTransactionBusiness.setStatus(TransactionBusiness.STATUS_UNTREATED);
        queryTransactionBusiness.setType(TransactionBusiness.TYPE_DEPOSIT);
        PaginationCondition<TransactionBusiness> paginationCondition = new PaginationCondition<TransactionBusiness>();
        paginationCondition.setCondition(queryTransactionBusiness);
        paginationCondition.setQueryCount(false);
        paginationCondition.setCurrentPage(1);
        paginationCondition.setPageSize(20);

        PaginationRepertory<TransactionBusiness> transactionBusinessPaginationRepertory = transactionBusinessService.getPagination(paginationCondition);
        List<TransactionBusiness> transactionBusinessList = transactionBusinessPaginationRepertory.getPageItems();
        if(transactionBusinessList == null || transactionBusinessList.isEmpty()) {
            return;
        }

        for (TransactionBusiness transactionBusiness : transactionBusinessList) {
            readyInitDepositNotice(transactionBusiness);
        }
    }

    @Override
    public void initNotice(String id) {
        TransactionBusiness transactionBusiness = transactionBusinessService.getById(id);
        if(!TransactionBusiness.STATUS_INIT_NOTICE.equals(transactionBusiness.getStatus())) {
            return;
        }

        transactionBusinessService.updateStatusToInitNoticed(id);
    }

    @Override
    public void deposit(TransactionBusiness transactionBusiness) {
        if(Transaction.TX_RECEIPT_STATUS_FAIL.equals(transactionBusiness.getTxReceiptStatus())) {
            return;
        }

        accountService.addBalance(
                transactionBusiness.getTxTo()
                ,transactionBusiness.getCoinType()
                ,transactionBusiness.getAmount()
                ,transactionBusiness.getTxFrom()
                ,transactionBusiness.getTxTo()
                ,AccountJnl.TYPE_DEPOSIT
                ,transactionBusiness.getId()
                ,transactionBusiness.getTxTime()
        );
    }

    @Override
    public void depositReback(TransactionBusiness transactionBusiness) {
        if(Transaction.TX_RECEIPT_STATUS_FAIL.equals(transactionBusiness.getTxReceiptStatus())) {
            return;
        }

        accountService.subtractBalance(
                transactionBusiness.getTxTo()
                ,transactionBusiness.getCoinType()
                ,transactionBusiness.getAmount()
                ,transactionBusiness.getTxFrom()
                ,transactionBusiness.getTxTo()
                ,AccountJnl.TYPE_DEPOSIT_REBACK
                ,transactionBusiness.getId()
                ,transactionBusiness.getTxTime()
        );
    }

    @Transactional
    protected void readyFinishNotice(TransactionBusiness transactionBusiness) {
        transactionBusinessService.updateStatusToFinishNoticing(transactionBusiness.getId());

        JobModel jobModel = new JobModel();
        jobModel.setLookupPath(depositFinishNoticeJobConfig.getLookupPath());
        jobModel.setDataId(transactionBusiness.getId());
        jobModel.setPlanExecuteTime(transactionBusiness.getTxTime());
        jobService.add(null, jobModel);
    }

    @Override
    public void readyFinishNotice() {
        TransactionBusiness queryTransactionBusiness = new TransactionBusiness();
        queryTransactionBusiness.setStatus(TransactionBusiness.STATUS_SETTLED);
        queryTransactionBusiness.setType(TransactionBusiness.TYPE_DEPOSIT);
        PaginationCondition<TransactionBusiness> paginationCondition = new PaginationCondition<TransactionBusiness>();
        paginationCondition.setCondition(queryTransactionBusiness);
        paginationCondition.setQueryCount(false);
        paginationCondition.setCurrentPage(1);
        paginationCondition.setPageSize(20);

        PaginationRepertory<TransactionBusiness> transactionBusinessPaginationRepertory = transactionBusinessService.getPagination(paginationCondition);
        List<TransactionBusiness> transactionBusinessList = transactionBusinessPaginationRepertory.getPageItems();
        if(transactionBusinessList == null || transactionBusinessList.isEmpty()) {
            return;
        }

        for (TransactionBusiness transactionBusiness : transactionBusinessList) {
            readyFinishNotice(transactionBusiness);
        }
    }

    @Override
    public void finishNotice(String id) {
        TransactionBusiness transactionBusiness = transactionBusinessService.getById(id);
        if(!TransactionBusiness.STATUS_FINISH_NOTICING.equals(transactionBusiness.getStatus())) {
            return;
        }

        transactionBusinessService.updateStatusToFinishNoticed(id);
    }

}
