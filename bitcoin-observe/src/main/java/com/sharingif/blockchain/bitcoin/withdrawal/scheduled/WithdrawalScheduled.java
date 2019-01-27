package com.sharingif.blockchain.bitcoin.withdrawal.scheduled;

import com.sharingif.cube.batch.core.handler.SimpleDispatcherHandler;
import com.sharingif.cube.batch.core.request.JobRequest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@EnableScheduling
public class WithdrawalScheduled {

    private SimpleDispatcherHandler simpleDispatcherHandler;

    @Resource
    public void setSimpleDispatcherHandler(SimpleDispatcherHandler simpleDispatcherHandler) {
        this.simpleDispatcherHandler = simpleDispatcherHandler;
    }

    @Scheduled(fixedRate = 1000*1)
    public synchronized void withdrawalEther() {
        JobRequest jobRequest = new JobRequest();
        jobRequest.setLookupPath("/withdrawal/withdrawal");

        simpleDispatcherHandler.doDispatch(jobRequest);
    }

    @Scheduled(fixedRate = 1000*1)
    public synchronized void readyInitNotice() {
        JobRequest jobRequest = new JobRequest();
        jobRequest.setLookupPath("/withdrawal/readyInitNotice");

        simpleDispatcherHandler.doDispatch(jobRequest);
    }

    @Scheduled(fixedRate = 1000*1)
    public synchronized void finishNotice() {
        JobRequest jobRequest = new JobRequest();
        jobRequest.setLookupPath("/withdrawal/finishNotice");

        simpleDispatcherHandler.doDispatch(jobRequest);
    }

}
