package com.sharingif.blockchain.ether.deposit.service.impl;


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
    private JobConfig depositInitDepositNoticeJobConfig;
    private JobService jobService;

    @Resource
    public void setTransactionBusinessService(TransactionBusinessService transactionBusinessService) {
        this.transactionBusinessService = transactionBusinessService;
    }
    @Resource
    public void setDepositInitDepositNoticeJobConfig(JobConfig depositInitDepositNoticeJobConfig) {
        this.depositInitDepositNoticeJobConfig = depositInitDepositNoticeJobConfig;
    }
    @Resource
    public void setJobService(JobService jobService) {
        this.jobService = jobService;
    }

    @Override
    public void addUntreated(TransactionBusiness transactionBusiness) {
        transactionBusiness.setStatus(TransactionBusiness.STATUS_UNTREATED);
        transactionBusiness.setType(TransactionBusiness.TYPE_DEPOSIT);

        transactionBusinessService.add(transactionBusiness);
    }

    @Transactional
    protected void readyDepositNotice(TransactionBusiness transactionBusiness) {
        transactionBusinessService.updateStatusToInitNotice(transactionBusiness.getId());

        JobModel jobModel = new JobModel();
        jobModel.setLookupPath(depositInitDepositNoticeJobConfig.getLookupPath());
        jobModel.setDataId(transactionBusiness.getId());
        jobModel.setPlanExecuteTime(transactionBusiness.getCreateTime());
        jobService.add(null, jobModel);
    }

    @Override
    public void readyDepositNotice() {
        TransactionBusiness queryTransactionBusiness = new TransactionBusiness();
        queryTransactionBusiness.setStatus(TransactionBusiness.STATUS_UNTREATED);
        queryTransactionBusiness.setType(TransactionBusiness.TYPE_DEPOSIT);
        PaginationCondition<TransactionBusiness> blockChainPaginationCondition = new PaginationCondition<TransactionBusiness>();
        blockChainPaginationCondition.setCondition(queryTransactionBusiness);
        blockChainPaginationCondition.setQueryCount(false);
        blockChainPaginationCondition.setCurrentPage(1);
        blockChainPaginationCondition.setPageSize(20);

        PaginationRepertory<TransactionBusiness> transactionBusinessPaginationRepertory = transactionBusinessService.getPagination(blockChainPaginationCondition);
        List<TransactionBusiness> transactionBusinessList = transactionBusinessPaginationRepertory.getPageItems();
        if(transactionBusinessList == null || transactionBusinessList.isEmpty()) {
            return;
        }

        for (TransactionBusiness transactionBusiness : transactionBusinessList) {
            readyDepositNotice(transactionBusiness);
        }
    }

    @Override
    public void initDepositNotice(String id) {
        TransactionBusiness transactionBusiness = transactionBusinessService.getById(id);
        if(!TransactionBusiness.STATUS_INIT_NOTICE.equals(transactionBusiness.getStatus())) {
            return;
        }

        transactionBusinessService.updateStatusToInitNoticed(id);
    }

}
