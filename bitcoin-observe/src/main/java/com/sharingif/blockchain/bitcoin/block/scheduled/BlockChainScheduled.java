package com.sharingif.blockchain.bitcoin.block.scheduled;


import com.sharingif.cube.batch.core.handler.SimpleDispatcherHandler;
import com.sharingif.cube.batch.core.request.JobRequest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@EnableScheduling
public class BlockChainScheduled {

    private SimpleDispatcherHandler simpleDispatcherHandler;

    @Resource
    public void setSimpleDispatcherHandler(SimpleDispatcherHandler simpleDispatcherHandler) {
        this.simpleDispatcherHandler = simpleDispatcherHandler;
    }

    @Scheduled(fixedRate = 1000*1)
    public synchronized void readySyncData() {
        JobRequest jobRequest = new JobRequest();
        jobRequest.setLookupPath("/blockChain/readySyncData");

        simpleDispatcherHandler.doDispatch(jobRequest);
    }

}
