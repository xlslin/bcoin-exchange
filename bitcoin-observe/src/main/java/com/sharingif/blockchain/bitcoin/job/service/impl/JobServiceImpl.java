package com.sharingif.blockchain.bitcoin.job.service.impl;

import com.sharingif.blockchain.bitcoin.job.model.entity.BatchJob;
import com.sharingif.blockchain.bitcoin.job.service.BatchJobService;
import com.sharingif.cube.batch.core.JobConfig;
import com.sharingif.cube.batch.core.JobModel;
import com.sharingif.cube.batch.core.JobService;
import com.sharingif.cube.batch.core.handler.MultithreadDispatcherHandler;
import com.sharingif.cube.batch.core.handler.SimpleDispatcherHandler;
import com.sharingif.cube.batch.core.request.JobRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * JobServiceImpl
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/11/29 上午11:32
 */
@Service
public class JobServiceImpl implements JobService, InitializingBean {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private BatchJobService batchJobService;
    private Map<String, JobConfig> allJobConfig;

    @Resource
    public void setBatchJobService(BatchJobService batchJobService) {
        this.batchJobService = batchJobService;
    }
    @Resource
    public void setAllJobConfig(@Qualifier("allJobConfig") Map<String, JobConfig> allJobConfig) {
        this.allJobConfig = allJobConfig;
    }

    @Override
    public void add(JobRequest jobRequest) {

    }

    @Override
    public void add(String jobId, JobModel jobModel) {
        BatchJob batchJob = new BatchJob();
        batchJob.setLookupPath(jobModel.getLookupPath());
        batchJob.setPlanExecuteTime(jobModel.getPlanExecuteTime());
        batchJob.setExecuteCount(0);
        batchJob.setDataId(jobModel.getDataId());
        batchJob.setStatus(BatchJob.STATUS_PENDING);

        batchJobService.add(batchJob);
    }

    @Override
    public void add(String jobId, List<JobModel> jobModelList) {
        for(JobModel jobModel : jobModelList) {
            add(jobId, jobModel);
        }
    }

    @Override
    public void success(String jobId) {
        BatchJob queryBatchJob = batchJobService.getById(jobId);

        BatchJob updateBatchJob = new BatchJob();
        updateBatchJob.setId(queryBatchJob.getId());
        updateBatchJob.setActualExecuteTime(new Date());
        updateBatchJob.setStatus(BatchJob.STATUS_SOLVED);
        updateBatchJob.setExecuteCount(queryBatchJob.getExecuteCount()+1);

        batchJobService.updateById(updateBatchJob);
    }

    @Transactional
    @Override
    public void failure(String jobId, String message, String localizedMessage, String cause) {
        BatchJob queryBatchJob = batchJobService.getById(jobId);

        BatchJob updateBatchJob = new BatchJob();
        updateBatchJob.setId(queryBatchJob.getId());
        updateBatchJob.setActualExecuteTime(new Date());
        updateBatchJob.setStatus(BatchJob.STATUS_FAILED);
        updateBatchJob.setExecuteCount(queryBatchJob.getExecuteCount()+1);
        updateBatchJob.setErrorMessageCode(message);
        updateBatchJob.setErrorLocalizedMessage(localizedMessage);
        updateBatchJob.setErrorCause(cause);

        batchJobService.updateById(updateBatchJob);

        JobConfig jobConfig = allJobConfig.get(queryBatchJob.getLookupPath());
        if(jobConfig.getMaxExecuteCount() > updateBatchJob.getExecuteCount()) {
            BatchJob batchJob = new BatchJob();
            batchJob.setLookupPath(jobConfig.getLookupPath());
            if(jobConfig.getIntervalPlanExecuteTime() == null) {
                batchJob.setPlanExecuteTime(queryBatchJob.getPlanExecuteTime());
            } else {
                batchJob.setPlanExecuteTime(new Date(System.currentTimeMillis()+jobConfig.getIntervalPlanExecuteTime()));
            }
            batchJob.setExecuteCount(updateBatchJob.getExecuteCount());
            batchJob.setDataId(queryBatchJob.getDataId());
            batchJob.setStatus(BatchJob.STATUS_PENDING);

            batchJobService.add(batchJob);
        }
    }

    @Override
    public void deleteJobHistory() {
        batchJobService.deleteSolvedBatchJob();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        batchJobService.updateJobStatusInQueueToPending();
        batchJobService.updateJobStatusHandlingToPending();
    }
}
