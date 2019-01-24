package com.sharingif.blockchain.bitcoin.deposit.service.impl;


import com.sharingif.blockchain.bitcoin.account.model.entity.AccountJnl;
import com.sharingif.blockchain.bitcoin.account.service.AccountService;
import com.sharingif.blockchain.bitcoin.block.dao.TransactionBusinessDAO;
import com.sharingif.blockchain.bitcoin.block.model.entity.BlockChain;
import com.sharingif.blockchain.bitcoin.block.model.entity.TransactionBusiness;
import com.sharingif.blockchain.bitcoin.deposit.service.DepositService;
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

    private TransactionBusinessDAO transactionBusinessDAO;
    private JobConfig depositInitNoticeJobConfig;
    private JobService jobService;
    private AccountService accountService;
    private JobConfig depositFinishNoticeJobConfig;

    @Resource
    public void setTransactionBusinessDAO(TransactionBusinessDAO transactionBusinessDAO) {
        this.transactionBusinessDAO = transactionBusinessDAO;
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
        transactionBusiness.setStatus(TransactionBusiness.STATUS_UNTREATED);
        transactionBusiness.setSettleStatus(TransactionBusiness.SETTLE_STATUS_UNTREATED);
        transactionBusiness.setTxStatus(BlockChain.STATUS_UNVERIFIED);

        transactionBusinessDAO.insert(transactionBusiness);

        deposit(transactionBusiness);
    }

    @Transactional
    protected void readyInitDepositNotice(TransactionBusiness transactionBusiness) {
        TransactionBusiness updateTransactionBusiness = new TransactionBusiness();
        updateTransactionBusiness.setId(transactionBusiness.getId());
        updateTransactionBusiness.setStatus(TransactionBusiness.STATUS_INIT_NOTICE);

        transactionBusinessDAO.updateById(updateTransactionBusiness);

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

        PaginationRepertory<TransactionBusiness> transactionBusinessPaginationRepertory = transactionBusinessDAO.queryPagination(paginationCondition);
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
        TransactionBusiness transactionBusiness = transactionBusinessDAO.queryById(id);
        if(!TransactionBusiness.STATUS_INIT_NOTICE.equals(transactionBusiness.getStatus())) {
            return;
        }

        TransactionBusiness updateTransactionBusiness = new TransactionBusiness();
        updateTransactionBusiness.setId(id);
        updateTransactionBusiness.setStatus(TransactionBusiness.STATUS_INIT_NOTICED);

        transactionBusinessDAO.updateById(updateTransactionBusiness);
    }

    @Override
    public void deposit(TransactionBusiness transactionBusiness) {
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

    protected void depositReback(TransactionBusiness transactionBusiness) {
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

    @Override
    public void depositConfirmed(TransactionBusiness transactionBusiness) {
        if(BlockChain.STATUS_VERIFY_VALID.equals(transactionBusiness.getTxStatus())) {
            return;
        } else {
            depositReback(transactionBusiness);
        }
    }

    @Transactional
    protected void readyFinishNotice(TransactionBusiness transactionBusiness) {
        TransactionBusiness updateTransactionBusiness = new TransactionBusiness();
        updateTransactionBusiness.setId(transactionBusiness.getId());
        updateTransactionBusiness.setStatus(TransactionBusiness.STATUS_FINISH_NOTICING);

        transactionBusinessDAO.updateById(updateTransactionBusiness);

        JobModel jobModel = new JobModel();
        jobModel.setLookupPath(depositFinishNoticeJobConfig.getLookupPath());
        jobModel.setDataId(transactionBusiness.getId());
        jobModel.setPlanExecuteTime(transactionBusiness.getTxTime());
        jobService.add(null, jobModel);
    }

    @Override
    public void readyFinishNotice() {
        TransactionBusiness queryTransactionBusiness = new TransactionBusiness();
        queryTransactionBusiness.setStatus(TransactionBusiness.STATUS_INIT_NOTICED);
        queryTransactionBusiness.setSettleStatus(TransactionBusiness.SETTLE_STATUS_FINISH);
        queryTransactionBusiness.setType(TransactionBusiness.TYPE_DEPOSIT);
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
            readyFinishNotice(transactionBusiness);
        }
    }

    @Override
    public void finishNotice(String id) {
        TransactionBusiness transactionBusiness = transactionBusinessDAO.queryById(id);
        if(!TransactionBusiness.STATUS_FINISH_NOTICING.equals(transactionBusiness.getStatus())) {
            return;
        }

        TransactionBusiness updateTransactionBusiness = new TransactionBusiness();
        updateTransactionBusiness.setId(id);
        updateTransactionBusiness.setStatus(TransactionBusiness.STATUS_FINISH_NOTICED);

        transactionBusinessDAO.updateById(updateTransactionBusiness);
    }

}
