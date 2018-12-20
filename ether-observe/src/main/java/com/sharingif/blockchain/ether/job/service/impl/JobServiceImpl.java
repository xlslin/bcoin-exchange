package com.sharingif.blockchain.ether.job.service.impl;

import com.sharingif.blockchain.ether.job.model.entity.BatchJob;
import com.sharingif.blockchain.ether.job.service.BatchJobService;
import com.sharingif.cube.batch.core.JobModel;
import com.sharingif.cube.batch.core.JobService;
import com.sharingif.cube.batch.core.handler.SimpleDispatcherHandler;
import com.sharingif.cube.batch.core.request.JobRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
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

    /**
     * 队列容量
     */
    private int queueSize;
    /**
     * 队列
     */
    private Queue<JobRequest> queue = new ConcurrentLinkedQueue<JobRequest>();
    /**
     * 查询job列表最大条数
     */
    private int queryJobSize;

    private BatchJobService batchJobService;
    private SimpleDispatcherHandler simpleDispatcherHandler;
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private DataSourceTransactionManager dataSourceTransactionManager;

    @Value("${job.queue.size}")
    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }
    @Value("${job.query.jobSize}")
    public void setQueryJobSize(int queryJobSize) {
        this.queryJobSize = queryJobSize;
    }
    @Resource
    public void setBatchJobService(BatchJobService batchJobService) {
        this.batchJobService = batchJobService;
    }
    @Resource
    public void setSimpleDispatcherHandler(SimpleDispatcherHandler simpleDispatcherHandler) {
        this.simpleDispatcherHandler = simpleDispatcherHandler;
    }
    @Resource(name = "workThreadPoolTaskExecutor")
    public void setThreadPoolTaskExecutor(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }
    @Resource
    public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
        this.dataSourceTransactionManager = dataSourceTransactionManager;
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
        batchJob.setStatus(BatchJob.STATUS_IN_QUEUE);

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
        updateBatchJob.setActualExecuteTime(new Date());
        updateBatchJob.setStatus(BatchJob.STATUS_SOLVED);
        updateBatchJob.setExecuteCount(queryBatchJob.getExecuteCount()+1);

        batchJobService.updateById(updateBatchJob);
    }

    @Override
    public void failure(String jobId, String message, String localizedMessage, String cause) {

    }

    @Override
    public synchronized void putQueue() {

        if(queue.size()>=queueSize) {
            return;
        }

        List<BatchJob> suspendingJobRequestList = batchJobService.getSuspendingStatus(queryJobSize);

        if(suspendingJobRequestList == null || suspendingJobRequestList.size() == 0) {
            return;
        }

        for(BatchJob batchJob : suspendingJobRequestList){
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            TransactionStatus status = dataSourceTransactionManager.getTransaction(def);

            try {
                batchJobService.updateStatusToInQueue(batchJob.getId());

                queue.add(batchJob.convertJobRequest());

                dataSourceTransactionManager.commit(status);
            } catch (Exception e) {
                logger.error("putQueue error", e);
                dataSourceTransactionManager.rollback(status);
            }
        }

    }

    @Override
    public synchronized void consume() {

        JobRequest inQueueJobRequest = queue.peek();
        if(null == inQueueJobRequest){
            return;
        }

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = dataSourceTransactionManager.getTransaction(def);

        try {
            // 修改job状态
            batchJobService.updateStatusToHandling(inQueueJobRequest.getId());

            // 从队列中删除job
            queue.remove(inQueueJobRequest);

            dataSourceTransactionManager.commit(status);
        } catch (Exception e) {
            logger.error("consume error", e);
            dataSourceTransactionManager.rollback(status);
        }

        threadPoolTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                simpleDispatcherHandler.doDispatch(inQueueJobRequest);
            }
        });

    }

    @Override
    public void updateJobStatusInQueueToSuspending() {
        batchJobService.updateJobStatusInQueueToSuspending();
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        updateJobStatusInQueueToSuspending();
    }
}
