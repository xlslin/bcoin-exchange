package com.sharingif.blockchain.bitcoin.block.scheduled;


import com.sharingif.blockchain.bitcoin.block.model.entity.BlockChainSync;
import com.sharingif.blockchain.bitcoin.block.service.BitCoinBlockService;
import com.sharingif.blockchain.bitcoin.block.service.BlockChainSyncService;
import com.sharingif.cube.batch.core.handler.SimpleDispatcherHandler;
import com.sharingif.cube.batch.core.request.JobRequest;
import org.bitcoincore.api.blockchain.entity.Block;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

@Component
@EnableScheduling
public class BlockChainSyncScheduled {

    private SimpleDispatcherHandler simpleDispatcherHandler;
    private BlockChainSyncService blockChainSyncService;
    private BitCoinBlockService bitCoinBlockService;

    @Resource
    public void setSimpleDispatcherHandler(SimpleDispatcherHandler simpleDispatcherHandler) {
        this.simpleDispatcherHandler = simpleDispatcherHandler;
    }
    @Resource
    public void setBlockChainSyncService(BlockChainSyncService blockChainSyncService) {
        this.blockChainSyncService = blockChainSyncService;
    }
    @Resource
    public void setBitCoinBlockService(BitCoinBlockService bitCoinBlockService) {
        this.bitCoinBlockService = bitCoinBlockService;
    }

    @Scheduled(fixedRate = 1000*1)
    public synchronized void sync() {
        // 查询数据库区块同步信息
        BlockChainSync blockChainSync = blockChainSyncService.getSyncType();

        // 查询区块链当前区块号
        BigInteger blockNumber = bitCoinBlockService.getBlockNumber();

        // 如果数据库区块同步信息为空，插入当前区块链信息到BlockChainSync表、BlockChain表并返回
        if(blockChainSync == null) {
            JobRequest<BigInteger> jobRequest = new JobRequest<BigInteger>();
            jobRequest.setLookupPath("/blockChainSync/add");
            jobRequest.setData(blockNumber);

            simpleDispatcherHandler.doDispatch(jobRequest);

            return;
        }

        // 如果数据库区块同步信息不为空比较数据库区块号是否小于区块链当前区块号
        // 如果数据库区块号不小于区块链当前区块号直接返回
        BigInteger currentSyncBlockNumber = blockChainSync.getBlockNumber();
        if(currentSyncBlockNumber.compareTo(blockNumber) >= 0) {
            return;
        }

        // 如果数据库区块号小于区块链当前区块号，递增修改BlockChainSync，添加BlockChain表
        while (currentSyncBlockNumber.compareTo(blockNumber)< 0) {
            currentSyncBlockNumber = currentSyncBlockNumber.add(BigInteger.ONE);

            JobRequest<BigInteger> jobRequest = new JobRequest<BigInteger>();
            jobRequest.setLookupPath("/blockChainSync/update");
            jobRequest.setData(currentSyncBlockNumber);

            simpleDispatcherHandler.doDispatch(jobRequest);
        }

    }

}
