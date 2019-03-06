package com.sharingif.blockchain.bitcoin.block.scheduled;

import com.sharingif.blockchain.bitcoin.block.model.entity.TransactionBusinessAccount;
import com.sharingif.blockchain.bitcoin.block.service.TransactionBusinessAccountService;
import com.sharingif.cube.batch.core.handler.SimpleDispatcherHandler;
import com.sharingif.cube.batch.core.request.JobRequest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@EnableScheduling
public class TransactionBusinessAccountScheduled {

    private SimpleDispatcherHandler simpleDispatcherHandler;
    private TransactionBusinessAccountService transactionBusinessAccountService;

    @Resource
    public void setSimpleDispatcherHandler(SimpleDispatcherHandler simpleDispatcherHandler) {
        this.simpleDispatcherHandler = simpleDispatcherHandler;
    }
    @Resource
    public void setTransactionBusinessAccountService(TransactionBusinessAccountService transactionBusinessAccountService) {
        this.transactionBusinessAccountService = transactionBusinessAccountService;
    }

    @Scheduled(fixedRate = 1000*1)
    public synchronized void addTransactionBusinessAccount() {
        List<TransactionBusinessAccount> transactionBusinessAccountList = transactionBusinessAccountService.getAll();
        if(transactionBusinessAccountList == null || transactionBusinessAccountList.isEmpty()) {
            return;
        }

        JobRequest<List<TransactionBusinessAccount>> jobRequest = new JobRequest();
        jobRequest.setLookupPath("/transactionBusinessAccount/settleAccounts");
        jobRequest.setData(transactionBusinessAccountList);

        simpleDispatcherHandler.doDispatch(jobRequest);
    }

}
