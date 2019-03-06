package com.sharingif.blockchain.ether.job.scheduled;

import com.sharingif.blockchain.ether.job.model.entity.BatchJob;
import com.sharingif.blockchain.ether.job.service.BatchJobService;
import com.sharingif.cube.batch.core.handler.MultithreadDispatcherHandler;
import com.sharingif.cube.batch.core.handler.SimpleDispatcherHandler;
import com.sharingif.cube.batch.core.request.JobRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 必须具备的任务
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/11/28 下午8:12
 */
@Component
@EnableScheduling
public class JobScheduled {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 队列容量
     */
    private int queueSize;
    /**
     * 队列
     */
    private Queue<JobRequest> queue = new ConcurrentLinkedQueue<JobRequest>();
    private SimpleDispatcherHandler simpleDispatcherHandler;
    private MultithreadDispatcherHandler jobMultithreadDispatcherHandler;
    private BatchJobService batchJobService;
    private DataSourceTransactionManager dataSourceTransactionManager;

    @Value("${job.queue.size}")
    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }
    @Resource
    public void setSimpleDispatcherHandler(SimpleDispatcherHandler simpleDispatcherHandler) {
        this.simpleDispatcherHandler = simpleDispatcherHandler;
    }
    @Resource
    public void setJobMultithreadDispatcherHandler(MultithreadDispatcherHandler jobMultithreadDispatcherHandler) {
        this.jobMultithreadDispatcherHandler = jobMultithreadDispatcherHandler;
    }
    @Resource
    public void setBatchJobService(BatchJobService batchJobService) {
        this.batchJobService = batchJobService;
    }
    @Resource
    public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
        this.dataSourceTransactionManager = dataSourceTransactionManager;
    }

    @Scheduled(fixedRate = 1000*1)
    public synchronized void putQueue() {
        if(queue.size() >= queueSize) {
            return;
        }

        List<BatchJob> suspendingJobRequestList = batchJobService.getSuspendingStatus();

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

    @Scheduled(fixedRate = 1000*1)
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

        jobMultithreadDispatcherHandler.doDispatch(inQueueJobRequest);

    }

    @Scheduled(fixedRate = 1000*60*5)
    public synchronized void deleteJobHistory() {
        JobRequest jobRequest = new JobRequest();
        jobRequest.setLookupPath("/job/deleteJobHistory");

        simpleDispatcherHandler.doDispatch(jobRequest);
    }

}
