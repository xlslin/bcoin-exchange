package com.sharingif.blockchain.ether.block.scheduled;

import com.sharingif.cube.batch.core.handler.SimpleDispatcherHandler;
import com.sharingif.cube.batch.core.request.JobRequest;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

public class TransactionBusinessAccountScheduled {

    private SimpleDispatcherHandler simpleDispatcherHandler;

    @Resource
    public void setSimpleDispatcherHandler(SimpleDispatcherHandler simpleDispatcherHandler) {
        this.simpleDispatcherHandler = simpleDispatcherHandler;
    }

    @Scheduled(fixedRate = 1000*1)
    public synchronized void addTransactionBusinessAccount() {
        JobRequest jobRequest = new JobRequest();
        jobRequest.setLookupPath("/transactionBusinessAccount/settleAccounts");

        simpleDispatcherHandler.doDispatch(jobRequest);
    }

}
