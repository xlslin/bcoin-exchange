package com.sharingif.blockchain.ether.job.scheduled;

import com.sharingif.cube.batch.core.handler.SimpleDispatcherHandler;
import com.sharingif.cube.batch.core.request.JobRequest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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

    private SimpleDispatcherHandler simpleDispatcherHandler;

    @Resource
    public void setSimpleDispatcherHandler(SimpleDispatcherHandler simpleDispatcherHandler) {
        this.simpleDispatcherHandler = simpleDispatcherHandler;
    }

    @Scheduled(fixedRate = 1000*1)
    public void putQueue() {
        JobRequest jobRequest = new JobRequest();
        jobRequest.setLookupPath("/job/putQueue");

        simpleDispatcherHandler.doDispatch(jobRequest);
    }

    @Scheduled(fixedRate = 1000*1)
    public void consume() {
        JobRequest jobRequest = new JobRequest();
        jobRequest.setLookupPath("/job/consume");

        simpleDispatcherHandler.doDispatch(jobRequest);
    }

}
