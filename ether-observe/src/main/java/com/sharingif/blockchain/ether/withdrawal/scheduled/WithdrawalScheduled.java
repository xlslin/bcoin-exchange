package com.sharingif.blockchain.ether.withdrawal.scheduled;

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
    public synchronized void initDepositNotice() {
        JobRequest jobRequest = new JobRequest();
        jobRequest.setLookupPath("/withdrawal/initWithdrawalNotice");

        simpleDispatcherHandler.doDispatch(jobRequest);
    }

    @Scheduled(fixedRate = 1000*1)
    public synchronized void readyFinishNotice() {
        JobRequest jobRequest = new JobRequest();
        jobRequest.setLookupPath("/withdrawal/readyFinishNotice");

        simpleDispatcherHandler.doDispatch(jobRequest);
    }

}
