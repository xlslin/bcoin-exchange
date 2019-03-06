package com.sharingif.blockchain.bitcoin.block.scheduled;

import com.sharingif.blockchain.bitcoin.block.model.entity.TransactionBusiness;
import com.sharingif.blockchain.bitcoin.block.service.TransactionBusinessService;
import com.sharingif.cube.batch.core.handler.SimpleDispatcherHandler;
import com.sharingif.cube.batch.core.request.JobRequest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@EnableScheduling
public class TransactionBusinessScheduled {

    private SimpleDispatcherHandler simpleDispatcherHandler;
    private TransactionBusinessService transactionBusinessService;

    @Resource
    public void setSimpleDispatcherHandler(SimpleDispatcherHandler simpleDispatcherHandler) {
        this.simpleDispatcherHandler = simpleDispatcherHandler;
    }
    @Resource
    public void setTransactionBusinessService(TransactionBusinessService transactionBusinessService) {
        this.transactionBusinessService = transactionBusinessService;
    }

    @Scheduled(fixedRate = 1000*1)
    public synchronized void settle() {
        List<TransactionBusiness> transactionBusinessList = transactionBusinessService.getSettleStatusReady();

        if(transactionBusinessList == null || transactionBusinessList.isEmpty()) {
            return;
        }


        JobRequest<List<TransactionBusiness>> jobRequest = new JobRequest();
        jobRequest.setLookupPath("/transactionBusiness/settle");
        jobRequest.setData(transactionBusinessList);

        simpleDispatcherHandler.doDispatch(jobRequest);
    }

}
