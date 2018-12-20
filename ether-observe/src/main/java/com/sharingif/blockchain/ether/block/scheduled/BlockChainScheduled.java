package com.sharingif.blockchain.ether.block.scheduled;


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
    public void syncDataJob() {
        JobRequest jobRequest = new JobRequest();
        jobRequest.setLookupPath("/blockChain/syncDataJob");

        simpleDispatcherHandler.doDispatch(jobRequest);
    }

}
