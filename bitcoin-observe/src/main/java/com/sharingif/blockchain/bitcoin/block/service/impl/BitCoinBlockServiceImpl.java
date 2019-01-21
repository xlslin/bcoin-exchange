package com.sharingif.blockchain.bitcoin.block.service.impl;

import com.sharingif.blockchain.bitcoin.block.service.BitCoinBlockService;
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
    public BigInteger getBlockCount() {
        return blockChainApiService.getBlockCount();
    }

}
