package com.sharingif.blockchain.bitcoin.block.scheduled;


import com.sharingif.blockchain.bitcoin.block.model.entity.BlockChain;
import com.sharingif.blockchain.bitcoin.block.service.BitCoinBlockService;
import com.sharingif.blockchain.bitcoin.block.service.BlockChainService;
import com.sharingif.cube.batch.core.handler.SimpleDispatcherHandler;
import com.sharingif.cube.batch.core.request.JobRequest;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

@Component
@EnableScheduling
public class BlockChainScheduled {

    private String btcValidBlockNumber;
    private SimpleDispatcherHandler simpleDispatcherHandler;
    private BlockChainService blockChainService;
    private BitCoinBlockService bitCoinBlockService;

    @Value("${btc.valid.block.number}")
    public void setBtcValidBlockNumber(String btcValidBlockNumber) {
        this.btcValidBlockNumber = btcValidBlockNumber;
    }
    @Resource
    public void setSimpleDispatcherHandler(SimpleDispatcherHandler simpleDispatcherHandler) {
        this.simpleDispatcherHandler = simpleDispatcherHandler;
    }
    @Resource
    public void setBlockChainService(BlockChainService blockChainService) {
        this.blockChainService = blockChainService;
    }
    @Resource
    public void setBitCoinBlockService(BitCoinBlockService bitCoinBlockService) {
        this.bitCoinBlockService = bitCoinBlockService;
    }

    @Scheduled(fixedRate = 1000*1)
    public synchronized void readySyncData() {
        if(blockChainService.getBlockTransactionQueue().size() !=0) {
            return;
        }

        BlockChain queryBlockChain = new BlockChain();
        queryBlockChain.setStatus(BlockChain.STATUS_UNTREATED);
        PaginationCondition<BlockChain> blockChainPaginationCondition = new PaginationCondition<BlockChain>();
        blockChainPaginationCondition.setCondition(queryBlockChain);
        blockChainPaginationCondition.setQueryCount(false);
        blockChainPaginationCondition.setCurrentPage(1);
        blockChainPaginationCondition.setPageSize(20);

        PaginationRepertory<BlockChain> paginationRepertory = blockChainService.getPaginationListOrderByBlockNumberAsc(blockChainPaginationCondition);
        List<BlockChain> blockChainList = paginationRepertory.getPageItems();
        if(blockChainList == null || blockChainList.isEmpty()) {
            return;
        }


        JobRequest<List<BlockChain>> jobRequest = new JobRequest();
        jobRequest.setLookupPath("/blockChain/readySyncData");
        jobRequest.setData(blockChainList);

        simpleDispatcherHandler.doDispatch(jobRequest);
    }

    @Scheduled(fixedRate = 1000*1)
    public synchronized void readyValidateBolck() {
        BigInteger currentBlockNumber = bitCoinBlockService.getBlockNumber();

        BlockChain queryBlockChain = new BlockChain();
        queryBlockChain.setBlockNumber(currentBlockNumber.subtract(new BigInteger(btcValidBlockNumber)));
        queryBlockChain.setStatus(BlockChain.STATUS_UNVERIFIED);
        PaginationCondition<BlockChain> blockChainPaginationCondition = new PaginationCondition<BlockChain>();
        blockChainPaginationCondition.setCondition(queryBlockChain);
        blockChainPaginationCondition.setQueryCount(false);
        blockChainPaginationCondition.setCurrentPage(1);
        blockChainPaginationCondition.setPageSize(20);

        PaginationRepertory<BlockChain> paginationRepertory = blockChainService.getPaginationListByBlockNumberStatus(blockChainPaginationCondition);
        List<BlockChain> blockChainList = paginationRepertory.getPageItems();

        if(blockChainList == null || blockChainList.isEmpty()) {
            return;
        }

        JobRequest<List<BlockChain>> jobRequest = new JobRequest();
        jobRequest.setLookupPath("/blockChain/readyValidateBolck");
        jobRequest.setData(blockChainList);

        simpleDispatcherHandler.doDispatch(jobRequest);
    }

}
