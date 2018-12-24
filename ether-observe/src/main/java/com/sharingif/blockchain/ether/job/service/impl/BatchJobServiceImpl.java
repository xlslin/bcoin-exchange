package com.sharingif.blockchain.ether.job.service.impl;

import com.sharingif.blockchain.ether.job.dao.BatchJobDAO;
import com.sharingif.blockchain.ether.job.model.entity.BatchJob;
import com.sharingif.blockchain.ether.job.service.BatchJobService;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * BatchJobServiceImpl
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/11/29 下午5:57
 */
@Service
public class BatchJobServiceImpl extends BaseServiceImpl<BatchJob, String> implements BatchJobService {

    private BatchJobDAO batchJobDAO;

    @Resource
    public void setBatchJobDAO(BatchJobDAO batchJobDAO) {
        super.setBaseDAO(batchJobDAO);
        this.batchJobDAO = batchJobDAO;
    }

    @Override
    public List<BatchJob> getSuspendingStatus() {

        BatchJob batchJob = new BatchJob();
        batchJob.setStatus(BatchJob.STATUS_PENDING);
        batchJob.setPlanExecuteTime(new Date());
        PaginationCondition<BatchJob> suspendingPaginationCondition = new PaginationCondition<BatchJob>();
        suspendingPaginationCondition.setCondition(batchJob);
        suspendingPaginationCondition.setQueryCount(false);
        suspendingPaginationCondition.setCurrentPage(1);
        suspendingPaginationCondition.setPageSize(PaginationCondition.DEFAULT_PAGE_SIZE);

        PaginationRepertory suspendingPaginationRepertory = batchJobDAO.queryPaginationListByPlanExecuteTimeStatus(suspendingPaginationCondition);

        return suspendingPaginationRepertory.getPageItems();
    }

    @Override
    public int updateStatusToInQueue(String id) {

        BatchJob batchJob = new BatchJob();
        batchJob.setId(id);
        batchJob.setStatus(BatchJob.STATUS_IN_QUEUE);

        return batchJobDAO.updateById(batchJob);
    }

    @Override
    public int updateStatusToHandling(String id) {
        BatchJob batchJob = new BatchJob();
        batchJob.setId(id);
        batchJob.setStatus(BatchJob.STATUS_HANDLING);

        return batchJobDAO.updateById(batchJob);
    }

    @Override
    public int updateJobStatusInQueueToPending() {
        return batchJobDAO.updateStatusByStatus(BatchJob.STATUS_IN_QUEUE, BatchJob.STATUS_PENDING);
    }

    @Override
    public int updateJobStatusHandlingToPending() {
        return batchJobDAO.updateStatusByStatus(BatchJob.STATUS_HANDLING, BatchJob.STATUS_PENDING);
    }

    @Override
    public int deleteSolvedBatchJob() {
        BatchJob batchJob = new BatchJob();
        batchJob.setStatus(BatchJob.STATUS_SOLVED);

        return batchJobDAO.deleteByCondition(batchJob);
    }

}
