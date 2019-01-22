package com.sharingif.blockchain.bitcoin.block.service.impl;

import com.sharingif.blockchain.bitcoin.block.service.BitCoinBlockService;
import org.bitcoincore.api.blockchain.entity.Block;
import org.bitcoincore.api.rawtransactions.entity.Transaction;
import org.bitcoincore.api.blockchain.service.BlockChainApiService;
import org.bitcoincore.api.rawtransactions.service.RawTransactionsApiService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

@Service
public class BitCoinBlockServiceImpl implements BitCoinBlockService {

    private BlockChainApiService blockChainApiService;
    private RawTransactionsApiService rawTransactionsApiService;

    @Resource
    public void setBlockChainApiService(BlockChainApiService blockChainApiService) {
        this.blockChainApiService = blockChainApiService;
    }
    @Resource
    public void setRawTransactionsApiService(RawTransactionsApiService rawTransactionsApiService) {
        this.rawTransactionsApiService = rawTransactionsApiService;
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
    public Block<List<String>> getBlock(String blockHash) {
        return blockChainApiService.getBlock(blockHash, 1);
    }

    @Override
    public Block<List<String>> getBlock(BigInteger blockNumber) {
        return getBlock(getBlockHash(blockNumber));
    }

    @Override
    public Block<List<Transaction>> getBlockFullTransaction(String blockHash) {
        return blockChainApiService.getBlockFullTransaction(blockHash, 2);
    }

    @Override
    public Transaction getFullRawTransaction(String txid, String blockhash) {
        return rawTransactionsApiService.getFullRawTransaction(txid, true, blockhash);
    }

}
