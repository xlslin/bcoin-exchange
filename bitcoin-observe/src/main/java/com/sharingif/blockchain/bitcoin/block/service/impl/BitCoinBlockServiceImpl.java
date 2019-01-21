package com.sharingif.blockchain.bitcoin.block.service.impl;

import com.sharingif.blockchain.bitcoin.block.service.BitCoinBlockService;
import org.bitcoincore.api.blockchain.entity.GetBlockRsp;
import org.bitcoincore.api.blockchain.service.BlockChainApiService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;

@Service
public class BitCoinBlockServiceImpl implements BitCoinBlockService {

    private BlockChainApiService blockChainApiService;

    @Resource
    public void setBlockChainApiService(BlockChainApiService blockChainApiService) {
        this.blockChainApiService = blockChainApiService;
    }

    @Override
    public BigInteger getBlockNumber() {
        return blockChainApiService.getBlockCount();
    }

    @Override
    public String getBlockHash(BigInteger blockNumber) {
        return blockChainApiService.getBlockHash(blockNumber);
    }

    @Override
    public GetBlockRsp getBlock(String blockHash) {
        return blockChainApiService.getBlock(blockHash, 1);
    }

    @Override
    public GetBlockRsp getBlock(BigInteger blockNumber) {
        return getBlock(getBlockHash(blockNumber));
    }

}
